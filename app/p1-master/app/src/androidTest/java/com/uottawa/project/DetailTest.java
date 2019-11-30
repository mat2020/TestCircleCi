package com.uottawa.project;
package com.uottawa.project;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.*;

import static org.junit.Assert.assertNotEquals;

public class DetailTest {

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
    public  void checkFirstName() throws  Exception{
        text = mRegister.findViewById(R.id.lastname);
        text.setText("last");
        String name= text.getText().toString();
        assertNotEquals( "last",name);
    }

    /*
     *   Unit Local test for login method on activity_main
     */

    @Test
    @UiThreadTest
    public  void checkLastName() throws  Exception{
        text = mRegister.findViewById(R.id.firstname);
        text.setText("first");
        String name= text.getText().toString();
        AssertJUnit.assertEquals( "first",name);
    }
    @Test
    @UiThreadTest
    public  void checkConfirmPass() throws  Exception{
        text = mRegister.findViewById(R.id.comfirmpassword);
        text.setText("com");
        String name= text.getText().toString();
        AssertJUnit.assertEquals( "com",name);
    }
}

