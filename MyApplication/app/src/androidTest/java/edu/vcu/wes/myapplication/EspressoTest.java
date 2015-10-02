package edu.vcu.wes.myapplication;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;

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
     * This method checks that the buttons currently in the app can be clicked.
     */
    public void testCheckButtons() {
        onView(withId(R.id.quiz_button)).perform(click());
        //assertEquals("MainActivity" , getActivity());
        pressBack();
        onView(withId(R.id.flash_button)).perform(click());
        pressBack();
        onView(withId(R.id.quiz_button)).perform(click());
        onView(withId(R.id.quiz_make)).perform(click());
        pressBack();
        onView(withId(R.id.quiz_take)).perform(click());
        //pressBack();
        onView(withId(R.id.quiz_all)).perform(click());
        pressBack();
        pressBack();

    }

    /**This one tests our make a quiz function. Currently half works.*/
    public void testMakeQuiz() {
        onView(withId(R.id.quiz_button)).perform(click());
        onView(withId(R.id.quiz_make)).perform(click());
        onView(withId(R.id.editTextTitle)).perform((click()), replaceText("Please Work")); /**Had to use replace text here rather than edit text because of a glitch in espresso with how the keyboard works */
        //.perform(typeText("Please Work"));
        pressBack();
        onView(withId(R.id.editTextQuestion)).perform((click()), replaceText("I got it to work."));
        pressBack();
        onView(withId(R.id.editTextAnswer)).perform((click()), replaceText("feels good man"));/**It stops working here for no reason*/
        pressBack();
        onView(withId(R.id.submit_button)).perform(click());
        pressBack();
        pressBack();
    }

    /** And this one tests our current delete function */
    /**public void testDeleteQuiz() {
        onView(withId(R.id.quiz_button)).perform(click());
        onView(withId(R.id.quiz_all)).perform(click());
        onView(withId(R.id.simple_expandable_list_item_1)).perform(click());
    }*/
}
