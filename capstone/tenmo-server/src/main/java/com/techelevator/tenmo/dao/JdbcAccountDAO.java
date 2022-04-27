package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcAccountDAO implements AccountDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDAO(DataSource ds){this.jdbcTemplate = new JdbcTemplate(ds);}

    // SqlRowSet Object Mapper :)
    private Account accountObjectMapper(SqlRowSet results){
        Account account = new Account();
        account.setAccountId(results.getLong("account_id"));
        account.setBalance(results.getBigDecimal("balance"));

        return account;
    }


    // override methods

    @Override
    public List<Account> getAllAccounts() {
       String sql = "SELECT * FROM account";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql);
        List<Account> accounts = new ArrayList<>();
        while (results.next()){
            accounts.add(accountObjectMapper(results));
        }
        return accounts;
    }

    @Override
    public BigDecimal getBalance(long id) {

        String sql = "SELECT balance FROM account WHERE user_id = ?;";
       return jdbcTemplate.queryForObject(sql,BigDecimal.class, id);
    }

    @Override
    public Account getAnAccountByUserId(long userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, userId);

        Account account = null;
        if(results.next()){
            account = accountObjectMapper(results);
        }

        return account;
    }


}
