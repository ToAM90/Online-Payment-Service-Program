package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JdbcAccountDAOTest extends BaseDaoTests{
    private static final Account ACCOUNT_1 = new Account(20L,1L, new BigDecimal("1000.00"));
    private static final Account ACCOUNT_2 = new Account(21L,2L, new BigDecimal("1000.00"));

    private JdbcAccountDAO sut;
    @Before
    public void setup(){
        sut = new JdbcAccountDAO(datasource);
    }


    @Test
    public void getBalance() {
        BigDecimal retrieveBalance = sut.getBalance(20);
        Assert.assertEquals(new BigDecimal("1000.00"), retrieveBalance);

    }

    @Test
    public void getAnAccountByUserId() {
        Account account2 = sut.getAnAccountByUserId(21);
        Assert.assertNotNull(account2);

        assertAccountsMatch(ACCOUNT_2, account2);


        Account account1 = sut.getAnAccountByUserId(20);
        Assert.assertNotNull(account1);

        assertAccountsMatch(ACCOUNT_1, account1);

    }

    @Test
    public void addBalance() {
        Account account = sut.getAnAccountByUserId(21);
        account.setBalance(new BigDecimal("1200.00"));

        sut.addBalance(new BigDecimal("200.00"), 21);

        Account updatedAccount = sut.getAnAccountByUserId(21);

        assertAccountsMatch(account,updatedAccount);

    }

    @Test
    public void subtractBalance() {
        Account account = sut.getAnAccountByUserId(21);
        account.setBalance(new BigDecimal("800.00"));

        sut.subtractBalance(new BigDecimal("200.00"), 21);

        Account updatedAccount = sut.getAnAccountByUserId(21);

        assertAccountsMatch(account,updatedAccount);

    }


    private void assertAccountsMatch
            (Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());



    }

}
