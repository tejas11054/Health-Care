package com.ensias.healthcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class chatbot extends AppCompatActivity {

    private EditText userInput;
    private TextView chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot);

        userInput = findViewById(R.id.userInput);
        chatHistory = findViewById(R.id.chatHistory);
    }


    public void sendMessage(View view) {
        String userMessage = userInput.getText().toString().trim();
        if (!userMessage.isEmpty()) {
            appendMessage("User: " + userMessage);

            // Call a function to process the user message and generate a bot response
            String botResponse = generateBotResponse(userMessage);
            appendMessage("Bot: " + botResponse);

            // Clear the user input
            userInput.getText().clear();
        }
    }


    private void appendMessage(String message) {
        String currentChatHistory = chatHistory.getText().toString();
        String newChatHistory = currentChatHistory + "\n" + message;
        chatHistory.setText(newChatHistory);
    }


    // Implement your logic here to generate bot responses based on user input
    private String generateBotResponse(String userMessage) {
        if(userMessage.toLowerCase().contains("Headache")) {
            // If the user mentioned headache, respond accordingly
            return "I'm sorry to hear that you have a headache. Have you taken any medication?";
        }
        else if(userMessage.toLowerCase().contains("Stomachache")) {
            // If the user mentioned headache, respond accordingly
            return "I'm here to help. Try sipping on clear liquids, like ginger tea or broth, and stick to bland foods such as crackers or bananas. If the discomfort continues, it's advisable to consult with a healthcare professional for a personalized assessment.\n";
        }
        else if(userMessage.toLowerCase().contains("Legpain")) {
            // If the user mentioned headache, respond accordingly
            return "I'm sorry to hear about your leg pain. Consider gently stretching the affected area, applying a warm or cold compress, and taking over-the-counter pain relievers following the recommended dosage. If the pain persists, it's important to seek medical advice for a thorough evaluation.\n";
        }
        else if(userMessage.toLowerCase().contains("Backpain")) {
            // If the user mentioned headache, respond accordingly
            return " I understand back pain can be challenging. Ensure good posture, use a heating pad or cold pack, and engage in gentle stretching exercises. Over-the-counter pain relievers may also help. If the pain persists or intensifies, it's recommended to consult with a healthcare professional.\n\n";
        }
        else if(userMessage.toLowerCase().contains("Throat Infection")) {
            // If the user mentioned headache, respond accordingly
            return " I'm here to assist. Drink warm tea with honey and lemon, gargle with saltwater, and stay hydrated. Throat lozenges and over-the-counter pain relievers can provide relief. If the sore throat persists, it's advisable to consult with a healthcare professional for a proper assessment.\n\n\n";
        }
        else if(userMessage.toLowerCase().contains("Menstral Cramp")) {
            // If the user mentioned headache, respond accordingly
            return "I'm sorry to hear you're experiencing discomfort. Consider using a heating pad, taking over-the-counter pain relievers like ibuprofen, and staying physically active. If the cramps are severe or persistent, it's important to consult with a healthcare provider for personalized guidance.\n";
        }
        else if(userMessage.toLowerCase().contains("Stress")) {
            // If the user mentioned headache, respond accordingly
            return "I understand managing stress is important. Practice deep breathing, meditation, or yoga. Engage in activities you enjoy, ensure sufficient sleep.";
        }
        else if(userMessage.toLowerCase().contains("Painful Joints")) {
            // If the user mentioned headache, respond accordingly
            return " I'm sorry to hear about your joint pain. Engage in low-impact exercises like swimming, apply heat or cold packs, and maintain a healthy weight. If the pain persists, it's important to consult with a healthcare professional for a thorough evaluation.";
        }
        else if(userMessage.toLowerCase().contains("Cut")) {
            // If the user mentioned headache, respond accordingly
            return "I'm sorry to hear about the cut. Clean the wound with mild soap and water, apply an antiseptic ointment, and cover it with a sterile bandage. If the cut is deep or shows signs of infection, seek medical attention for proper care.\n";
        }
        else if(userMessage.toLowerCase().contains("Difficulty in Breathing")) {
            // If the user mentioned headache, respond accordingly
            return "I'm here to help. If you're experiencing difficulty breathing, sit upright, practice slow and deep breathing, and seek immediate medical attention. It could be a serious issue that requires prompt evaluation by a healthcare professional.\n";
        }
        else if(userMessage.toLowerCase().contains("Cough")) {
            // If the user mentioned headache, respond accordingly
            return "I understand a persistent cough can be bothersome. Stay hydrated, use cough drops, and consider inhaling steam. If the cough persists for more than a week or is accompanied by other symptoms, consult with a healthcare professional for further evaluation.\n";
        }
        else if(userMessage.toLowerCase().contains("Urinary tract infection")) {
            // If the user mentioned headache, respond accordingly
            return "I'm here to assist. Drink plenty of water, avoid irritants like caffeine, and consider over-the-counter urinary pain relievers. However, it's crucial to consult with a healthcare professional for a proper diagnosis and treatment.\n\n";
        }
        else if(userMessage.toLowerCase().contains("Skin rash")) {
            // If the user mentioned headache, respond accordingly
            return "I'm here to help. Keep the area clean, apply a gentle moisturizer, and avoid scratching. Over-the-counter hydrocortisone cream may provide relief. If the rash persists or spreads, consult with a healthcare professional for proper diagnosis and treatment.\n\n";
        }
        else if(userMessage.toLowerCase().contains("Symptoms of diabetes")) {
            // If the user mentioned headache, respond accordingly
            return "I'm here to assist. Controlling blood sugar through diet, oral medication or insulin is the main treatment.\n" +
                    "Diabetes symptoms include increased thirst, frequent urination, and unexplained weight loss.\n";
        }
        else if(userMessage.toLowerCase().contains("Migraine")) {
            // If the user mentioned headache, respond accordingly
            return "Migraine management may involve rest in a dark, quiet room, staying hydrated, and taking prescribed medications. Consult with a healthcare provider for personalized treatment.\n\n";
        }
        else if(userMessage.toLowerCase().contains("Symptoms of Flu")) {
            // If the user mentioned headache, respond accordingly
            return "I'm here to assist.Flu is primarily treated with rest and fluid intake to allow the body to fight the infection on its own. Paracetamol may help cure the symptoms but NSAIDs should be avoided.\n" +
                    "Flu symptoms include fever, chills, cough, sore throat, muscle aches, fatigue, and sometimes nausea. It's recommended to get a flu vaccine for prevention.\n\n";
        }
        return "Mc nit input taak";
    }

}
