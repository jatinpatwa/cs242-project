package utils;
import org.apache.commons.cli.*;


public class ParseSearch {
    public static String query = null;
    public static String indexField = null;

    public static void parse(String[] args){
        Options options = new Options();

        Option q = new Option("q","query",true,"Provide query string to search for");
        q.setRequired(true);
        options.addOption(q);

        Option iField = new Option("f","indexField",true,"index field to be searched against");
        iField.setRequired(true);
        options.addOption(iField);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try{
            cmd = parser.parse(options, args);
        }
        catch(ParseException pe){
            System.out.println(pe.getMessage());
            formatter.printHelp("Lucene Twitter Search Parser",options);
            System.exit(1);
            return;
        }
        query = cmd.getOptionValue("query");
        indexField = cmd.getOptionValue("indexField");
    }
}