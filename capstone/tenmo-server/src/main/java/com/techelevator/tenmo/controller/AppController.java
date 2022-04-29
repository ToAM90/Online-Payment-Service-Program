package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path="balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal principal){
        String username = principal.getName();
        long userId = userDao.findIdByUsername(username);
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
        List<Transfer> transferList = transferDAO.getAllApprovedTransfers(accountId);
        return  transferList;

    }

    @RequestMapping(path="transfers/{transferId}", method = RequestMethod.GET)
    public Transfer transferDetails (@PathVariable long transferId){
        return transferDAO.getTransferById(transferId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="transfers", method = RequestMethod.POST)
    public Transfer startTransfer (Principal principal, @RequestBody TransferDTO transferDTO) {

        System.out.println(transferDTO.getAccountToId());
        System.out.println(transferDTO.getAmount());
        String username = principal.getName();
        long userID = userDao.findIdByUsername(username);
        Transfer transfer = transferDAO.newTransfer(userID, transferDTO.getAccountToId(),transferDTO.getAmount() );


        return transfer;
    }

    @RequestMapping(path="username/{accountId}", method = RequestMethod.GET)
    public String username (@PathVariable long accountId){
        return userDao.findUserByAccountID(accountId);
    }




}
