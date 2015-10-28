package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.amlcurran.showcaseview.ShowcaseView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Thomas on 10/26/2015.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityHelpTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    @Test
    public void testShowTutorial() throws Exception {

        //Click tutorial button.
        onView(withId(R.id.tutorial_btn))
                .perform(click());

        //First screen of MainActivity help
        /*onView(withId(com.github.amlcurran.showcaseview.ShowcaseView.))
                .check(matches(withText(R.string.help_getstarted)));
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_sub_text))
                .check(matches(withText(R.string.quizem)));*/
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.help_next)))
                .perform(click());

        //Second screen, showcasing quiz button
        /*onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_title_text))
                .check(matches(withText(R.string.main_help_quiz)));*/
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.help_next)))
                .perform(click());

        //Third screen, showcasing flash button
        /*onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_title_text))
                .check(matches(withText(R.string.main_help_flash)));*/
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.help_next)))
                .perform(click());

        //Fourth and final screen, showcasing help button
        /*onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_title_text))
                .check(matches(withText(R.string.main_help_help)));*/
        onView(withId(com.github.amlcurran.showcaseview.R.id.showcase_button))
                .check(matches(withText(R.string.close)))
                .perform(click());


    }

    @After
    public void afterAllTests(){

    }
}