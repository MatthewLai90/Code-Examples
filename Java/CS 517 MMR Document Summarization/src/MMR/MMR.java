package MMR;

import SimMetrics.CosineSim;
import SimMetrics.SimMetric;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Multi-Document ranking and summarization program the implements the algorithm set
 * forth in the published report "The Use of MMR, Diversity-Based Reranking for Reordering
 * Documents and Producing Summaries". Allows ranking of documents, not only by their 
 * relevance to a query, but by their marginality to other relevant documents.
 * 
 * To use a new Similarity Metric, simple implement the SimMetric interface within the desired new Similarity Metric
 * and pass it into the MMR constructor.
 * 
 * Any document passed into MMR should already be pre-processed. The documents should be in the form of 
 * Lists of Lists of Strings. The Lists of Strings represent a sentence while the List of List of Strings represent
 * the list of sentences that form a document.
 * 
 * @author Matthew Lai
 */
public class MMR {
    
//<editor-fold defaultstate="collapsed" desc="Utility classes">
    /**
     * Document_Score is a private class to pass and store the pair of a document
     * and its MMR score. Allows for comparison.
     */
    public class Document_Score
    {
        private final List<String> document;
        private final double score;
        public Document_Score(List<String> document, double score)
        {
            this.document = document;
            this.score = score;
        }
        public List<String> getDocument() { return document; }
        public double getScore() { return score; }
    }
    
    
    /**
     * DoubleDoc is a private class that acts as a Document and Query pair for use
     * in a HashMap.
     */
    private class DoubleDoc
    {
        final List<String> doc1;
        final List<String> doc2;
        final int simType;
        public DoubleDoc(List<String> d1, List<String> d2, int sT)
        {
            doc1 = d1;
            doc2 = d2;
            simType = sT;
        }
        
        @Override
        public int hashCode()
        {
            return (37 * (37 * simType + doc1.hashCode()) + doc2.hashCode());
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DoubleDoc other = (DoubleDoc) obj;
//            if (!Objects.equals(this.doc1, other.doc1)) {
//                return false;
//            }
            return (Objects.equals(this.doc2, other.doc2) && Objects.equals(this.doc1, other.doc1))
                    || (Objects.equals(this.doc1, other.doc2) && Objects.equals(this.doc2, other.doc1))
                    && Objects.equals(this.simType, other.simType);
        }
        
        public List<String> getDoc1() { return doc1; }
        public List<String> getDoc2() { return doc2; }
        
        
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Declarations">
    /**
     * ConcurrentHashMap for use in concurrently generating scores. Concurrent function does not provide speedup
     * But storing previously generated scores does provide noticeable speedup.
     */
    private ConcurrentHashMap<DoubleDoc, Double> scores;
    int sim1Type;
    int sim2Type;
    SimMetric sim1;
    SimMetric sim2;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default Constructor uses CosineSim for both similarity metrics.
     */
    public MMR()
    {
        scores = new ConcurrentHashMap<>();
        sim1 = new CosineSim();
        sim2 = new CosineSim();
    }
    
    public MMR(SimMetric sim1, SimMetric sim2)
    {
        this.sim1 = sim1;
        this.sim2 = sim2;
        scores = new ConcurrentHashMap<>();
    }

//</editor-fold>
    
    /**
     * Returns a ranked list given the a list of documents, a query, and a lambda value.
     * The list will be ranked according to the lambda value. A lambda value of 0 will
     * return a list ranked more on diversity while a lambda value of 1 will return a 
     * standard relevance ranked list. It is recommended the lambda value be between 
     * 0 and 1.
     * 
     * @param documentList Documents stored in a list as a list of Strings, representing its words. Words should be stemmed and Stopwords removed before passed in
     * @param query A query represented as a list of Strings, representing query words
     * @param lambda The lambda value to determine MMR search ranking
     * @param omega The omega value is the similarity threshold for a sentence to be added to the retrieved set of documents
     * @param maxResults The maximum size of the returned resulting ranked list.
     * @return The list of ranked sentences, ranked according to the lambda value
     */
    public List<List<String>> rankedList(List<List<String>> documentList, List<String> query, double lambda, double omega, int maxResults)
    {
        ArrayList<List<String>> r_sList = new ArrayList<>();
        for(List<String> sentence : documentList)
        {
            double score = sim1.documentRank(sentence, query);
            if(score > omega) 
            {
                r_sList.add(sentence);
                scores.put(new DoubleDoc(sentence, query, sim1Type), score);
            }
        }
        
        TreeSet<Document_Score> sList = new TreeSet<>(new Comparator<Document_Score>() {
            @Override
            public int compare(Document_Score o1, Document_Score o2) {
                if(o1.getScore() == o2.getScore()) return 1;
                return -Double.compare(o1.getScore(), o2.getScore());
            }
        });
        while(!r_sList.isEmpty())
        {
            Document_Score retrievedDocument = retrieveDocument(r_sList, sList, query, lambda);
            sList.add(retrievedDocument);
            r_sList.remove(retrievedDocument.getDocument());
        }
        
        ArrayList<List<String>> rankedList = new ArrayList<>();
        for(Document_Score ds : sList) 
        {
            rankedList.add(ds.getDocument());
            if(rankedList.size() >= maxResults) break;
        }
        
        return rankedList;
    }
    
