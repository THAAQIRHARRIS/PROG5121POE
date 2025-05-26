/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.prog5121poe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class MessageLogicTest {

    private MessageLogic logic;

    @Before
    public void setUp() {
        logic = new MessageLogic();
    }

    @Test
    public void testCheckMessageID_UniqueAndValidLength() {
        String id1 = logic.checkMessageID();
        String id2 = logic.checkMessageID();

        assertNotNull("Message ID should not be null", id1);
        assertNotNull("Message ID should not be null", id2);
        assertNotEquals("Message IDs should be unique", id1, id2);
        assertTrue("Message ID should be longer than 10 digits", id1.length() > 10);
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
    }

    @Test
    public void testCreateMessageHash_Format() {
        String hash = logic.createMessageHash();
        assertNotNull("Hash should not be null", hash);
        assertEquals("Hash should be 64 characters long", 64, hash.length());
    }

    @Test
    public void testStoreMessageAndTotalCount() {
        int initial = logic.returnTotalMessages();
        logic.storeMessage("Test 1");
        logic.storeMessage("Test 2");
        int after = logic.returnTotalMessages();

        assertEquals("Should increase by 2 messages", initial + 2, after);
    }

    @Test
    public void testFormatMessages_Formatting() {
        List<String> input = Arrays.asList("Hello", "World");
        String result = logic.formatMessages(input);

        assertTrue("Should contain '1. Hello'", result.contains("1. Hello"));
        assertTrue("Should contain '2. World'", result.contains("2. World"));
    }

    @Test
    public void testPrintMessage_NoException() {
        logic.printMessage("This is a test message to print");
        // No assertion needed unless we redirect output
    }

    @Test
    public void testSendMessage_NoException() {
        logic.sendMessage("This is a test message to send");
        // No assertion needed since it's a GUI dialog
    }
}
