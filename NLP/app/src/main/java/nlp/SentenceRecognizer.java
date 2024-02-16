package nlp;

import java.util.List;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class SentenceRecognizer {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String text = "An FYI, Dennis is a little under the weather. He sounds terrible. Try not to draw attention to it. I think he's self conscious. Do you mean self conscious? ";
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> sentences = coreDocument.sentences();
        
        for (CoreSentence sentence : sentences) {
            System.out.println(sentence.toString());
        }
    }
}
