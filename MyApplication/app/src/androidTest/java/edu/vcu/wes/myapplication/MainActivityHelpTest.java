package edu.vcu.wes.myapplication;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Thomas on 10/26/2015.
 */
public class MainActivityHelpTest {

    @Test
    public void testShowTutorial() throws Exception {
        onView(withId(R.id.tutorial_btn))
                .perform(click())
                //.check(matches(withText("@string/tutorial")))
                ;

    }
}