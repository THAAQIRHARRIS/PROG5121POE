/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.prog5121poe;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
 */
public class LoginTest {
    
    public LoginTest() {
    }
    
    @BeforeAll
   public static void setUpClass() {
        System.out.println("Setting up test class...");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Tests complete.");
    }

    @Test
    public void testSetFirstName() {
        Login instance = new Login();
        instance.setFirstName("Kyle");
        assertEquals("Kyle", instance.getFirstName());
    }

    @Test
    public void testSetSecondName() {
        Login instance = new Login();
        instance.setSecondName("Steven");
        assertEquals("Steven", instance.getSecondName());
    }

    @Test
    public void testValidateUsername() {
        Login instance = new Login();
        assertTrue(instance.validateUsername("kyl_1"));    
        assertFalse(instance.validateUsername("kyle!!!!!!"));          
    }

    @Test
    public void testValidatePassword() {
        Login instance = new Login();
        assertTrue(instance.validatePassword("Ch&&sec@ke99!"));     
        assertFalse(instance.validatePassword("Password"));         
    }

    @Test
    public void testValidatePhoneNumber() {
        Login instance = new Login();
        assertTrue(instance.validatePhoneNumber("0838968976"));    
        assertFalse(instance.validatePhoneNumber("08677"));        
    }

    @Test
    public void testGetFormattedPhoneNumber() {
        Login instance = new Login();
        instance.setPhoneNumber("0838968976");
        assertEquals("083 896 8976", instance.getFormattedPhoneNumber());  
    }

    @Test
    public void testLogin() {
        Login instance = new Login();
        instance.setUsername("kyl_1");
        instance.setPassword("Ch&&sec@ke99!");
        assertTrue(instance.login("kyl_1", "Ch&&sec@ke99!"));
        assertFalse(instance.login("kyle!!!!", "Password"));
    }

    @Test
    public void testGetFullName() {
        Login instance = new Login();
        instance.setFirstName("Kyle");
        instance.setSecondName("Steven");
        assertEquals("Kyle Steven", instance.getFullName());
    }

    @Test
    public void testIsUsernameCorrect() {
        Login instance = new Login();
        instance.setUsername("kyl_1");
        assertTrue(instance.isUsernameCorrect("kyl_1"));
        assertFalse(instance.isUsernameCorrect("kyle!!!!!"));
    }

    @Test
    public void testIsPasswordCorrect() {
        Login instance = new Login();
        instance.setPassword("Ch&&sec@ke99!");
        assertTrue(instance.isPasswordCorrect("Ch&&sec@ke99!"));
        assertFalse(instance.isPasswordCorrect("Password"));
    }
}
