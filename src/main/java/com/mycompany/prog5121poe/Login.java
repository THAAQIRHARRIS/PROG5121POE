/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;

/**
 *
 * @author RC_Student_lab
 */
public class Login {
   private String username;
    private String password;
    private String firstname;
    private String secondname;
    private String phoneNumber;


    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public void setSecondName(String secondname) {
        this.secondname = secondname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getSecondName() {
        return secondname;
    }

    public String getFullName() {
        return firstname + " " + secondname;
    }

    public String getFormattedPhoneNumber() {// used AI for this part of question
        
        if (phoneNumber != null && phoneNumber.length() == 10) {
            return phoneNumber.substring(0, 3) + " " +
                   phoneNumber.substring(3, 6) + " " +
                   phoneNumber.substring(6);
        }
        return phoneNumber;
    }

    public boolean validateUsername(String username) {
        if (username.length() >= 5 && username.contains("_")) {
            this.username = username;
            return true;
        }
        return false;
    }

    public boolean validatePassword(String password) {
        if (password.length() >= 8 &&
            password.matches(".*\\d.*") &&                
            password.matches(".*[A-Z].*") &&              
            password.matches(".*[^a-zA-Z0-9].*")) {        
            this.password = password;
            return true;
        }
        return false;
    }

    public boolean validatePhoneNumber(String number) { // used AI for this part of question
        if (number.matches("\\d{10}")) {
            this.phoneNumber = number;
            return true;
        }
        return false;
    }
    
    public boolean login(String userInput, String passInput) {
        return userInput.equals(username) && passInput.equals(password);
    }

    public boolean isUsernameCorrect(String userInput) {
        return userInput.equals(username);
    }

    public boolean isPasswordCorrect(String passInput) {
        return passInput.equals(password);
    }
}    

