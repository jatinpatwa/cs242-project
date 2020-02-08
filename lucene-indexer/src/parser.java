import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class parser{
    
    private JSONObject tweet;
    private JSONObject user;
    private boolean found_user = false;

    public void setTweet(JSONObject new_tweet){
        tweet = new_tweet;
        if(tweet.containsKey("user")){
            user = (JSONObject)tweet.get("user"); 
            found_user = true;
        }
    }
    
    public String getText(){
        if(tweet.get("text") != null){
            return (String) tweet.get("text");
        }
        return "";
    }
    public String getTimeStamp(){
        return (String) tweet.get("created_at");
    }
    
    public String getBoundingCoordinates(){
        JSONArray coords = null;
        JSONObject obj = new JSONObject();
       if(tweet.get("place") != null){
            JSONObject place = (JSONObject)tweet.get("place");
            JSONObject bound_box =(JSONObject)place.get("bounding_box");
        
            coords = (JSONArray)bound_box.get("coordinates");
            coords = (JSONArray)coords.get(0);
         
            System.out.println("TwitterParser::getBoundingCoordinates::coords");
            obj.put("coords",coords.get(0));
            System.out.println(obj.toString());
            return obj.toString();
        } 
        return null;
    }
    
    public String getFullCityName(){
        String cityName = null;
        if(tweet.get("place") != null){
            JSONObject place = (JSONObject)tweet.get("place");
            cityName = (String) place.get("full_name");
        }
        return cityName;
    }

    public String getUserName(){
        if(found_user){
            return (String) user.get("name");
        }
        else{
            return null;
        }
    }

    public String getUserScreenName(){
        if(found_user){
            return (String) user.get("screen_name");
        }
        else{
            return null;
        }
    }
    
    public String getUserImageUrl(){
        if(found_user){
            return (String) user.get("profile_image_url_https");
        }
        else{
            return null;
        }
    }
    
    public Long getUserFriendCount(){
        if(found_user){
            return (Long) user.get("friends_count");
        }
        else{
            return null;
        }
    }
    
    public Long getUserFollwersCount(){
        if(found_user){
            return (Long) user.get("followers_count");
        }
        else{
            return null;
        }
    }
    
    public ArrayList<String> getLinks(){
        Set<String> linksList = new HashSet<String>();
        if(tweet.containsKey("retweeted_status")){
            JSONObject retweet = (JSONObject)tweet.get("retweeted_status");
            if(retweet.containsKey("extended_tweet")){
                JSONObject extended = (JSONObject)retweet.get("extended_tweet");
                JSONObject entities = (JSONObject) extended.get("entities");
                JSONArray urls = (JSONArray)entities.get("urls");
                Iterator<?> url_iter = urls.iterator();
                while(url_iter.hasNext()){
                    JSONObject obj = (JSONObject)url_iter.next();
                    String expanded_url = (String) obj.get("expanded_url");
                    linksList.add(expanded_url);
                }
                
                if(entities.containsKey("media")){
                    JSONArray media = (JSONArray) entities.get("media");
                    Iterator<?> media_iter = media.iterator();
                    while(media_iter.hasNext()){
                        JSONObject obj = (JSONObject)media_iter.next();
                        String medurl = (String)obj.get("media_url");
                        String expurl = (String)obj.get("expanded_url");
                        String url = (String)obj.get("url");
                        linksList.add(expurl);
                        linksList.add(medurl);
                        linksList.add(url);
                    }
                }
            }
        }
        return new ArrayList<String>(linksList); 
    }

    public String getUserLocation(){
        if(found_user){
            return (String)user.get("location");
        }
        else{
            return null;
        }
    }
    
    public ArrayList<String> getHashTags(){
        Set<String> hash_tags_list = new HashSet<String>();
        JSONArray hash_tags = null;

        String[] text = getText().split(" ");
        for(String t :text){
            if(t.length() > 0){
                if(t.charAt(0) == '#'){
                    hash_tags_list.add(t.replace("#",""));
                }
            }
        }

        if(tweet.containsKey("entities")){
            JSONObject entit = (JSONObject)tweet.get("entities");
            hash_tags = (JSONArray) entit.get("hashtags"); 
            Iterator<?> iter = hash_tags.iterator();
            if(!hash_tags.isEmpty()){
                while(iter.hasNext()){
                    JSONObject ht = (JSONObject)iter.next();
                    String htag =(String) ht.get("text");
                    hash_tags_list.add(htag);
                }
            }

        }
        if(tweet.containsKey("extended_tweet")){
            JSONObject entit = (JSONObject)tweet.get("entities");
            hash_tags = (JSONArray) entit.get("hashtags"); 
            Iterator<?> iter = hash_tags.iterator();
            if(!hash_tags.isEmpty()){
                while(iter.hasNext()){
                    
                    JSONObject ht = (JSONObject)iter.next();
                    String htag =(String) ht.get("text");
                    hash_tags_list.add(htag);
                }
            }
        }
        return new ArrayList<String>(hash_tags_list);        
    }

    public Long getLikedCount(){
        return (Long)tweet.get("favorite_count");
    }
    private ArrayList<String> parseForHashtags(){
        ArrayList<String> hashtags = new ArrayList<String>();
        return hashtags;
    }
}
