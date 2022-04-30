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
    private static final Transfer TRANSFER_1 = new Transfer(1L, 1L, 2L,new BigDecimal("10.00"),"Send","Approved");
    private static final Transfer TRANSFER_2 = new Transfer(2L, 2L, 1L,new BigDecimal("10.00"),"Send","Approved");
    private static final Transfer TRANSFER_3 = new Transfer(3L, 2L, 1L,new BigDecimal("10.00"),"Send","Rejected");
    private static final Transfer TRANSFER_4 = new Transfer(4L, 3L, 1L,new BigDecimal("10.00"),"Send","Rejected");


    private TransferDAO sut;
    private AccountDao accountDao;
    private Transfer transferTest;
    @Before
    public void setup(){
        accountDao=new JdbcAccountDAO(datasource);
        sut = new JdbcTransferDAO(datasource);
        transferTest = new Transfer(5L, 2L, 1L,new BigDecimal("200.00"),"Send","Approved");

    }

    @Test
    public void getAllApprovedTransfers() {
        List<Transfer> approvedTransfers = sut.getAllApprovedTransfers(2L);

        Assert.assertEquals(2,approvedTransfers.size());
        assertTransfersMatch(TRANSFER_1,approvedTransfers.get(0));
        assertTransfersMatch(TRANSFER_2,approvedTransfers.get(1));

        List<Transfer> rejectedTransfers = sut.getAllApprovedTransfers(3L);
        Assert.assertEquals(0,rejectedTransfers.size());
    }

    @Test
    public void getTransferById() {
        Transfer transfer = sut.getTransferById(1L);
        Assert.assertNotNull(transfer);
        assertTransfersMatch(TRANSFER_1,transfer);

    }

    @Test
    public void newTransfer() {
        Transfer createdTransfer = sut.newTransfer(2L,1L, new BigDecimal("200.00"));
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
