package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AppController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TransferDAO transferDAO;

    @RequestMapping(path="account", method = RequestMethod.GET)
    public List<Account> listAccounts(){return accountDao.getAllAccounts();
    }

    @RequestMapping(path="balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal principal){
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal balance = accountDao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path="user/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable long id){return accountDao.getAnAccountByUserId(id);
    }

    @RequestMapping(path="users", method = RequestMethod.GET)
    public List<User>getAllUsers(Principal principal){
        String username = principal.getName();
        long userID = userDao.findIdByUsername(username);
        return userDao.findAll(userID);
    }

    @RequestMapping(path="transfers", method = RequestMethod.GET)
    public List<Transfer> listTransfers(Principal principal){
        String username = principal.getName();
        long userID = userDao.findIdByUsername(username);
        Account account = accountDao.getAnAccountByUserId(userID);
        long accountId = account.getAccountId();
        List<Transfer> transferList = transferDAO.getAllTransfers(accountId);
        return  transferList;

    }




}
