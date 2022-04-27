package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AppController {

    @Autowired
    AccountDao accountDao;

    TransferDAO transferDAO;

    @RequestMapping(path="/account", method = RequestMethod.GET)
    public List<Account> listAccounts(){return accountDao.getAllAccounts();
    }

    @RequestMapping(path="/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(){return accountDao.getBalance();
    }

    @RequestMapping(path="/user/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable long id){return accountDao.getAnAccountByUserId(id);
    }

}
