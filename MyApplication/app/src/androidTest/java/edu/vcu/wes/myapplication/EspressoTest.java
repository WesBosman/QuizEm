package edu.vcu.wes.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.suitebuilder.annotation.LargeTest;
//import junit.framework.TestResult;

//import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Wes on 9/8/2015.
 * This is an Espresso Test Class for testing button functionality. Etc.
 */
@RunWith(JUnit4.class)
@LargeTest
 public class EspressoTest extends ActivityInstrumentationTestCase2<MainActivity>{
    public EspressoTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
    }

    @Test
    public void checkButtons() {
        onView(withId(R.id.button)).perform(click());
    }
}
