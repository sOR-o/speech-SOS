package nlp;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class SentimentAnalysis {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        String text = "RUN RUN! Fire! Keep Running";
        
        // Create a CoreDocument with the entire text as one sentence
        CoreDocument coreDocument = new CoreDocument(text);

        // Annotate the document
        stanfordCoreNLP.annotate(coreDocument);

        // Get the list of sentences (in this case, there is only one)
        List<CoreSentence> sentences = coreDocument.sentences();
        
        // Output the sentiment of the entire text
        for (CoreSentence sentence : sentences) {
            String sentiment = sentence.sentiment();
            System.out.println(sentiment + "\t" + sentence);
        }
    }
}
