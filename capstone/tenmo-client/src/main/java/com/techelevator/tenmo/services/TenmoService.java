package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TenmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    /**
     * Creates a new HttpEntity with the `Authorization: Bearer:` header and a reservation request body
     */
    private HttpEntity <TransferDTO> makeTransferEntity(TransferDTO transferDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);

        HttpEntity<TransferDTO> entity = new HttpEntity<>(transferDTO, headers);

        return entity;
    }

    /**
     * Returns an HttpEntity with the `Authorization: Bearer:` header
     */

    private HttpEntity<Void> makeAuthEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return entity;
    }

    public BigDecimal getAccountBalance(){
        BigDecimal balance = new BigDecimal(0);

       try{
         
           balance = restTemplate.exchange(
                   API_BASE_URL + "balance",
                   HttpMethod.GET,
                   makeAuthEntity(),
                   BigDecimal.class).getBody();

       } catch(RestClientResponseException | ResourceAccessException e){
           BasicLogger.log(e.getMessage());
       }
       return balance;
    }

    public Account getAccountById(long id){
        Account account = null;
        try {
            account = restTemplate.exchange(API_BASE_URL + "user/" + id,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account.class).getBody();

        }catch(RestClientResponseException | ResourceAccessException e){
        System.out.println("Something went wrong getting the account by userId");
    }
        return account;
    }

    public User[] getAllUsers(){
        User[] listOfUsers = null;
        try {
            listOfUsers = restTemplate.exchange(
                    API_BASE_URL + "users",
                    HttpMethod.GET,
                    makeAuthEntity(),
                    User[].class).getBody();
        }catch(RestClientResponseException | ResourceAccessException e){
            System.out.println("Something went wrong getting all users");
        }
        return listOfUsers;
    }

    public Transfer[] getAllTransfers(){
        Transfer[] listOfTransfers = null;
        try {
            listOfTransfers = restTemplate.exchange(
                    API_BASE_URL + "transfers",
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Transfer[].class).getBody();
        }catch(RestClientResponseException | ResourceAccessException e){
            System.out.println("Something went wrong getting all transfers");
        }
        return listOfTransfers;
    }

    public Transfer getTransferDetails(long accountId){
        Transfer transfer = null;
        try {
            transfer = restTemplate.exchange(
                    API_BASE_URL + "transfers/" + accountId,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Transfer.class).getBody();
        }catch(RestClientResponseException | ResourceAccessException e){
            System.out.println("Something went wrong getting all transfers");
        }
        return transfer;
    }

        public Transfer makeTransfer(long userToId, BigDecimal amount){
        TransferDTO transferDTO = new TransferDTO(userToId, amount);
        Transfer transfer = null;
        try{
            transfer = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.POST, makeTransferEntity(transferDTO), Transfer.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println("Something went wrong making a transfer");
        }
        return transfer;

    }

    public String username (long accountId){
        String username = null;
        try {
            username = restTemplate.exchange(API_BASE_URL + "username/" + accountId, HttpMethod.GET, makeAuthEntity(), String.class).getBody();

        } catch (RestClientResponseException | ResourceAccessException e){
        System.out.println("Something went wrong getting username");
    }
        return username;
    }

}
