import csv
import string
import tweepy
from tweepy import OAuthHandler
import json
import time
import config



consumer_key = config.twitter['consumer_key']
consumer_secret = config.twitter['consumer_secret']
access_key = config.twitter['access_key']
access_secret = config.twitter['access_secret']


auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_key, access_secret)
api = tweepy.API(auth, wait_on_rate_limit=True, wait_on_rate_limit_notify=True)


query = 'corona'

csvFile = open('data/raw_data.csv', 'w', encoding='utf-8')

csvWriter = csv.writer(csvFile)

tweet_num = 0
for tweet in tweepy.Cursor(api.search,q=query+ ' -RT',count=10000000,lang="en").items():
    if tweet.place is not None:
        try:
            print ('tweet number: {}'.format(tweet_num), tweet.text, tweet.place.full_name)
            csvWriter.writerow([tweet.created_at,
                                tweet.user.location,
                                tweet.user.followers_count,
                                tweet.user.friends_count,
                                tweet.text,
                                tweet.place.bounding_box.coordinates,
                                tweet.place.full_name,
                                tweet.place.country,
                                tweet.place.country_code,
                                tweet.place.place_type])
            tweet_num += 1

        except Exception:
            pass
