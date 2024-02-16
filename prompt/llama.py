import requests
import json

class SignLanguageTranslator:
    def __init__(self, default_text):
        self.default_text = default_text
        self.text = default_text
        self.queue_size = 15
        self.broken_correct_queue = []



    @staticmethod 
    def data():
        return  {
            "stream": True,
            "n_predict": 21,
            "temperature": 0.08,
            "stop": ["</s>", "Sentiment:", "Sentence:"],
            "api_key": "",
            "cache_prompt": True,
            "frequency_penalty": 0,
            "grammar": "",
            "image_data": [],
            "min_p": 0.05,
            "mirostat": 0,
            "mirostat_eta": 0.1,
            "mirostat_tau": 5,
            "n_probs": 0,
            "presence_penalty": 0,
            "prompt": "You are an AI Sentiment Analyst specialized in interpreting emotions from given sentence. Your task is to predict whether the sentence express panic, fear, negativity, positivity, amusement, sarcasm, jokes, humor, anger or any other relevant emotion. Consider the overall sentiment conveyed by the text and provide the most appropriate label for each set of sentences.\nSentence : Someone broke into my house last night, I'm terrified\nSentiment: Fear (Authorities: Police,  Ambulance )\nSentence: Yikes, I just spilled coffee all over my laptop!\nSentiment: Frustration (Authorities: None)\nSentence: There's smoke coming from the kitchen,I think there's a fire\nSentiment: Panic (Authorities: Fire Brigade,  Ambulance )\nSentence: Seriously, why does everything always go wrong for me?\nSentiment: Negativity (Authorities: None)\nSentence : If this storm gets any worse, we might have to evacuate.\nSentiment: Concern (Authorities: Fire Brigade,  Ambulance, Storm Authority)\nSentence: Help me! I'm trapped under the rubble.\nSentiment: Panic (Authorities: Fire Brigade,  Ambulance )\nSentence: My hands are up, please don't shoot!\nSentiment: Fear (Authorities: Police,  Ambulance )\nSentence: I will kill you if you don't give me your wallet.\nSentiment: Threatening (Authorities: Fire Brigade,  Ambulance )\nSentence: Call 911, there's been a serious accident.\nSentiment: Urgency (Authorities: Police, Ambulance, Fire Brigade)\nSentence: Quick, grab the first aid kit, there's been an accident!\nSentiment: Urgency (Authorities: Fire Brigade, Ambulance, Police)\nSentence: Help! I can't find my child in the crowd!\nSentiment: Panic (Authorities: Police )\nSentence: I need backup, the suspect is armed and dangerous!\nSentiment: Urgency (Authorities: Police, Ambulance )\nSentence: We have to get out of here, the building is collapsing!\nSentiment: Fear (Authorities: Fire Brigade, Ambulance )\nSentence: Stay low, the shooter is still in the building.\nSentiment: Alert (Authorities: Police,  Ambulance )\nSentence: The puppy chewed up my favorite shoes, now I'm devastated.\nSentiment: Sadness (Authorities: None)\nSentence: hey, dont shoot. my hands are up\nSentiment:",
            "repeat_last_n": 256,
            "repeat_penalty": 1.18,
            "slot_id": -1,
            "top_k": 40,
            "top_p": 0.95,
            "typical_p": 1
            }

    def append_broken_sentence(self, broken):

        if len(self.broken_correct_queue) == self.queue_size:
            self.remove_oldest_pair()
        self.broken_correct_queue.append((broken, None))
        self.update_text()

    def append_correct_sentence(self, correct):
        if not self.broken_correct_queue:
            return  
        self.broken_correct_queue[-1] = (self.broken_correct_queue[-1][0], correct)
        self.update_text()

    def remove_oldest_pair(self):
        self.broken_correct_queue.pop(0)

    def update_text(self):
        self.text = self.default_text
        for pair in self.broken_correct_queue:
            if pair[0] is not None and pair[1] is not None:
                self.text += f"\nSentence: {pair[0]}\nSentiment: {pair[1]}"
            elif pair[0] is not None:
                self.text += f"\nSentence: {pair[0]}"
            elif pair[1] is not None:
                self.text += f"\nSentiment: {pair[1]}"

    def translate(self, Sentence):
        self.append_broken_sentence(Sentence)
        nprediction = 15

        data = self.data()
        data["prompt"] = self.text + "\nSentiment:"
        data["n_predict"] = nprediction

        response = requests.post("http://127.0.0.1:8080/completion", json=data)

        response_text = response.text

        # Extract content from each line until "\n" is encountered
        contents = []
        for line in response_text.split('\n'):
            if line.strip().startswith('data: '):
                try:
                    data = json.loads(line.split('data: ')[1])
                    content = data['content']
                    if content == "":
                        break
                    contents.append(content)
                except json.JSONDecodeError:
                    pass

        # Join the contents excluding '\n'
        extracted_text = ''.join(contents).replace("\\n", "")
        print(extracted_text)
        self.append_correct_sentence(extracted_text)

        return extracted_text
        
        
text = "You are an AI Sentiment Analyst specialized in interpreting emotions from given sets of sentences. Your task is to predict whether the sentences express panic, fear, negativity, positivity, amusement, sarcasm, jokes, humor, anger or any other relevant emotion. Consider the overall sentiment conveyed by the text and provide the most appropriate label for each set of sentences."
text += '''\nSentence : Someone broke into my house last night, I'm terrified
Sentiment: Fear (Authorities: Police, Ambulance)
Sentence: Yikes, I just spilled coffee all over my laptop!
Sentiment: Frustration (Authorities: None)
Sentence: There's smoke coming from the kitchen,I think there's a fire
Sentiment: Panic (Authorities: Fire Brigade, Ambulance)
Sentence: Seriously, why does everything always go wrong for me?
Sentiment: Negativity (Authorities: None)
Sentence : If this storm gets any worse, we might have to evacuate.
Sentiment: Concern (Authorities: Fire Brigade, Ambulance, Storm Authority)
Sentence: Help me! I'm trapped under the rubble.
Sentiment: Panic (Authorities: Fire Brigade, Ambulance)
Sentence: My hands are up, please don't shoot!
Sentiment: Fear (Authorities: Police, Ambulance)
Sentence: I will kill you if you don't give me your wallet.
Sentiment: Threatening (Authorities: Fire Brigade, Ambulance)
Sentence: Call 911, there's been a serious accident.
Sentiment: Urgency (Authorities: Police, Ambulance, Fire Brigade)
Sentence: Quick, grab the first aid kit, there's been an accident!
Sentiment: Urgency (Authorities: Fire Brigade, Ambulance, Police)
Sentence: Help! I can't find my child in the crowd!
Sentiment: Panic (Authorities: Police)
Sentence: I need backup, the suspect is armed and dangerous!
Sentiment: Urgency (Authorities: Police, Ambulance)
Sentence: We have to get out of here, the building is collapsing!
Sentiment: Fear (Authorities: Fire Brigade, Ambulance)
Sentence: Stay low, the shooter is still in the building.
Sentiment: Alert (Authorities: Police, Ambulance)
Sentence: The puppy chewed up my favorite shoes, now I'm devastated.
Sentiment: Sadness (Authorities: None)'''

translator = SignLanguageTranslator(text)

for i in range(2):
    input_sentence = input("Enter : ")
    translator.translate(input_sentence)
print(translator.text)