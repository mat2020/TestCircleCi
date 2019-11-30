package com.uottawa.project;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.*;

import static org.junit.Assert.assertNotEquals;

public class RegisterActivityTest {


    @Rule
    public ActivityTestRule<Register> mRegisterTestRule= new ActivityTestRule(Register.class);
    private  Register mRegister;
    private TextView text;



    @Before
    public void setUp() throws Exception {
        mRegister = mRegisterTestRule.getActivity();
    }

    /*
     *   Unit Local test for login method on activity_main
     */

    @Test
    @UiThreadTest
    public  void checkRegisterUserName() throws  Exception{
        text = mRegister.findViewById(R.id.username);
        text.setText("user1");
        String name= text.getText().toString();
        assertNotEquals( "user",name);
    }

    /*
     *   Unit Local test for login method on activity_main
     */

    @Test
    @UiThreadTest
    public  void checkRegisterEmail() throws  Exception{
        text = mRegister.findViewById(R.id.email);
        text.setText("email@gmail.com");
        String name= text.getText().toString();
        AssertJUnit.assertEquals( "email@gmail.com",name);
    }



}

