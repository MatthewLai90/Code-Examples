

package Parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Parser {
    
    HashMap<List<String>, String> stemMap;
    List<List<String>> stemList;
    static HashSet<String> stopwords = new HashSet<>();
    
    /**
     * Constructor takes a file and parses the file. 
     * 
     * @param f File from which the parser reads the data.
     * @throws FileNotFoundException 
     * @throws IOException 
     */
    public Parser(File f) throws FileNotFoundException, IOException
    {
        stopwords = new HashSet<>();
        stemMap = new HashMap<>();
        stemList = new ArrayList<>();
        addDocument(f);
    }
    
    /**
     * Default constructor. Initializes data but does not stem and store any documents.
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Parser() throws FileNotFoundException, IOException
    {
        stemMap = new HashMap<>();
        stemList = new ArrayList<>();
    }
    
    /**
     * Adds a file to the StemMap and StemList
     * 
     * @param f The file from which the parser reads the data.
     * @throws IOException 
     */
    public void addDocument(File f) throws IOException
    {
        String document = "";
        BufferedReader io = new BufferedReader(new FileReader(f));
        String line;
        boolean markUpFile = false, captureText = false;
        while((line = io.readLine()) != null)
        {
            if(line.startsWith("<DOC>")) markUpFile = true;
            else if(!markUpFile) captureText = true;
            if(markUpFile && line.startsWith("<TEXT>"))
            {
                captureText = true;
                continue;
            }
            if(markUpFile && line.startsWith("</TEXT>")) captureText = false;
            if(!captureText) continue;
            
            document += line + " ";
            
            if(line.equals("\n"))
            {
                stemDocument(document);
                document = "";
            }
        }
        stemDocument(document);
    }
    
    /**
     * Parses document into sentences, stems, and removes stops words before adding it to hashmaps and lists to be used by the MMR and other Sim
     * classes
     * 
     * @param document the target document to be parsed and stemmed.
     */
    private void stemDocument(String document)
    {
        document = document.replaceAll("<P>|</P>", "");
        document = document.trim();
        for(String sentence : document.split("(?<=\\w[\\w\\)\\]\"](?<!Mrs?|Dr|Rev|Mr|Ms|vs|abd|ABD|Abd|resp|St|wt)[\\.\\?\\!\\@\\_][\\\"']{0,2}\\s)"))
        {
            sentence = sentence.trim();
            sentence = sentence.replaceFirst("^[A-Z- ]+,?[ ]?[a-zA-Z]*?[ ]?(?:\\([A-Z][a-zA-Z ]*\\))? _", "");
            sentence = sentence.trim();
            if(sentence.length() < 1) continue;
//            ArrayList<String> sentVect = new ArrayList<String>();
            ArrayList<String> stemmedSentVect = new ArrayList<String>();
            for(String word : sentence.replaceAll("[^\\w ]", "").split("\\s")) 
            {
                if(word.replaceAll("\\s\n", "").length() < 1) continue;
                word = word.replaceAll("[^\\w]", "");
//                sentVect.add(word);
                if(!stopwords.contains(word)) stemmedSentVect.add(Stemmer.stemWord(word));
            }
            if(stemmedSentVect.size() > 0)
            {
                stemList.add(stemmedSentVect);
                stemMap.put(stemmedSentVect, sentence.trim());
            }
        }
    }
    
    /**
     * Stems an input string and returns the sentence as a List of strings.
     * 
     * @param sentence The sentence that you want to stem.
     * @return A stemmed List of String representation of the input sentence
     */
    public static List<String> vectorAndStem(String sentence)
    {
        ArrayList<String> stemmedSentVect = new ArrayList<String>();
        for(String word : sentence.replaceAll("[^\\w ]", "").split("\\s")) 
        {
            word = word.replaceAll("[^\\w]", "");
            if(!stopwords.contains(word.trim())) stemmedSentVect.add(Stemmer.stemWord(word));
        }
        
        
        return stemmedSentVect;
    }
    
    /**
     * Returns the stemmed documents in a List of List of Strings form.
     * 
     * @return The List of List of Strings representation of the parsed documents.
     */
    public List<List<String>> getStemmedDocument()
    {
        return stemList;
    }
    
    /**
     * Returns the original sentence based on the List of String representation of the
     * sentence.
     * 
     * @param stemmedVector The stemmed and vector representation of the sentence.
     * @return The original un-stemmed String representation of the sentence.
     */
    public String getSentence(List<String> stemmedVector)
    {
        return stemMap.get(stemmedVector);
    }

    public static void populateStopwords(File f) throws FileNotFoundException {
        Scanner io = new Scanner(f);
        
        while(io.hasNextLine()) stopwords.add(Stemmer.stemWord(io.nextLine()));
    }
}
