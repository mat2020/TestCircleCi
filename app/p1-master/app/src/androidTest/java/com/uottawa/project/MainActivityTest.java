package com.uottawa.project;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testng.AssertJUnit;

import static org.junit.Assert.assertNotEquals;


public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule= new ActivityTestRule(MainActivity.class);
    private  MainActivity  mActivity = null;
    private TextView text;



    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    /*
    *   Unit Local test for UserName on activity_main
    */

    @Test
    @UiThreadTest
    public  void checkUserName() throws  Exception{
        text = mActivity.findViewById(R.id.username);
        text.setText("user1");
        String name= text.getText().toString();
        assertNotEquals( "user",name);
    }

    /*
     *   Unit Local test for password on activity_main
     */

    @Test
    @UiThreadTest
    public  void checkPassword() throws  Exception{
        text = mActivity.findViewById(R.id.password);
        text.setText("temp");
        String name= text.getText().toString();
        AssertJUnit.assertEquals( "temp",name);
    }


}