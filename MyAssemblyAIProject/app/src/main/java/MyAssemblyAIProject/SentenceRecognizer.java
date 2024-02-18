package MyAssemblyAIProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class SentenceRecognizer {
    void sentenceExtractor() {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        // Read the content from transcription_output.txt
        String inputFile = "src/main/resources/transcription_output.txt";
        String outputFile = "src/main/resources/Extracted_Sentences.txt";
        String text = readFileContent(inputFile);

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> sentences = coreDocument.sentences();

        // Overwrite the content of the file with the recognized sentences
        writeSentencesToFile(sentences, outputFile);
    }

    private static String readFileContent(String filePath) {
        // Read content from the file and return as a string
        try {
            Path path = Paths.get(filePath);
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    void writeSentencesToFile(List<CoreSentence> sentences, String filePath) {
        try (FileWriter writer = new FileWriter(new File(filePath))) {
            // Write each sentence to the file
            for (CoreSentence sentence : sentences) {
                writer.write(sentence.toString() + "\n");
            }
            System.out.println("Sentences written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}