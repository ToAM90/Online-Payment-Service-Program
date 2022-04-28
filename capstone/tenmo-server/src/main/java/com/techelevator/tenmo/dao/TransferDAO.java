package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDAO {

    public List<Transfer> getAllApprovedTransfers(long accountId);

    public Transfer getTransferById(long transferId);

    public Transfer newTransfer(long accountFrom, long accountTo, BigDecimal amount);








}
