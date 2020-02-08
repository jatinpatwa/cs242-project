# CS 242 - UC Riverside
Repository of a course project for CS 242 - Information Retrieval and Web Search

## `Authors`

Arasanagatta Ramasesha, Rithika <br> 
Liu, Tianyu <br>
Patwa, Jatin <br>
Vinayak, Shreshta <br>
Yan, Jie <br>

## `Clone this Repository`

Navigate to the desired location on your computer. Enter the following command in your terminal:
```
git clone https://github.com/jatinpatwa401/cs242-project.git
```
## `Setup Instructions` <br>
#### `Setting up the Twitter Crawler`
Create a new file called config.py and add the following information:
```
twitter = {'consumer_key': '[Insert your public consumer API key]',
           'consumer_secret': '[Insert your secret consumer API key]',
           'access_token': '[Insert your public access token]',
           'access_secret': '[Insert your secret access token]'}
```
#### `Requirements for crawlers`

twython <br>
datetime <br>
json <br>
pandas <br>
tweepy <br>
string <br>
textblob <br>
tweet-preprocessor <br>
nltk <br>
dataset <br>
datafreeze <br>

#### `Running the streamer`

After everything is done, run selected version of the crawler using:
```
python twitterCrawler.py
```
This will save a .json file to twitter-crawler/data/
<br>
Move this json file to the indexer directory using:
```
mv data/data.json ./lucene-indexer/data
```
### `Runnning Lucene`

First, you should download the latest (http://www.apache.org/dyn/closer.cgi/lucene/java/) Lucene distribution and then extract it to a working directory.

You should see the Lucene JAR file in the directory you created when you extracted the archive. It should be named something like lucene-core-{version}.jar. You should also see a file called lucene-demos-{version}.jar. If you checked out the sources from Subversion then the JARs are located under the build subdirectory (after running ant successfully). Put both of these files in your Java CLASSPATH. 

Along with these, you may also have to add these external JARs to your CLASSPATH:

commons-cli-1.4 <br>
commons-logging-1.1.1 <br>
json-simple-1.1

Assuming CLASSPATH has been added succesfully, run indexer.java to index our tweets. 

Then run searcher.java to search for a particular query:
```
java searcher -q <query string> -f <index field>
```
The console should output a json of results.



## `License`

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
