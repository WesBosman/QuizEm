package edu.vcu.wes.myapplication;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

import android.test.suitebuilder.annotation.LargeTest;

//import junit.framework.TestResult;
//import org.junit.Rule;


/**
 * Created by Wes on 9/8/2015.
 * This is an Espresso Test Class for testing button functionality.
 * May need to run using JUNIT4???
 * Currently working.
 */
@LargeTest
 public class EspressoTest extends ActivityInstrumentationTestCase2<MainActivity>{
    private MainActivity mainActivity;

    public EspressoTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mainActivity = getActivity();
    }

    /**
     * This method checks that the buttons on the Main Screen can be clicked.
     */
    public void testCheckButtons() {
        onView(withId(R.id.quiz_button)).perform(click());
        //getActivity();
        //onView(withId(R.id.flash_button)).perform(click());
    }
}
