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

public class Message {

    private final MessageLogic handler = new MessageLogic();

    public void showDialog() {
        JFrame frame = new JFrame("Welcome to Quickchat!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // horizontal layout with spacing

        JButton sendBtn = new JButton("Send Messages");
        JButton comingSoonBtn = new JButton("Feature Coming Soon");
        JButton exitBtn = new JButton("Exit");

        sendBtn.addActionListener(e -> sendMessages());
        comingSoonBtn.addActionListener(e -> showComingSoonMessage());
        exitBtn.addActionListener(e -> exitApp());

        panel.add(sendBtn);
        panel.add(comingSoonBtn);
        panel.add(exitBtn);

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(500, 150)); // optional size
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
            String id = handler.checkMessageID();
            String hash = handler.createMessageHash();

            String fullMessage = "Message ID: " + id + "\n"
                    + "Recipient: " + recipient + "\n"
                    + "Message: " + msg + "\n"
                    + "Hash: " + hash;

            handler.storeMessage(fullMessage);
            handler.printMessage(fullMessage);
            handler.sendMessage(fullMessage);
        }

        int total = handler.returnTotalMessages();
        JOptionPane.showMessageDialog(null, "All messages sent.\nTotal messages stored: " + total);
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
