from datetime import date

currencyapi_key = '4OgMUnFb0RLrvddrJkKR973nfiXsDLLpyrwAegoa'
currencyapi_historical_rates_url = 'https://api.currencyapi.com/v3/historical?apikey=<API_KEY>&date=<DATE>&base_currency=<CURR_FROM>&currencies=<CURR_TO>'
currencyapi_latest_rates_url = 'https://api.currencyapi.com/v3/latest?apikey=<API_KEY>&base_currency=<CURR_FROM>&currencies=<CURR_TO>'


def response_to_dict(response_dict: dict, date_str: str, currency_to: str):
    return {date_str: response_dict['data'][currency_to]['value']}


async def get_rates(currency_from: str, currency_to: str, time: date, client):
    if time == date.today():
        full_url = currencyapi_latest_rates_url.replace('<API_KEY>', currencyapi_key) \
            .replace('<CURR_FROM>', currency_from) \
            .replace('<CURR_TO>', currency_to)
    else:
        full_url = currencyapi_historical_rates_url.replace('<API_KEY>', currencyapi_key) \
            .replace('<DATE>', time.isoformat()) \
            .replace('<CURR_FROM>', currency_from) \
            .replace('<CURR_TO>', currency_to)
    response = await client.get(full_url)
    if response.status_code == 404:
        return {}
    response.raise_for_status()
    return response_to_dict(response.json(), time.isoformat(), currency_to)
