import tweepy
import dataset
from datafreeze import freeze
from textblob import TextBlob

CONNECTION_STRING = "sqlite:///tweets.db"
CSV_NAME = "data/tweets.csv"
TABLE_NAME = "tweets"

db = dataset.connect(CONNECTION_STRING)

result = db[TABLE_NAME].all()
freeze(result, format='csv', filename=CSV_NAME)
