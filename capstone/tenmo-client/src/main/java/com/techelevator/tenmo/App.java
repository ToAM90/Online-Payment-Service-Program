package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TenmoService;

import java.math.BigDecimal;
import java.util.Scanner;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private TenmoService tenmoService = new TenmoService();


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);


        if (currentUser == null) {
            consoleService.printErrorMessage();
        }

        // set token in Service
        tenmoService.setAuthToken(currentUser.getToken());


    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
       System.out.println("Your current balance is: $" + tenmoService.getAccountBalance());
	}

	private void viewTransferHistory() {
        Transfer[]  listOfTransfers = tenmoService.getAllTransfers();
        consoleService.border();
        System.out.println("Transfer");
        System.out.println("ID \t From/To \t\t Amount");
        consoleService.border();
        long currentAccountId = tenmoService.getAccountById(currentUser.getUser().getId()).getAccountId();
        for (Transfer transfer : listOfTransfers){
            if(transfer.getAccountFrom() == currentAccountId){
                System.out.println(transfer.getTransferId() + "To: " + transfer.getAccountTo() + transfer.getAmount());
            } else if (transfer.getAccountTo() == currentAccountId){
                System.out.println(transfer.getTransferId() + "From: " + transfer.getAccountFrom() + transfer.getAmount());
            }

        }
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        boolean running = true;
        while(running){
        System.out.println("Users ID\t" + "Name");
        consoleService.border();
        User[] listUsers = tenmoService.getAllUsers();
        for(User user : listUsers){
            System.out.println(user.getId() + "\t\t" + user.getUsername());
        }
        consoleService.border();

        int id = consoleService.promptForInt("Enter the ID of user you are sending to (0 to cancel): ");
        if (id == 0){
            running = false;
        } else {
            BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:");

        }


	}}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
