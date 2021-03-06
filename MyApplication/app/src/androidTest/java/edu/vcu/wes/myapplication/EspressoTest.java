package edu.vcu.wes.myapplication;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import android.test.suitebuilder.annotation.LargeTest;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;

/**
 * Created by Wes on 9/8/2015.
 * This is an Espresso Test Class for testing button functionality.
 * Updated to test various other functionality - Brian Richardson
 * Tests delete and make flash, also checks for NULL entry prevention
 * Currently working.
 * RUN THIS TEST TWICE, IT POPULATES THE DATABASES FIRST. START IT WITH EMPTY DATABASES.
 * The testCheckButtons and testDeleteFlash tests will fail the first time this test is run
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
        onView(withId(R.id.homeButton)).perform(click());


    }

    /**Checks to see whether we can enter null value into our Quizzes*/
    public void testEmptyQuiz(){
        onView(withId(R.id.quiz_button)).perform(click());
        onView(withId(R.id.quiz_make)).perform(click());
        onView(withId(R.id.submit_button)).perform(click());
        Activity current = getActivity();
        assertNotNull(current);
        onView(withId(R.id.homeButton));
    }

    /**Tests our make flashcards function*/
    public void testMakeFlash() {
        onView(withId(R.id.flash_button)).perform(click());
        onView(withId(R.id.make_flash_button)).perform(click());
        onView(withId(R.id.flashCardsTitle))
                .perform(click())
                .perform(typeText("The best Flashcard"));
        onView(withId(R.id.flashCardsQuestion))
                .perform(click())
                .perform(typeText("Who is the coolest guy around?"));
        onView(withId(R.id.flashCardsAnswer))
                .perform(click())
                .perform(typeText("Brian Richardson"));
        onView(withId(R.id.submit_FlashCards_button)).perform(click());
        pressBack();
        pressBack();
    }


    /**Tests our delete flashcard function*/
    public void testDeleteFlash() {
        onView(withId(R.id.flash_button))
                .perform(click());
        onView(withId(R.id.all_flash_button))
                .perform(click());
        onData(startsWith("The best"))
                .inAdapterView(withId(R.id.listAll))
                .onChildView(withId(R.id.delete_btn))
                .perform(click());
        onView(withText(startsWith("No")))
                .perform(click());
        onData(startsWith("The best"))
                .inAdapterView(withId(R.id.listAll))
                .onChildView(withId(R.id.delete_btn))
                .perform(click());
        onView(withText(startsWith("Yes")))
                .perform(click());
        pressBack();
        pressBack();
    }
    /**Checks to see whether we can enter null value into our flash cards*/
        public void testEmptyFlash() {
        onView(withId(R.id.flash_button)).perform(click());
        onView(withId(R.id.make_flash_button)).perform(click());
        onView(withId(R.id.submit_FlashCards_button)).perform(click());
        pressBack();
        pressBack();
    }

}
