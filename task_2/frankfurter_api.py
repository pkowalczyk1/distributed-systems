from datetime import date

frankfurter_api_list_url = 'https://api.frankfurter.app/currencies'
frankfurter_api_timeseries_url = 'https://api.frankfurter.app/<DATE_FROM>..<DATE_TO>?from=<CURR_FROM>&to=<CURR_TO>'


def response_to_dict(rates_dict: dict, currency_to: str):
    result = {}
    for item in rates_dict['rates'].items():
        result[item[0]] = item[1][currency_to]
    return result


async def get_currencies_list(client):
    currencies_list = await client.get(frankfurter_api_list_url)
    currencies_list.raise_for_status()
    return currencies_list


async def get_rates(currency_from: str, currency_to: str, date_from: date, date_to: date, client):
    full_url = frankfurter_api_timeseries_url.replace('<DATE_FROM>', date_from.isoformat()) \
        .replace('<DATE_TO>', date_to.isoformat()) \
        .replace('<CURR_FROM>', currency_from) \
        .replace('<CURR_TO>', currency_to)
    response = await client.get(full_url)
    if response.status_code == 404:
        return {}
    response.raise_for_status()
    return response_to_dict(response.json(), currency_to)
