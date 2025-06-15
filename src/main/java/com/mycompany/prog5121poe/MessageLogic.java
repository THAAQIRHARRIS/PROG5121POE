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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MessageLogic {
    private final List<String> storedMessages = new ArrayList<>();
    private final String jsonFilePath = "messages.json";

    public List<String> collectMessages(int count) {
        List<String> messages = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            String msg = JOptionPane.showInputDialog("Enter message #" + i + " (max 250 chars):");

            if (msg == null || msg.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Message is empty or cancelled. Please enter a valid message.");
                i--; // Try again
                continue;
            }

            msg = msg.trim();
            if (msg.length() > 250) {
                JOptionPane.showMessageDialog(null, "Message too long! Limit is 250 characters.");
                i--;
                continue;
            }

            messages.add(msg);
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

    public String generateMessageID() {
        Random rand = new Random();
        StringBuilder idBuilder = new StringBuilder();

        idBuilder.append(rand.nextInt(9) + 1); 
        for (int i = 0; i < 9; i++) {
            idBuilder.append(rand.nextInt(10));
        }

        return idBuilder.toString();
    }

    public boolean checkRecipientCell(String cell) {
        return cell != null && cell.matches("0\\d{9}");
    }

    public String createMessageHash() {
        try {
            String input = System.nanoTime() + "message";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.substring(0, 10); 
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not found", e);
        }
    }

    public void sendMessage(String msg) {
        JOptionPane.showMessageDialog(null, "Message sent:\n" + msg);
    }

    public void printMessage(String msg) {
        System.out.println("Printed Message:\n" + msg);
    }

    public void storeMessage(String messageID, String messageHash, String recipient, String message) {
        JSONObject obj = new JSONObject();
        obj.put("messageID", messageID);
        obj.put("messageHash", messageHash);
        obj.put("recipient", recipient);
        obj.put("message", message);

        storedMessages.add(message);

        try (FileWriter file = new FileWriter(jsonFilePath, true)) {
            file.write(obj.toString(4));
            file.write(System.lineSeparator());
            System.out.println("Message saved to " + jsonFilePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to store message: " + e.getMessage());
        }
    }

    public List<JSONObject> readStoredMessages() {
        List<JSONObject> messages = new ArrayList<>();

        try (FileReader reader = new FileReader(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            while (tokener.more()) {
                Object obj = tokener.nextValue();
                if (obj instanceof JSONObject jsonObj) {
                    messages.add(jsonObj);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to read messages from file: " + e.getMessage());
        }

        return messages;
    }

    public int returnTotalMessages() {
        return storedMessages.size();
    }
}


