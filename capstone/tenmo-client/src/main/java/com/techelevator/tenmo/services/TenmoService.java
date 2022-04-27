package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
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
    private HttpEntity <Account> makeAccountEntity(Account account){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);

        HttpEntity<Account> entity = new HttpEntity<>(account, headers);

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

    public Account[] getAllAccounts(){

        Account[] listOfAccounts = null;
        try{
            listOfAccounts = restTemplate.exchange(
                    API_BASE_URL + "account",
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account[].class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println("Like zoinks scoob, something went wrong... Rikes Raggy Rehehehe");
        }
        return listOfAccounts;
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
            account = restTemplate.exchange(API_BASE_URL + "user?=" + id,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account.class).getBody();

        }catch(RestClientResponseException | ResourceAccessException e){
        System.out.println("Something went wrong");
    }
        return account;
    }



}
