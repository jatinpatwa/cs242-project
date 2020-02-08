import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.Iterator;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class indexer{
    private IndexWriter writer = null;
    private parser tweet = new parser();
    private String indexPath = null;
    public ArrayList<String> indexFieldList = new ArrayList<String>();

    public static void main(String[] args) throws IOException{
        System.out.println("IndexCreator main...");
        indexer ic = new indexer();
        ic.createIndex();
    }
             
    public indexer()throws IOException {
        
        this.indexPath = new File(".").getCanonicalPath()+ "/indices/";
        Directory indexDir = FSDirectory.open( new File(this.indexPath).toPath() );
        System.out.println("IndexPath is: " + indexPath);

       
        IndexWriterConfig indexConfig = new IndexWriterConfig( new StandardAnalyzer()); 
        writer = new IndexWriter(indexDir, indexConfig);
    }
    
    public Document getDocument(JSONObject json_obj) throws IOException{
        Document doc = new Document();
        
        tweet.setTweet(json_obj);
        if(tweet.getText() != null){
            String index = "text";
            Field text = new TextField(index,tweet.getText(), Field.Store.YES);
            doc.add(text);        
            this.addToIndexFieldList(index);
            }

        if(tweet.getTimeStamp() != null){
     
            String index = "timestamp";
            Field timestamp = new StringField(index,tweet.getTimeStamp(), Field.Store.YES);
            doc.add(timestamp);        
            this.addToIndexFieldList(index);
            }

        if(tweet.getUserLocation() != null){
            
            String index = "location";
            Field location = new StringField(index,tweet.getUserLocation(), Field.Store.YES);
            doc.add(location);        
            this.addToIndexFieldList(index);
            }
        if(tweet.getBoundingCoordinates() != null){
           
            String index = "coords";
            Field location = new StringField(index,tweet.getBoundingCoordinates(), Field.Store.YES);
            doc.add(location);        
            this.addToIndexFieldList(index);
        }
        if(tweet.getUserName() != null){
           
            String index = "username";
            Field username = new StringField(index,tweet.getUserName(), Field.Store.YES);
            doc.add(username);        
            this.addToIndexFieldList(index);
            }

        if(tweet.getUserScreenName() != null){
           
            String index = "userscreenname";
            Field userscreenname = new StringField(index,tweet.getUserScreenName(), Field.Store.YES);
            doc.add(userscreenname);        
            this.addToIndexFieldList(index);
            }

        if(tweet.getUserImageUrl() != null){
            
            String index = "userimageurl";
            Field userimageurl = new StringField(index,tweet.getUserImageUrl(), Field.Store.YES);
            doc.add(userimageurl);        
            this.addToIndexFieldList(index);
            }

        if(tweet.getLikedCount() != null){
            
            String index = "likedcount";
            Field likedcount = new LongPoint(index,tweet.getLikedCount());
            doc.add(likedcount);        
            this.addToIndexFieldList(index);
            }
        
        ArrayList<String> hash_tag_list = tweet.getHashTags();
        if(hash_tag_list.size() > 0){
            Iterator<String> hash_iter = hash_tag_list.iterator();
            while(hash_iter.hasNext()){
                String hash = hash_iter.next();
                
                String index = "hashtags";
                Field hashtags = new StringField(index, hash, Field.Store.YES);
                doc.add(hashtags);
                this.addToIndexFieldList(index);
            }
        }
       
        ArrayList<String> links_list = tweet.getLinks();
        if(links_list.size() > 0){
            Iterator<String> link_iter = links_list.iterator();
            while(link_iter.hasNext()){
                String link = link_iter.next();
                
                String index = "links";
                doc.add(new StringField(index,link,Field.Store.YES));
                this.addToIndexFieldList(index);
            }
        }
        return doc;    
    }

    public void createIndex() throws IOException{
        try{
        	File file = new File("./data/data.json");
        	Scanner scan = new Scanner(file, "UTF-8");       	
            JSONObject obj;
            while(scan.hasNext()) {
                    obj = (JSONObject) new JSONParser().parse(scan.nextLine());
                    Document doc = this.getDocument(obj);
                    this.writer.addDocument(doc);
            }
            this.writer.close();
            scan.close();
        }
        catch(ParseException pe){
            System.out.println(pe);
        }   
    }

    private void addToIndexFieldList(String index){
        if(!this.indexFieldList.contains(index)){
            this.indexFieldList.add(index);
            }
        }
}





