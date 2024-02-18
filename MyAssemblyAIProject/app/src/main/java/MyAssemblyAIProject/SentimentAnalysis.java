package MyAssemblyAIProject;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class SentimentAnalysis {
    void sentiment() {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String inputFile = "src/main/resources/Extracted_Sentences.txt";
        String text = readFileContent(inputFile);
        
        // Create a CoreDocument with the entire text as one sentence
        CoreDocument coreDocument = new CoreDocument(text);

        // Annotate the document
        stanfordCoreNLP.annotate(coreDocument);

        // Get the list of sentences (in this case, there is only one)
        List<CoreSentence> sentences = coreDocument.sentences();
        
        // Output the sentiment of the entire text
        for (CoreSentence sentence : sentences) {
            String sentiment = sentence.sentiment();
            System.out.println(sentiment + "   " + sentence);
        }
    }
    String readFileContent(String filePath) {
        // Read content from the file and return as a string
        try {
            Path path = Paths.get(filePath);
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}