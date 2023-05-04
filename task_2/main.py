import httpx
import asyncio
import operator

import frankfurter_api
import currencyapi
import github_api

from typing import Union
from fastapi import FastAPI
from fastapi.responses import HTMLResponse
from datetime import date, timedelta

html_form_content = '''
<!doctype html>
<html>
<head>
    <title>Currency service</title>
</head>

<body>
<form method="GET" action="http://localhost:8000/currencies">
    <h4>
        <input type="hidden" name=api_key id=api_key value="<API_KEY>"/>
        <label for="currency_from">Choose from currency: </label>
        <select id="currency_from" name="currency_from" required>
            <OPTIONS>
        </select>
    </h4>
    
    <h4>
        <label for="currency_to">Choose to currency: </label>
        <select id="currency_to" name="currency_to" required>
            <OPTIONS>
        </select>
    </h4>
    
    <h4>
        <label for="date_from">Choose from date: </label>
        <input type="date" id="date_from" name="date_from" required/>
    </h4>
    
    <h4>
        <label for="date_to">Choose to date: </label>
        <input type="date" id="date_to" name="date_to" required/>
    </h4>
    
    <h4>
        <input type="submit"/>
    </h4>
</form>
</body>
</html>
'''

html_results_content = '''
<!doctype html>
<html>
<head>
    <title>Currency service</title>
</head>

<body>
    <h3>Average rate: <AVG_RATE></h3>
    <h3>Highest rate: <MAX_RATE>, <MAX_DATE></h3>
    <h3>Lowest rate: <MIN_RATE>, <MIN_DATE></h3>
    <h3><PARTIAL_DATA></h3>
    <h3><NO_DATA></h3>
</body>
</html>
'''

known_api_keys = ['e9d51e81fe9d498e9089ba06a7f852cc']

app = FastAPI()


def create_currencies_option_list(currencies_dict: dict):
    options = ''
    for item in currencies_dict.items():
        options += '<option value="' + item[0] + '">' + item[1] + '</option>\n'
    return options


def validate_dates(date_from: date, date_to: date):
    if date_from > date_to:
        return HTMLResponse(content='From date cannot exceed to date', status_code=400)
    if date_to > date.today():
        return HTMLResponse(content='Cannot process dates from the future', status_code=400)
    return None


def validate_api_key(api_key: Union[str | None]):
    if api_key not in known_api_keys:
        return HTMLResponse(content='Valid api key not provided', status_code=401)
    return None


@app.get('/')
async def get_currencies_form(api_key: Union[str | None] = None):
    api_key_validation_result = validate_api_key(api_key)
    if api_key_validation_result is not None:
        return api_key_validation_result

    try:
        async with httpx.AsyncClient() as client:
            currencies_response = await frankfurter_api.get_currencies_list(client)
    except httpx.HTTPError as exc:
        return HTMLResponse(content='Cannot get currencies list', status_code=exc.response.status_code)

    options = create_currencies_option_list(currencies_response.json())
    response_content = html_form_content \
        .replace('<OPTIONS>', options) \
        .replace('<API_KEY>', api_key)
    return HTMLResponse(content=response_content, status_code=200)


@app.get('/currencies')
async def get_currencies_info(currency_from: str, currency_to: str, date_from: date, date_to: date,
                              api_key: Union[str | None]):
    api_key_validation_result = validate_api_key(api_key)
    if api_key_validation_result is not None:
        return api_key_validation_result

    if currency_to == currency_from:
        return HTMLResponse(content='Give two different currencies to fetch exchange rates', status_code=400)

    date_validation_result = validate_dates(date_from, date_to)
    if date_validation_result is not None:
        return date_validation_result

    dates = []
    delta = date_to - date_from
    for i in range(delta.days + 1):
        day = date_from + timedelta(days=i)
        dates.append(day)

    try:
        async with httpx.AsyncClient() as client:
            tasks = [asyncio.ensure_future(
                frankfurter_api.get_rates(currency_from, currency_to, date_from, date_to, client))]
            for time in dates:
                tasks.append(asyncio.ensure_future(currencyapi.get_rates(currency_from, currency_to, time, client)))
                tasks.append(asyncio.ensure_future(github_api.get_rates(currency_from, currency_to, time, client)))

            responses = await asyncio.gather(*tasks)
    except httpx.HTTPError as exc:
        code = exc.response.status_code
        if 400 <= code < 500:
            return HTMLResponse(content='Request for data is not correct', status_code=code)
        else:
            return HTMLResponse(content='Problem with calling external service', status_code=code)

    aggregated_results = {}
    partial_data_dates = []
    no_data_dates = []
    for time in dates:
        time_str = time.isoformat()
        rates_list = list(map(lambda response: response[time_str],
                              filter(lambda response: time_str in response.keys(), responses)))
        if len(rates_list) == 0:
            no_data_dates.append(time_str)
        elif len(rates_list) < 3:
            partial_data_dates.append(time_str)

        if len(rates_list) > 0:
            aggregated_results[time_str] = sum(rates_list) / len(rates_list)

    if len(aggregated_results) == 0:
        return HTMLResponse(content='Received empty data from external services, cannot aggregate', status_code=404)

    max_rate = max(aggregated_results.items(), key=operator.itemgetter(1))
    min_rate = min(aggregated_results.items(), key=operator.itemgetter(1))
    avg_rate = sum(aggregated_results.values()) / len(aggregated_results)

    response_content = html_results_content.replace('<AVG_RATE>', str(avg_rate)) \
        .replace('<MAX_DATE>', max_rate[0]) \
        .replace('<MIN_DATE>', min_rate[0]) \
        .replace('<MAX_RATE>', str(max_rate[1])) \
        .replace('<MIN_RATE>', str(min_rate[1]))

    if len(partial_data_dates):
        replacement_str = 'Dates with partial data from APIs: '
        for time in partial_data_dates:
            replacement_str += time + ', '
        replacement_str = replacement_str.rstrip(', ')
        response_content = response_content.replace('<PARTIAL_DATA>', replacement_str)
    else:
        response_content.replace('<PARTIAL_DATA>', '')

    if len(no_data_dates):
        replacement_str = 'Dates with partial data from APIs: '
        for time in no_data_dates:
            replacement_str += time + ','
        replacement_str = replacement_str.rstrip(', ')
        response_content = response_content.replace('<NO_DATA>', replacement_str)
    else:
        response_content.replace('<NO_DATA>', '')

    return HTMLResponse(content=response_content, status_code=200)
