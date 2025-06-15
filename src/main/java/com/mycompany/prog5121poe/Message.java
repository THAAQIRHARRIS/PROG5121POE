/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;

/**
 *
 * @author RC_Student_lab
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;
import org.json.JSONObject;

public class Message {

    private final MessageLogic handler = new MessageLogic();
    private final List<String> sentMessages = new java.util.ArrayList<>();
    private final List<String> disregardMessages = new java.util.ArrayList<>();
    private final List<String> hashMessages = new java.util.ArrayList<>();
    private final List<String> idMessages = new java.util.ArrayList<>();
    private final List<String> recipientMessages = new java.util.ArrayList<>();
    private final List<String> storedMessages = new java.util.ArrayList<>();

    public void showDialog() {
        JFrame frame = new JFrame("Welcome to Quickchat!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton sendBtn = new JButton("Send Messages");
        JButton comingSoonBtn = new JButton("Feature Coming Soon");
        JButton functionsBtn = new JButton("View Reports & Search");
        JButton exitBtn = new JButton("Exit");

        sendBtn.addActionListener(e -> sendMessages());
        comingSoonBtn.addActionListener(e -> showComingSoonMessage());
        functionsBtn.addActionListener(e -> showFunctionsMenu());
        exitBtn.addActionListener(e -> exitApp());

        panel.add(sendBtn);
        panel.add(comingSoonBtn);
        panel.add(functionsBtn);
        panel.add(exitBtn);

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(550, 150));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void sendMessages() {
        String recipient = JOptionPane.showInputDialog("Enter recipient's SA cellphone number (e.g., 0821234567):");
        if (recipient == null || !handler.checkRecipientCell(recipient)) {
            JOptionPane.showMessageDialog(null, "Invalid SA cellphone number. Aborting message sending.");
            return;
        }

        String input = JOptionPane.showInputDialog("Enter how many messages you want to enter:");
        if (input == null) return;

        int numMessages;
        try {
            numMessages = Integer.parseInt(input);
            if (numMessages <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid positive number.");
            return;
        }

        List<String> messages = handler.collectMessages(numMessages);

        for (String msg : messages) {
            String id = handler.generateMessageID();
            String hash = handler.createMessageHash();

            String fullMessage = "ID: " + id +
                    "\nHash: " + hash +
                    "\nRecipient: " + recipient +
                    "\nMessage: " + msg + "\n";

            String[] options = {"Send", "Store", "Disregard", "Cancel"};
            int choice = JOptionPane.showOptionDialog(
                    null, fullMessage, "Confirm Message",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (choice) {
                case 0 -> {
                    idMessages.add(id);
                    hashMessages.add(hash);
                    sentMessages.add(msg);
                    recipientMessages.add(recipient);
                    handler.printMessage(fullMessage);
                    handler.sendMessage(fullMessage);
                }
                case 1 -> {
                    idMessages.add(id);
                    hashMessages.add(hash);
                    recipientMessages.add(recipient);
                    storedMessages.add(msg);
                    handler.storeMessage(id, hash, recipient, msg);
                    JOptionPane.showMessageDialog(null, "Message stored.");
                }
                case 2 -> {
                    disregardMessages.add(msg);
                    JOptionPane.showMessageDialog(null, "Message disregarded. Skipping to next.");
                }
                case 3, -1 -> {
                    JOptionPane.showMessageDialog(null, "Cancelled. Returning to main menu.");
                    return;
                }
            }
        }

        int total = handler.returnTotalMessages();
        JOptionPane.showMessageDialog(null,
                "Total messages stored: " + total + "\n" +
                        "Messages sent: " + sentMessages.size() + "\n" +
                        "Messages disregarded: " + disregardMessages.size());
    }

    private void showFunctionsMenu() {
        String[] options = {
                "Show all sent (sender & recipient)",
                "Longest sent message",
                "Search by message ID",
                "Search by recipient",
                "Delete by hash",
                "Full report",
                "Back to Main Menu"
        };

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Choose an action:", "Message Functions",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> displaySenderRecipient();
                case 1 -> displayLongestSentMessage();
                case 2 -> searchByMessageID();
                case 3 -> searchByRecipient();
                case 4 -> deleteByHash();
                case 5 -> displayFullReport();
                default -> { return; }
            }
        }
    }

    private void displaySenderRecipient() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("Sender: You | Recipient: ")
                    .append(recipientMessages.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void displayLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent.");
            return;
        }

        String longest = sentMessages.stream().max((a, b) -> Integer.compare(a.length(), b.length())).orElse("");
        JOptionPane.showMessageDialog(null, "Longest sent message:\n" + longest);
    }

    private void searchByMessageID() {
        String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
        int index = idMessages.indexOf(searchID);
        if (index >= 0) {
            String result = "Message ID: " + searchID +
                    "\nRecipient: " + recipientMessages.get(index) +
                    "\nMessage: " + sentMessages.get(index);
            JOptionPane.showMessageDialog(null, result);
        } else {
            JOptionPane.showMessageDialog(null, "Message ID not found.");
        }
    }

    private void searchByRecipient() {
    String searchRecipient = JOptionPane.showInputDialog("Enter recipient number:");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < recipientMessages.size(); i++) {
        if (recipientMessages.get(i).equals(searchRecipient)) {
            String msgType = i < sentMessages.size() ? "Sent Message" : "Stored Message";
            String messageContent = i < sentMessages.size() ? sentMessages.get(i) : storedMessages.get(i - sentMessages.size());

            sb.append(msgType).append(":\n")
              .append("ID: ").append(idMessages.get(i)).append("\n")
              .append("Hash: ").append(hashMessages.get(i)).append("\n")
              .append("Recipient: ").append(recipientMessages.get(i)).append("\n")
              .append("Message: ").append(messageContent).append("\n\n");
        }
    }

    JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No messages found.");
}

    private void deleteByHash() {
        String hash = JOptionPane.showInputDialog("Enter hash to delete:");
        int index = hashMessages.indexOf(hash);
        if (index >= 0) {
            sentMessages.remove(index);
            recipientMessages.remove(index);
            hashMessages.remove(index);
            idMessages.remove(index);
            JOptionPane.showMessageDialog(null, "Message deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "Hash not found.");
        }
    }

    private void displayFullReport() {
        StringBuilder sb = new StringBuilder("Full Sent Message Report:\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("ID: ").append(idMessages.get(i)).append("\n")
                    .append("Hash: ").append(hashMessages.get(i)).append("\n")
                    .append("Recipient: ").append(recipientMessages.get(i)).append("\n")
                    .append("Message: ").append(sentMessages.get(i)).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void showComingSoonMessage() {
        JOptionPane.showMessageDialog(null, "This feature is coming soon!");
    }

    private void exitApp() {
        JOptionPane.showMessageDialog(null, "Exiting application. Goodbye!");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Message().showDialog());
    }
}

