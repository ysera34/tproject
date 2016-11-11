package com.tacademy.test.robotium.test;

import com.tacademy.test.robotium.LoginActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class LoginTest1 extends ActivityInstrumentationTestCase2<LoginActivity> {
  	private Solo solo;
  	
  	public LoginTest1() {
		super(LoginActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.tacademy.test.robotium.LoginActivity'
		solo.waitForActivity(com.tacademy.test.robotium.LoginActivity.class, 2000);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.tacademy.test.robotium.R.id.email));
        //Enter the text: 'erfsdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.tacademy.test.robotium.R.id.email));
		solo.enterText((android.widget.EditText) solo.getView(com.tacademy.test.robotium.R.id.email), "erfsdf");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.tacademy.test.robotium.R.id.password));
        //Enter the text: 'thfdzcvf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.tacademy.test.robotium.R.id.password));
		solo.enterText((android.widget.EditText) solo.getView(com.tacademy.test.robotium.R.id.password), "thfdzcvf");
        //Click on sign in
		solo.clickOnView(solo.getView(com.tacademy.test.robotium.R.id.email_sign_in_button));
        //Click on sign in
		solo.clickOnView(solo.getView(com.tacademy.test.robotium.R.id.email_sign_in_button));
        //Click on sign in
		solo.clickOnView(solo.getView(com.tacademy.test.robotium.R.id.email_sign_in_button));
        //Click on sign in
		solo.clickOnView(solo.getView(com.tacademy.test.robotium.R.id.email_sign_in_button));
        //Press menu back key
		solo.goBack();
	}
}
