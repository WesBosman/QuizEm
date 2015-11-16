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

import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
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


/** tests scenario 4 of Making a better quiz scenario */


@RunWith(AndroidJUnit4.class)

/**
 * Created by Charles on 10/27/2015.
 */
public class MakeQuizTest_Scenario4 {   @Rule
public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    @Test
    public void MakeQuiz_Scenario4(){
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

            //CLick All Quizzes Button
        //Checks to make sure no quiz was created

        onView(withId(R.id.all_quizzes_button))
                .perform(click())
                .check(doesNotExist());






    }

    @After
    public void afterAllTests(){



    }


}
