package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    // properties
    private long transferId;
    private String transferTypeDesc;
    private String transferStatusDesc;
    @NotBlank(message = "The sender should exist.")
    private long accountFrom;
    @NotBlank(message = "The receiver should exist.")
    private long accountTo;
    @Positive(message = "Amount should be greater than zero.")
    private BigDecimal amount;

    public Transfer() {}

    public Transfer(long transferId, long accountFrom, long accountTo, BigDecimal amount, String transferTypeDesc, String transferStatusDesc) {
        this.transferId = transferId;
        this.transferTypeDesc = transferTypeDesc;
        this.transferStatusDesc = transferStatusDesc;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    // getters and setters
    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }
}
