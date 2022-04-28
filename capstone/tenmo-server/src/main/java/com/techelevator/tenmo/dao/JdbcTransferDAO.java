package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO{

    private AccountDao accountDao;

    private String joinTemplate = "SELECT * FROM transfer t " +
        "JOIN transfer_status ts ON t.transfer_id = ts.transfer_status_id " +
        "JOIN transfer_type tt ON t.transfer_id = tt.transfer_type_id ";

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> getAllTransfers(long accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = joinTemplate + "WHERE account_from = ? OR account_to = ?";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while  (results.next()){
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }


    @Override
    public Transfer getTransferById(long transferId) {
        Transfer transfer = new Transfer();
        String sql = joinTemplate + "WHERE t.transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        if(results.next()){
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public Transfer newTransfer(long accountFrom, long accountTo, BigDecimal amount) {
        String sql = "INSERT INTO transfer (account_from, account_to, amount) Values (?, ?, ?) RETURNING transfer_id";
        long newTransferId = 0;

        
        if(accountFrom != accountTo){
        try {
        newTransferId = jdbcTemplate.queryForObject(sql, Long.class, accountFrom, accountTo, amount);

        if (accountDao.subtractBalance(amount, accountFrom)){
              accountDao.addBalance(amount, accountTo);
        }

        } catch (DataAccessException e) {
            System.out.println("");

        } } else {

        }
        return getTransferById(newTransferId);
    }

    private Transfer mapRowToTransfer(SqlRowSet rs){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getLong("tt.transfer_type_id"));
        transfer.setTransferTypeDesc(rs.getString("tt.transfer_type_desc"));
        transfer.setTransferStatusId(rs.getLong("ts.transfer_status_id"));
        transfer.setTransferTypeDesc(rs.getString("ts.transfer_status_desc"));
        transfer.setAccountFrom(rs.getLong("t.amount_from"));
        transfer.setAccountTo(rs.getLong("t.amount_to"));
        transfer.setAmount(rs.getBigDecimal("t.amount"));

        return transfer;

    }
}
