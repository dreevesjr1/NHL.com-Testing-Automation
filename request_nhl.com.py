import requests

url = "https://api-web.nhle.com/v1/skater-stats-leaders/current"

response = requests.get(url)
data = response.json()

print(data)