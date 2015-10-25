package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Wes on 10/1/2015.
 * This test enters information into the make a Quiz page and checks that it gets saved in the database.
 * by checking that the data gets pulled from the database and displayed on the All Quizzes page.
 */
@RunWith(AndroidJUnit4.class)
public class MakeQuizTest {

    @Rule
    public ActivityTestRule<MakeQuiz> rule = new ActivityTestRule<>(MakeQuiz.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    //This works need to check that the info got put into the database.
    @Test
    public void userInput() {

        onView(withId(R.id.editTextTitle)).perform(click())
                .perform(typeText("EspressoTestTitle"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextQuestion)).perform(click())
                .perform(typeText("EspressoTestQuestion"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextAnswerOne)).perform(click())
                .perform(typeText("EspressoTestAnswer1"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextAnswerTwo)).perform(click())
                .perform(typeText("EspressoTestAnswer2"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextAnswerThree)).perform(click())
                .perform(typeText("EspressoTestAnswer3"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextAnswerFour)).perform(click())
                .perform(typeText("EspressoTestAnswer4(Correct_Answer)"))
                .check(matches(isDisplayed()));

        //How to check if the information is stored in the database?
        onView(withId(R.id.submit_button)).perform(click());

        //Click All Quizzes button and check that the previous text was stored in the list view.
        //This would tell us that the information was stored in the database and then retrieved.
        onView(withId(R.id.all_quizzes_button)).perform(click());

        onData(startsWith("EspressoTestTitle"))
                .inAdapterView(withText(android.R.id.list));
        onData(startsWith("EspressoTestQuestion"))
                .inAdapterView(withText(android.R.id.list));
        onData(startsWith("EspressoTestAnswer1"))
                .inAdapterView(withText(android.R.id.list));
        onData(startsWith("EspressoTestAnswer2"))
                .inAdapterView(withText(android.R.id.list));
        onData(startsWith("EspressoTestAnswer3"))
                .inAdapterView(withText(android.R.id.list));
        onData(startsWith("EspressoTestAnswer4(Correct_Answer"))
                .inAdapterView(withText(android.R.id.list));

    }

    //Use view assert statements to test.
    //REMEMBER TO TRY TO TEST ALL POSSIBILITIES.
    @After
    public void afterAllTests(){

    }
}