    /**
     * Returns a list of Document_Score objects given the a list of documents, a query, and a lambda value.
     * Theis list will be ranked according to the lambda value. A lambda value of 0 will
     * return a list ranked more on diversity while a lambda value of 1 will return a 
     * standard relevance ranked list. It is recommended the lambda value be between 
     * 0 and 1. The Document_Score object returns both the document and its MMR score.
     * 
     * @param documentList Documents stored in a list as a list of Strings, representing its words. Words should be stemmed and Stopwords removed before being passed in
     * @param query A query represented as a list of Strings, representing query words
     * @param lambda The lambda value to determine MMR search ranking
     * @param omega The omega value is the similarity threshold for a sentence to be added to the retrieved set of documents
     * @param maxResults The maximum size of the returned resulting ranked list.
     * @return The list of Document_Scores which contains both the document and its MMR score
     */
    public List<Document_Score> rankedAndList(List<List<String>> documentList, List<String> query, double lambda, double omega, int maxResults)
    {
        ArrayList<List<String>> r_sList = new ArrayList<>();
        for(List<String> sentence : documentList)
        {
            double score = sim1.documentRank(sentence, query);
            if(score > omega) 
            {
                r_sList.add(sentence);
                scores.put(new DoubleDoc(sentence, query, sim1Type), score);
            }
        }
        TreeSet<Document_Score> sList = new TreeSet<>(new Comparator<Document_Score>() {
            @Override
            public int compare(Document_Score o1, Document_Score o2) {
                if(o1.getScore() == o2.getScore()) return 1;
                return -Double.compare(o1.getScore(), o2.getScore());
            }
        });
        while(!r_sList.isEmpty())
        {
            Document_Score retrievedDocument = retrieveDocument(r_sList, sList, query, lambda);
            sList.add(retrievedDocument);
            r_sList.remove(retrievedDocument.getDocument());
        }
        
        ArrayList<Document_Score> rankedList = new ArrayList<>();
        for(Document_Score ds : sList) 
        {
            rankedList.add(ds);
            if(rankedList.size() >= maxResults) break;
        }
        
        return rankedList;
    }
    
    /**
     * Private method that returns a singe document that has the maximum score based
     * on the its relevance to the query and its marginality compared to the 
     * sentences already retrieved.
     * 
     * @param unselectedDocuments A list of documents that will be considered for selection
     * @param selectedDocuments The list of documents already selected
     * @param query The query against which the list of documents will be compared
     * @param lambda The value which will determine relevance versus marginality
     * @return A Document and Score tuple of the highest ranking sentence
     */
    private Document_Score retrieveDocument(List<List<String>> unselectedDocuments, TreeSet<Document_Score> selectedDocuments, List<String> query, double lambda)
    {
        List<String> bestDocument = unselectedDocuments.get(0);
        double maxScore = 0;
        for(List<String> document : unselectedDocuments)
        {
            double score = lambda * (getSim1(document, query) - ((1 - lambda) * maxSim2(document, selectedDocuments)));
            if(score > maxScore) 
            {
                maxScore = score;
                bestDocument = document;
            }
        }
        return new Document_Score(bestDocument, maxScore);
    }
    
    /**
     * Method to retrieve the similarity score between documents if it exists in the HashMap. 
     * If it does not exist in the HashMap, it calculates the similarity between the two
     * parameters, adds it to the HashMap, and returns the value;
     * 
     * @param document One of the two vectors to retrieve the similarity score for
     * @param query The other of the two vectors to retrieve the similarity score for.
     * @return The similarity score
     */
    private double getSim1(List<String> document, List<String> query)
    {
        Double score = scores.get(new DoubleDoc(document, query, sim1Type));
        if(score != null) return score;
        Double newScore = sim1.documentRank(document, query);
        scores.putIfAbsent(new DoubleDoc(document, query, sim1Type), newScore);
        return newScore;
    }
    
    /**
     * Method to retrieve the similarity score between documents if it exists in the HashMap. 
     * If it does not exist in the HashMap, it calculates the similarity between the two
     * parameters, adds it to the HashMap, and returns the value;
     * 
     * @param document1 One of the two vectors to retrieve the similarity score for
     * @param document2 The other of the two vectors to retrieve the similarity score for.
     * @return The similarity score
     */
    private double getSim2(List<String> document1, List<String> document2)
    {
        Double score = scores.get(new DoubleDoc(document1, document2, sim2Type));
        if(score != null) return score;
        Double newScore = sim2.documentRank(document1, document2);
        scores.putIfAbsent(new DoubleDoc(document1, document2, sim2Type), newScore);
        return newScore;
    }
    
    
    /**
     * Private method that is used by retrieveDocument to iterate through the selected
     * documents and returns the score for the document with the greatest similarity
     * to the considered document
     * 
     * @param unselectedDocument The document against which all selected documents will be compared against
     * @param selectedDocuments The list of documents against which the considered document will be compared
     * @return The greatest similarity score between the selected documents and the considered document.
     */
    private double maxSim2(List<String> unselectedDocument, TreeSet<Document_Score> selectedDocuments)
    {
        double maxScore = 0;
        Iterator<Document_Score> it = selectedDocuments.iterator();
        while(it.hasNext())
        {
            double score = getSim2(unselectedDocument, it.next().getDocument());
            if(score > maxScore) maxScore = score;
        }
        return maxScore;
    }
}
