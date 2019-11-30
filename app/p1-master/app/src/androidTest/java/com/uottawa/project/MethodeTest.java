package com.uottawa.project;

import org.junit.Assert;
import org.junit.Test;

public class MethodeTest {
    public Account testAccount = new Account("team", "User", "firstname", "lastName", "email", "account" );

    /*
     *   Unit Local test for hasshing method on activity_main
     */

    @Test
    public  void checkHashingMethode() throws  Exception{

        Assert.assertEquals(testAccount.getPassword(), testAccount.hashPassword("team"));
    }
}
