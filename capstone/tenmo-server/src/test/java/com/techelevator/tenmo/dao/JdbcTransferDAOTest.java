package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TransferQueue;


public class JdbcTransferDAOTest extends BaseDaoTests {
    private static final Transfer TRANSFER_1 = new Transfer(3001L, 1L, 2L,new BigDecimal("10.00"),"Send","Approved");
    private static final Transfer TRANSFER_2 = new Transfer(3002L, 2L, 1L,new BigDecimal("10.00"),"Send","Approved");
    private static final Transfer TRANSFER_3 = new Transfer(3003L, 2L, 1L,new BigDecimal("10.00"),"Send","Rejected");


    private TransferDAO sut;
    private AccountDao accountDao;
    private Transfer transferTest;
    @Before
    public void setup(){
        accountDao=new JdbcAccountDAO(datasource);
        sut = new JdbcTransferDAO(datasource);
        transferTest = new Transfer(3004L, 2L, 1L,new BigDecimal("200.00"),"Send","Approved");

    }

    @Test
    public void getAllApprovedTransfers() {
        List<Transfer> transfer = sut.getAllApprovedTransfers(2L);

        Assert.assertEquals(2,transfer.size());
        assertTransfersMatch(TRANSFER_1,transfer.get(0));
        assertTransfersMatch(TRANSFER_2,transfer.get(1));
    }

    @Test
    public void getTransferById() {
        Transfer transfer = sut.getTransferById(3001L);
        Assert.assertNotNull(transfer);
        assertTransfersMatch(TRANSFER_1,transfer);

    }

    @Test
    public void newTransfer() {
        Transfer createdTransfer = sut.newTransfer(21L,20L, new BigDecimal("200.00"));
        Assert.assertNotNull(createdTransfer);
        long newId = createdTransfer.getTransferId();
        Assert.assertTrue(newId>0);
        assertTransfersMatch(transferTest,createdTransfer);

    }

    private void assertTransfersMatch
            (Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        Assert.assertEquals(expected.getAccountTo(), actual.getAccountTo());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
        Assert.assertEquals(expected.getTransferStatusDesc(), actual.getTransferStatusDesc());
        Assert.assertEquals(expected.getTransferTypeDesc(), actual.getTransferTypeDesc());
    }

}
