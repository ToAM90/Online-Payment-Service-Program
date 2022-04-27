package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {


    List<Account> getAllAccounts();

    BigDecimal getBalance();

    Account getAnAccountByUserId(long userId);


}
