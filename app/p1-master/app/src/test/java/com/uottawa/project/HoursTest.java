package com.uottawa.project;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HoursTest {
        Hours testHours = new Hours("test");
    @Test
    public void getHours() {
        testHours.setHours(1, 8, 5,true);
        int[] hours = testHours.getHours(1);
        Assert.assertEquals(hours[1],5);
    }

    @Test
    public void setHours() {
        testHours.setHours(1, 8, 5,true);
        int[] hours = testHours.getHours(1);
        Assert.assertEquals(hours[0],8);
    }

    @Test
    public void getId() {
        testHours.setId("test");
        String expect = testHours.getId();
        Assert.assertEquals(expect,"test");
    }

    @Test
    public void setId() {
        testHours.setId("test");
        String expect = testHours.getId();
        Assert.assertEquals(expect,"test");

    }

    @Test
    public void getSpecialHours() {
        List<SingleHour> specialHours = new ArrayList<>();
        specialHours.add(new SingleHour(1,8,5));
        testHours.setSpecialHours(specialHours);
        List<SingleHour> expectedHours = testHours.getSpecialHours();
        Assert.assertEquals(expectedHours,specialHours);
    }

    @Test
    public void setSpecialHours() {
        List<SingleHour> specialHours = new ArrayList<>();
        specialHours.add(new SingleHour(1,8,5));
        testHours.setSpecialHours(specialHours);
        List<SingleHour> expectedHours = testHours.getSpecialHours();
        Assert.assertEquals(expectedHours,specialHours);
    }

    @Test
    public void getWeeklyHours() {
        List<SingleHour> specialHours = new ArrayList<>();
        specialHours.add(new SingleHour(1,8,5));
        testHours.setWeeklyHours(specialHours);
        List<SingleHour> expectedHours = testHours.getWeeklyHours();
        Assert.assertEquals(expectedHours,specialHours);
    }

    @Test
    public void setWeeklyHours() {
        List<SingleHour> specialHours = new ArrayList<>();
        specialHours.add(new SingleHour(1,8,5));
        testHours.setWeeklyHours(specialHours);
        List<SingleHour> expectedHours = testHours.getWeeklyHours();
        Assert.assertEquals(expectedHours,specialHours);
    }

    @Test
    public void getName() {
        Assert.assertEquals(testHours.getName(),"test");
    }

    @Test
    public void setName() {
        testHours.setName("newHours");
        Assert.assertEquals(testHours.getName(),"newHours");

    }
}