package com.uottawa.project;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

public class ClinicTest {

    public Clinic testClinic = new Clinic("Test");
    public Service testService = new Service((float) 1.0, "testService", "test", "d1");
    public  ArrayList<Service> Service;


    @Test
    public void addService() {
        testClinic.addService(testService);
        Service = testClinic.getServices();
        String name = Service.get(Service.size() - 1).getName();
        Assert.assertEquals(name, "testService");
    }

    @Test
    public void removeService() {
        testClinic.addService(testService);
        ArrayList<Service> Service1 = testClinic.getServices();
        testClinic.removeService(testService);
        ArrayList<Service> Service2 = testClinic.getServices();

        Assert.assertEquals(Service1, Service2);
    }

    @Test
    public void setOpenHours() {
        int[] hours = {8,1,2};
        testClinic.setOpenHours(hours);
        int[]expectedHours = testClinic.openHours;
        Assert.assertArrayEquals(expectedHours , hours);
    }

    @Test
    public void setClosingHours() {
        int[] hours = {8,1,2};
        testClinic.setClosingHours(hours);
        int[]expectedHours = testClinic.closeHours;
        Assert.assertArrayEquals(expectedHours , hours);
    }

    @Test
    public void addEmployee() {
        Employee setEmployee = new Employee("joe");
        testClinic.addEmployee(setEmployee);
        ArrayList<Employee> expected =  testClinic.getEmployees();
        String back = expected.get(0).getUsername();
        Assert.assertEquals(back,"joe" );
    }

    @Test
    public void getEmployees() {
        Employee setEmployee = new Employee("joe");
        testClinic.addEmployee(setEmployee);
        ArrayList<Employee> expected =  testClinic.getEmployees();
        String back = expected.get(0).getUsername();
        Assert.assertEquals(back,"joe" );
    }

    @Test
    public void getName() {
        testClinic.getName();
        Assert.assertEquals(testClinic.getName(),"Test");
    }

    @Test
    public void setID() {
        testClinic.setID("test");
        Assert.assertEquals(testClinic.getID(),"test");
    }

    @Test
    public void getID() {
        testClinic.setID("test");
        Assert.assertEquals(testClinic.getID(),"test");
    }

    @Test
    public void getServices() {
        testClinic.addService(testService);
        Service = testClinic.getServices();
        String name = Service.get(Service.size() - 1).getName();
        Assert.assertEquals(name, "testService");
    }
}