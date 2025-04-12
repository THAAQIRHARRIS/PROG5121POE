/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121poe;

/**
 *
 * @author RC_Student_lab
 */
import java.util.Scanner;
public class PROG5121POE {

    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        Login validator = new Login();

        System.out.println("------------------ REGISTRATION INFO -----------------------");
        System.out.println("please enter the following infomation to create an account: ");
        System.out.print("Enter your firstname: ");
        validator.setFirstName(scanner.nextLine());

        System.out.print("Enter your secondname: ");
        validator.setSecondName(scanner.nextLine());

        
        while (true) {
            System.out.print("Enter your username: ");
            String inputUsername = scanner.nextLine();

            if (validator.validateUsername(inputUsername)) {
                System.out.println("Username is valid.");
                break;
            } else {
                System.out.println("Username is invalid. Must be at least 5 characters and contain '_'.");
            }
        }

        
        while (true) {
            System.out.print("Enter a password: ");
            String inputPassword = scanner.nextLine();

            if (validator.validatePassword(inputPassword)) {
                System.out.println("Password is valid.");
                break;
            } else {
                System.out.println("Password is invalid. Must be at least 8 characters, contain an uppercase letter, digit, and special character.");
            }
        }

        
        while (true) {// Used AI to code this part of the question
            System.out.print("Enter your 10-digit South African phone number (e.g., 0821234567): ");
            String inputPhone = scanner.nextLine();

            if (validator.validatePhoneNumber(inputPhone)) {
                System.out.println("Your South African phone number with country code: " + validator.getFormattedPhoneNumber());
                break;
            } else {
                System.out.println("Invalid phone number. Must be exactly 10 digits.");
            }
        }

        System.out.println("");
        System.out.println("----------------- LOGIN INFO -------------------");
        System.out.println("please enter the following infomation to login: ");
        while (true) {
            System.out.print("Enter your username: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter a password: ");
            String loginPass = scanner.nextLine();

            if (validator.login(loginUser, loginPass)) {
                System.out.println("Welcome " + validator.getFullName() + ", we are happy to see you joined us.");
                break;
            } else if (!validator.isUsernameCorrect(loginUser) && validator.isPasswordCorrect(loginPass)) {
                System.out.println("Username incorrect, try again.");
            } else if (validator.isUsernameCorrect(loginUser) && !validator.isPasswordCorrect(loginPass)) {
                System.out.println("Password incorrect, try again.");
            } else {
                System.out.println("Username and password incorrect, try again.");
            }
        }

        
    }
}
    

