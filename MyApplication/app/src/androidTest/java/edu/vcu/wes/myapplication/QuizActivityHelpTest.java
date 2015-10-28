package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Thomas on 10/28/2015.
 */
@RunWith(AndroidJUnit4.class)
public class QuizActivityHelpTest {

    @Rule
    public ActivityTestRule<QuizActivity> rule = new ActivityTestRule<>(QuizActivity.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    @Test
    public void testShowTutorial() throws Exception {

        //Click tutorial button.
        onView(withId(R.id.help_btn))
                .perform(click());

        //First screen of QuizActivity help
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.help_next)))
                .perform(click());

        //Second screen, showcasing take-a-quiz button
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.help_next)))
                .perform(click());

        //Third screen, showcasing make-a-quiz button
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.help_next)))
                .perform(click());

        //Fourth screen, showcasing all-quizzes button
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.help_next)))
                .perform(click());

        //Fifth and final screen, showcasing help button
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.close)))
                .perform(click());

    }
}