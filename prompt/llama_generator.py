class SignLanguageTranslator:
    def __init__(self, default_text):
        self.default_text = default_text
        self.text = default_text
        self.queue_size = 3
        self.broken_correct_queue = []

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


default_text = "System: You are an AI Sentiment Analyst specialized in interpreting emotions from given sets of sentences. Your task is to predict whether the sentences express panic, fear, negativity, positivity, amusement, sarcasm, jokes, humor, anger or any other relevant emotion. Consider the overall sentiment conveyed by the text and provide the most appropriate label for each set of sentences."

translator = SignLanguageTranslator(default_text)

 
Sentence = "Someone broke into my house last night, I'm terrified"
translator.append_broken_sentence(Sentence)
    
print(translator.text)

Sentiment = "Fear (Authorities: Police,  Ambulance )"
translator.append_correct_sentence(Sentiment)
    
print(translator.text)
