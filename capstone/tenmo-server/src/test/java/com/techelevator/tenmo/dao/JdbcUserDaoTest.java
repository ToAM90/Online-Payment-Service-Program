package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcUserDaoTest extends BaseDaoTests{
    public static final User USER_1 = new User(20L,"Anne","Pass","ROLE_USER");
    public static final User USER_2 = new User(21L,"Roland","Pass","ROLE_USER");

    private JdbcUserDao sut;

    private User userTest;

    @Before
    public void setup(){
        sut = new JdbcUserDao(datasource);
        userTest = new User(22L,"Robot","Pass","Admin");
    }

    @Test
    public void findUserByAccountID() {
        String findUser = sut.findUserByAccountID(2L);
        Assert.assertNotNull(findUser);
        assertEquals(USER_2.getUsername(),findUser);

    }

    @Test
    public void findIdByUsername() {
        Long findId = sut.findIdByUsername("Anne");
        Assert.assertNotNull(findId);
        assertEquals(USER_1.getId(),findId);


    }

    @Test
    public void findAll() {
        List<User> listUsers = sut.findAll(21);

        Assert.assertEquals(1,listUsers.size());
        assertUserMatch(USER_1,listUsers.get(0));


    }

    @Test
    public void findByUsername() {
        User findByUsername = sut.findByUsername("Anne");
        Assert.assertNotNull(findByUsername);
        assertUserMatch(USER_1,findByUsername);
    }

    @Test
    public void create() {
        boolean createNewUser = sut.create("Robot","Pass");

        Assert.assertTrue(createNewUser);

    }

    private void assertUserMatch
            (User expected, User actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());

    }

}
