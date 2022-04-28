package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {

    private long accountToId;
    private BigDecimal amount;

    public TransferDTO(long accountToId, BigDecimal amount) {
        this.accountToId = accountToId;
        this.amount = amount;
    }

    public long getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(long accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
