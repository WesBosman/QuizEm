package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.onView;


/** tests scenarios 1 & 2 of Making a better quiz scenario.
 * Created by Charles on 10/27/2015.
 */

@RunWith(AndroidJUnit4.class)
public class MakeQuizTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    @Test
    public void MakeQuiz(){
        /**
         * NOTE: This method assumes that no information has been entered into the quiz table prior to
         *       running this test. If there is already information stored in the Quiz Table this Test
         *       will fail because it will assume it is on the results page after 1 click of the next button
         *       during the take quiz execution.
         */

        //Click the quiz button on the main screen.
        onView(withId(R.id.quiz_button))
                .perform(click());

        //click the make quiz button.
        onView(withId(R.id.quiz_make))
                .perform(click());

        //Type input and save it in the database.
        onView(withId(R.id.editTextTitle)).perform(click())
                .perform(typeText("EspressoTestMakeQuiz"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextQuestion)).perform(click())
                .perform(typeText("What color is the sky?"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextAnswerOne)).perform(click())
                .perform(typeText("Black"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextAnswerTwo)).perform(click())
                .perform(typeText("Red"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextAnswerThree)).perform(click())
                .perform(typeText("Green"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextCorrectAnswer)).perform(click())
                .perform(typeText("Blue (Correct_Answer)"))
                .check(matches(isDisplayed()));

        //Exit Keyboard mode (may have to remove depending on what device the test is run on i.e. emulator or phone)
        closeSoftKeyboard();
        //pressBack();

        //Click submit button so that it gets stored in the database.
        onView(withId(R.id.submit_button))
                .perform(click());



        //click all quizzzes button
        onView(withId(R.id.all_quizzes_button))
                .perform(click());



        //Delete the quiz we just made and took.
        onData(startsWith("Espresso"))
                .inAdapterView(withId(R.id.listAll))
                .onChildView(withId(R.id.delete_btn))
                .perform(click());
        onView(withText(startsWith("Yes")))
                .perform(click());

    }

}
