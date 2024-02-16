package MyAssemblyAIProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    void write(String result) {
        String fileName = "transcription_output.txt";
        String directoryPath = "src/main/resources/";
        String filePath = directoryPath + fileName;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(result);
            System.out.println("Transcription has been written to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing transcription to file: " + e.getMessage());
        }
    }
}