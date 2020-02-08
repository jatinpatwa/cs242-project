import java.io.*;
import java.util.ArrayList;
import java.io.FileWriter;

import java.util.*;

import utils.*;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;

import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;

import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.commons.cli.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class searcher{
    private String indexDir = null ;
    IndexSearcher isearch;
    private TopDocs results = null;

    public static void main(String[] args) throws IOException, ParseException{
        ParseSearch ps = new ParseSearch();
        ParseSearch.parse(args);
        String indexField = ParseSearch.indexField;
        String queryTerm = ParseSearch.query;
        
        Constants constants = new Constants();
        searcher s = new searcher();
        ArrayList<String> indexFieldList = Constants.indexFieldList;

        TopDocs res = s.search( queryTerm, indexField);
       
        int count = 1;
    
            System.out.println(s.resultsToJson());
    }
    
    public TopDocs search(String searchQuery, String indexField) throws IOException, ParseException{
        this.indexDir = new File(".").getCanonicalPath()+ "/indices/";
        Directory index_dir = FSDirectory.open( new File(this.indexDir).toPath() );
        IndexReader reader = DirectoryReader.open(index_dir); 


        isearch = new IndexSearcher(reader); 
        QueryParser qparser = new QueryParser(indexField, new StandardAnalyzer() );
        Query query = qparser.parse(searchQuery);
        
        int max_results = 10;
        this.results = isearch.search(query, max_results);
        //postResults();
        return this.results;
    }

    public Document getDocument(ScoreDoc sd) throws IOException{
        return isearch.doc(sd.doc);
    }


    @SuppressWarnings("unchecked")
	public String resultsToJson()throws IOException{
        JSONArray resultsArray = new JSONArray();
        if (this.results != null) {
            ScoreDoc[] hits = this.results.scoreDocs;
            for(int rank = 0; rank < hits.length; rank++){
                JSONObject obj = new JSONObject();
                Document hitdoc = getDocument(hits[rank]);
                obj.put("rank", (rank +1));
                obj.put("score", hits[rank].score);
                obj.put("text", hitdoc.get("text"));
                obj.put("username", hitdoc.get("username"));
                obj.put("timestamp", hitdoc.get("timestamp"));
                obj.put("hashtags", hitdoc.get("hashtags"));
                obj.put("links", hitdoc.get("links"));
                obj.put("userimageurl", hitdoc.get("userimageurl"));
                obj.put("likedcount", hitdoc.get("likedcount"));
                obj.put("location", hitdoc.get("location"));
                obj.put("coords", hitdoc.get("coords"));
                resultsArray.add(obj);
            }
        }
        JSONObject res = new JSONObject();
        res.put("results",resultsArray);
        return res.toString();
    }

    @SuppressWarnings("unchecked")
	public void postResults() throws IOException{
        JSONArray resultsArray = new JSONArray();
        if (this.results != null){
            ScoreDoc[] hits = this.results.scoreDocs;
            for(int rank = 0; rank < hits.length; rank++){
                JSONObject obj = new JSONObject();
                Document hitdoc = getDocument(hits[rank]);
                obj.put("rank", (rank +1));
                obj.put("score", hits[rank].score);
                obj.put("text", hitdoc.get("text"));
                obj.put("username", hitdoc.get("username"));
                obj.put("timestamp", hitdoc.get("timestamp"));
                obj.put("hashtags", hitdoc.get("hashtags"));
                obj.put("links", hitdoc.get("links"));
                resultsArray.add(obj);
            }
              
//          FileWriter file = new FileWriter("./results/results.txt");
            JSONObject res = new JSONObject();
            res.put("results", resultsArray);
//          file.write(res.toString());
        }
    }
}