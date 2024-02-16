package MyAssemblyAIProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmergencySOSExtractor {

    void extracted_lines() {
        try {
            extractEmergencySentences("src/main/resources/Extracted_Sentences.txt", "src/main/resources/harmful_sentences.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractEmergencySentences(String inputFilePath, String outputFilePath) throws IOException {
        Set<String> harmfulWords = new HashSet<>(Arrays.asList("Fire", "Bomb", "Explosion", "Gun", "Attack", "Murder",
                "Violence", "Threat", "Terrorist", "Hostage", "Kidnap", "Rape", "Assault", "Robbery", "Hazard",
                "Chemical", "Biological", "Nuclear", "War", "Riot", "Protest", "Sabotage", "Hijack", "Shoot", "Stab",
                "Strangle", "Abduction", "Harm", "Danger", "Emergency", "Crisis", "Trauma", "Slaughter", "Execute",
                "Massacre", "Genocide", "Suicide", "Homicide", "Injure", "Disrupt", "Kill", "Collapsing"));

        List<String> allLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 2);
                if (parts.length == 2) {
                    line = parts[1];
                }

                String formattedLine = line;

                allLines.add(formattedLine);

                if (containsHarmfulWord(line, harmfulWords)) {
                    // Overwrite the output file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                        for (String harmfulLine : allLines) {
                            if (containsHarmfulWord(harmfulLine, harmfulWords)) {
                                writer.write(harmfulLine);
                                writer.newLine();
                            }
                        }
                    }
                }
            }
        }
    }
    private static boolean containsHarmfulWord(String sentence, Set<String> harmfulWords) {
        for (String word : harmfulWords) {
            if (sentence.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}