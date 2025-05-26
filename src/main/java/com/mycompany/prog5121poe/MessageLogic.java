/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;

/**
 *
 * @author RC_Student_lab
 */
import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MessageLogic {
    private final List<String> storedMessages = new ArrayList<>();
    private final String jsonFilePath = "stored_messages.json";  

    public List<String> collectMessages(int count) {
        List<String> messages = new ArrayList<>();
        int i = 1;
        while (i <= count) {
            String msg = JOptionPane.showInputDialog("Enter message #" + i + ":");
            if (msg == null) {
                messages.add("[Message cancelled]");
            } else {
                messages.add(msg.trim().isEmpty() ? "[Empty message]" : msg.trim());
            }
            i++;
        }
        return messages;
    }

    public String formatMessages(List<String> messages) {
        StringBuilder formatted = new StringBuilder("You entered the following messages:\n");
        for (int i = 0; i < messages.size(); i++) {
            formatted.append((i + 1)).append(". ").append(messages.get(i)).append("\n");
        }
        return formatted.toString();
    }

    public String checkMessageID() {
        return UUID.randomUUID().toString();
    }

    public boolean checkRecipientCell(String cell) {
        return cell != null && cell.matches("0\\d{9}");
    }

    public String createMessageHash() {
        Random random = new Random();
        int randomDigits = 1000 + random.nextInt(9000); 
        String input = randomDigits + "message";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "Error generating hash.";
        }
    }

    public void sendMessage(String msg) {
        JOptionPane.showMessageDialog(null, "Message sent:\n" + msg);
    }

    public void printMessage(String msg) {
        System.out.println("Printed Message:\n" + msg);
    }

    public void storeMessage(String msg) {
       
        if (msg == null || msg.equals("[Message cancelled]")) {
            System.out.println("Message cancelled or null. Not storing.");
            return;
        }
        if (msg.equals("[Empty message]")) {
            JOptionPane.showMessageDialog(null, "Warning: You entered an empty message.");
        }
        
        sendMessage(msg);      
        storedMessages.add(msg);       
        saveMessagesToJson();
    }

    public int returnTotalMessages() {
        return storedMessages.size();
    }
    
    private void saveMessagesToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(jsonFilePath)) {
            gson.toJson(storedMessages, writer);
            System.out.println("Messages auto-saved to " + jsonFilePath);
        } catch (IOException e) {
            System.err.println("Error saving messages to JSON file: " + e.getMessage());
        }
    }
}

