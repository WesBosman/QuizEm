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

        //Click next button


        //Click next button

        //Click next button

        //Should be back on the original screen

    }

    @After
    public void afterAllTests(){

    }
}