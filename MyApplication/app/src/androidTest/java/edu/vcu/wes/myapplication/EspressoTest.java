package edu.vcu.wes.myapplication;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

import android.test.suitebuilder.annotation.LargeTest;

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
     * This method checks that the buttons currently in the app can be clicked.
     */
    public void testCheckButtons() {
        onView(withId(R.id.quiz_button)).perform(click());

        pressBack();
        onView(withId(R.id.flash_button)).perform(click());
        pressBack();
        onView(withId(R.id.quiz_button)).perform(click());
        onView(withId(R.id.quiz_make)).perform(click());
        pressBack();
        onView(withId(R.id.quiz_take)).perform(click());
        onView(withId(R.id.quiz_all)).perform(click());
        pressBack();
        pressBack();

    }

    /**This one tests our make a quiz function. Currently half works.*/
    public void testMakeQuiz() {
        onView(withId(R.id.quiz_button)).perform(click());
        onView(withId(R.id.quiz_make)).perform(click());

        onView(withId(R.id.editTextTitle)).perform(click())
                .perform(typeText("Please Work"));


        onView(withId(R.id.editTextQuestion)).perform(click())
                .perform(typeText("I got it to work."));


        //onView(withId(R.id.editTextAnswer)).perform(click())
        //.perform(typeText("feels good man"));

        onView(withId(R.id.submit_button)).perform(click());

    }
}
