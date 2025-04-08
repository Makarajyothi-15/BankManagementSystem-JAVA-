package com.bank;

import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.println("===== Admin Login =====");
        System.out.print(" Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        
        if (!username.equals("admin") || !password.equals("1234")) {
            System.out.println(" Invalid credentials. Access Denied!");

            scanner.close();
            return;
        }

        System.out.println("✅ Login successful! Welcome, " + username);

        
        int choice;
        do {
            System.out.println("\n======= BANK MANAGEMENT MENU =======");
            System.out.println("1. Insert Customer");
            System.out.println("2. View All Customers");
            System.out.println("3.  Update Customer Balance");
            System.out.println("4.  Search Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6.  Deposit Amount");
            System.out.println("7.  Withdraw Amount");
            System.out.println("8. Check Balance");


            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Account Number: ");
                    String accNum = scanner.nextLine();
                    System.out.print("Enter Balance: ");
                    double balance = scanner.nextDouble();
                    BankDatabase.insertCustomer(name, accNum, balance);
                    break;

                case 2:
                    BankDatabase.viewCustomers();
                    break;

                case 3:
                    System.out.print("Enter Account Number to Update: ");
                    String updateAcc = scanner.nextLine();
                    System.out.print("Enter New Balance: ");
                    double newBal = scanner.nextDouble();
                    BankDatabase.updateCustomerBalance(updateAcc, newBal);
                    break;

                case 4:
                    System.out.print("Enter Name or Account Number to Search: ");
                    String keyword = scanner.nextLine();
                    BankDatabase.searchCustomer(keyword);
                    break;

                case 5:
                    System.out.print("Enter Account Number to Delete: ");
                    String delAcc = scanner.nextLine();
                    BankDatabase.deleteCustomer(delAcc);
                    break;
                case 6:
                    System.out.print("Enter Account Number: ");
                    String depAcc = scanner.nextLine();
                    System.out.print("Enter Amount to Deposit: ");
                    double depAmt = scanner.nextDouble();
                    BankDatabase.depositAmount(depAcc, depAmt);
                    break;

                case 7:
                    System.out.print("Enter Account Number: ");
                    String witAcc = scanner.nextLine();
                    System.out.print("Enter Amount to Withdraw: ");
                    double witAmt = scanner.nextDouble();
                    BankDatabase.withdrawAmount(witAcc, witAmt);
                    break;
                    case 8:
    System.out.print("Enter Account Number to Check Balance: ");
    String balAcc = scanner.nextLine();
    BankDatabase.checkBalance(balAcc);
    break;


                case 0:
                    System.out.println(" Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
