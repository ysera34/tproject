package com.tacademy.test.robotium;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

/**
 * Created by yoon on 2016. 11. 10..
 */

@SuppressWarnings("rawtypes")
public class LoginTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            LoginTest.class.getCanonicalName();

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public LoginTest() throws ClassNotFoundException {
        super(launcherActivityClass);
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
        //Wait for activity
        solo.waitForActivity("LoginActivity", 2000);
        //Set default small timeout to 15425 milliseconds
        Timeout.setSmallTimeout(15425);
        //Enter the text: 'test@example.com'
        solo.clearEditText((android.widget.EditText) solo.getView("email"));
        solo.enterText((android.widget.EditText) solo.getView("email"), "test@example.com");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("password"));
        //Enter the text: 'asdfg'
        solo.clearEditText((android.widget.EditText) solo.getView("password"));
        solo.enterText((android.widget.EditText) solo.getView("password"), "asdfg");
        //Click on Sign in or register
        solo.clickOnView(solo.getView("email_sign_in_button"));
    }
}
