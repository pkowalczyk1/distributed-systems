from datetime import date

github_api_url = 'https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/<DATE>/currencies/<CURR_FROM>/<CURR_TO>.json'


def response_to_dict(response_dict: dict, currency_to: str):
    return {response_dict['date']: response_dict[currency_to.lower()]}


async def get_rates(currency_from: str, currency_to: str, time: date, client):
    full_url = github_api_url.replace('<DATE>', time.isoformat()) \
        .replace('<CURR_FROM>', currency_from.lower()) \
        .replace('<CURR_TO>', currency_to.lower())
    response = await client.get(full_url)
    if response.status_code == 404 or response.status_code == 403:
        return {}
    response.raise_for_status()
    return response_to_dict(response.json(), currency_to)
