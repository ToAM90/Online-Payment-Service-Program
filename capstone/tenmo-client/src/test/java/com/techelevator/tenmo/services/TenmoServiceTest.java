package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TenmoServiceTest {
    private TenmoService obj;

    @Before
    public void setup(){
        obj = new TenmoService();
    }

    @Test
    public void getAccountBalance() {
        // ARRANGE
        BigDecimal actualAccountBalance = obj.getAccountBalance();
        BigDecimal expectedAccountBalance = new BigDecimal("0");

        //ASSERT
        Assert.assertEquals(expectedAccountBalance,actualAccountBalance);


    }

//    @Test
//    public void getAccountById() {
//        // ARRANGE
//        Account actualAccountId = obj.getAccountById(1002);
//        String expectedAccountId = "";
//
//        Assert.assertEquals(expectedAccountId,actualAccountId);
//
//    }
//
//    @Test
//    public void getAllUsers() {
//    }
//
//    @Test
//    public void getAllTransfers() {
//    }
//
//    @Test
//    public void getTransferDetails() {
//    }
//
//    @Test
//    public void makeTransfer() {
//    }
//
//    @Test
//    public void username() {
//    }
}
