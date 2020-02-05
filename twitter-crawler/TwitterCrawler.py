import json
import config
from datetime import datetime
from twython import Twython, TwythonStreamer, TwythonError


consumer_key = config.twitter['consumer_key']
consumer_secret = config.twitter['consumer_secret']
access_key = config.twitter['access_key']
access_secret = config.twitter['access_secret']

twitter = Twython (
  consumer_key,
  consumer_secret,
  access_key,
  access_secret
  )

f= open("data/data.json","a+")

class MyStreamer(TwythonStreamer):

  def on_success(self, data):
    if ('place' in data and data['place'] is not None) or ('location' in data['user'] and data['user']['location'] is not None):
      print(datetime.now())
      f.write(json.dumps(data) + ',\n')

  def on_error(self, status_code, data):
    print(status_code)



stream = MyStreamer(consumer_key, consumer_secret, access_key, access_secret)
for i in range(10):
  stream.statuses.filter(track = 'corona',language='en')
