package MyAssemblyAIProject;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptOptionalParams;

public class SpeechToText {
    public static void main(String[] args) {
        AssemblyAI aai = AssemblyAI.builder().apiKey("a3775415656b4412913a152a115c400f").build();
        String url = "https://audio.jukehost.co.uk/3hvIO0VuyDvYZ5mkZvRLuyqZpyrqZPqW";
        var params = TranscriptOptionalParams.builder()
                .speakerLabels(true)
                .build();

        Transcript transcript = aai.transcripts().transcribe(url, params);
        if (transcript.getStatus().equals("error")) {
            System.err.println(transcript.getError());
        }
        StringBuilder fullTranscription = new StringBuilder();

        transcript.getUtterances().ifPresent(utterances ->
            utterances.forEach(utterance ->
                fullTranscription.append("Speaker ")
                                .append(utterance.getSpeaker())
                                .append(": ")
                                .append(utterance.getText())
                                .append("\n"))
        );
        String result = fullTranscription.toString();
        WriteToFile obj = new WriteToFile();
        obj.write(result);


        SentenceRecognizer obj3 = new SentenceRecognizer();
        obj3.sentenceExtractor();

        EmergencySOSSenExtractor obj2 = new EmergencySOSSenExtractor();
        obj2.extracted_lines();

        SentimentAnalysis obj4 = new SentimentAnalysis();
        obj4.sentiment();
    }
}