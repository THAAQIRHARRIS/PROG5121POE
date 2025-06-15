/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.prog5121poe;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MessageLogicTest {

    private MessageLogic logic;

    @Before
    public void setUp() {
        logic = new MessageLogic();
    }

    @Test
    public void testGenerateMessageID_UniqueAndTenDigits() {
        String id1 = logic.generateMessageID();
        String id2 = logic.generateMessageID();

        assertNotNull("Message ID should not be null", id1);
        assertNotNull("Message ID should not be null", id2);
        assertNotEquals("Message IDs should be unique", id1, id2);
        assertEquals("Message ID should be exactly 10 digits", 10, id1.length());
        assertTrue("Message ID should be numeric", id1.matches("\\d{10}"));
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        assertTrue("Valid SA number should pass", logic.checkRecipientCell("0821234567"));
    }

    @Test
    public void testCheckRecipientCell_InvalidCases() {
        assertFalse("Too short", logic.checkRecipientCell("123456789"));
        assertFalse("Too long", logic.checkRecipientCell("08212345678"));
        assertFalse("Non-numeric", logic.checkRecipientCell("a821234567"));
        assertFalse("Null input", logic.checkRecipientCell(null));
        assertFalse("Starts with wrong digit", logic.checkRecipientCell("1721234567"));
        assertFalse("Has space", logic.checkRecipientCell("082 1234567"));
    }

    @Test
    public void testCreateMessageHash_Format() {
        String hash = logic.createMessageHash();
        assertNotNull("Hash should not be null", hash);
        assertEquals("Hash should be 10 characters long", 10, hash.length());
        assertTrue("Hash should be hexadecimal", hash.matches("[0-9a-fA-F]{10}"));
    }

    @Test
    public void testStoreMessageAndTotalCount() {
        int initial = logic.returnTotalMessages();

        String id = logic.generateMessageID();
        String hash = logic.createMessageHash();
        String recipient = "0821234567";
        String message = "Test message";

        logic.storeMessage(id, hash, recipient, message);

        int after = logic.returnTotalMessages();
        assertEquals("Should increase by 1 message", initial + 1, after);
    }

    @Test
    public void testStoreMessage_WritesValidJson() {
        String id = logic.generateMessageID();
        String hash = logic.createMessageHash();
        String recipient = "0829876543";
        String message = "Another test message";

        logic.storeMessage(id, hash, recipient, message);

        List<JSONObject> allMessages = logic.readStoredMessages();
        JSONObject lastMsg = allMessages.get(allMessages.size() - 1);

        assertEquals("ID should match", id, lastMsg.getString("messageID"));
        assertEquals("Hash should match", hash, lastMsg.getString("messageHash"));
        assertEquals("Recipient should match", recipient, lastMsg.getString("recipient"));
        assertEquals("Message should match", message, lastMsg.getString("message"));
    }

    @Test
    public void testReadStoredMessages_ReturnsList() {
        List<JSONObject> messages = logic.readStoredMessages();
        assertNotNull("Should return a list, even if empty", messages);
        for (JSONObject obj : messages) {
            assertTrue("Each item should be a JSONObject", obj instanceof JSONObject);
        }
    }

    @Test
    public void testFormatMessages_Formatting() {
        List<String> input = Arrays.asList("Hello", "World");
        String result = logic.formatMessages(input);

        assertTrue("Should contain '1. Hello'", result.contains("1. Hello"));
        assertTrue("Should contain '2. World'", result.contains("2. World"));
    }

    @Test
    public void testReturnTotalMessages_ReflectsStoredList() {
        int before = logic.returnTotalMessages();
        logic.storeMessage(
            logic.generateMessageID(),
            logic.createMessageHash(),
            "0839999999",
            "Message to test total count"
        );
        int after = logic.returnTotalMessages();

        assertEquals("Should reflect one additional message", before + 1, after);
    }

    @Ignore("Disabled in CI due to GUI dialog")
    @Test
    public void testSendMessage_NoException() {
        logic.sendMessage("Test message for GUI dialog");
    }

    @Test
    public void testPrintMessage_NoException() {
        logic.printMessage("Console output test message");
    }
}


