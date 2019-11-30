package com.uottawa.project;
import org.junit.Assert;
import org.junit.Test;


public class ServiceTest {
    public Service test= new Service(2,"Tom","Employee","55");
    public Service test2;


    public  void checkSetMethod() throws  Exception {
        test2.setName("Tom");
        test2.setRole("Patient");
        test2.setId("66");
        Assert.assertEquals(test.getName(), test2.getName());
        Assert.assertNotEquals(test.getRole(), test2.getRole());
        Assert.assertNotEquals(test.getId(),test2.getId());
    }
}
