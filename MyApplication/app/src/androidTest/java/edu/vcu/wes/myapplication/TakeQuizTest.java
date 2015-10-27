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
/**
 * Created by Wes on 10/1/2015.
 * This test class should make sure that a quiz can be made then taken then deleted.
 */
@RunWith(AndroidJUnit4.class)
public class TakeQuizTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    @Test
    public void testTakeQuiz(){
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
                .perform(typeText("EspressoTestTakeQuiz"))
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

        //Click submit button so that it gets stored in the database.
        onView(withId(R.id.submit_button))
                .perform(click());

        //Press back button to go to the quiz activity screen.
        //Click take quiz. Select the first answer and go to results page.
        pressBack();
        onView(withId(R.id.quiz_take))
                .perform(click());
        onView(withId(R.id.questionOneButton))
                .perform(click());
        onView(withId(R.id.nextButton))
                .perform(click());

        //Check results are correct.
        onView(withId(R.id.resultsStats))
                .check(matches(withText("Correct: " + String.format("%5s", TakeQuiz.correct) + "\n" +
                        "Missed: " + String.format("%5s",TakeQuiz.wrong) + "\n" +
                        "Ratio: " + String.format("%10s", TakeQuiz.correct + " / "
                        + TakeQuiz.total) + "\n" +
                        "Percent: " + String.format("%4.2f  ", TakeQuiz.percentCorrect) + "%")));

        //Return to the main menu.
        onView(withId(R.id.mainMenuButton))
                .perform(click());

        //Click quiz button from Main menu.
        onView(withId(R.id.quiz_button))
                .perform(click());

        //Click all quizzes.
        onView(withId(R.id.quiz_all))
                .perform(click());

        //Delete the quiz we just made and took.
        onData(startsWith("Espresso"))
                .inAdapterView(withId(android.R.id.list))
                .onChildView(withId(R.id.delete_btn))
                .perform(click());
        onView(withText(startsWith("Yes")))
                .perform(click());

    }

    @After
    public void afterAllTests(){



    }
}

