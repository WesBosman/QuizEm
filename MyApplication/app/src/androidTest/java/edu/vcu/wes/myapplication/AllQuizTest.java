package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.onData;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.onView;
/**
 * Created by Wes on 10/1/2015.
 * This test class should make sure that an item can be deleted from the database/list adapter
 */
@RunWith(AndroidJUnit4.class)
public class AllQuizTest {

    @Rule
    public ActivityTestRule<AllQuizzes> rule = new ActivityTestRule<>(AllQuizzes.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    /**
     * This method should test the deleting an item from the listView works correctly.
     * the item to be deleted should be the item at the top of the list or the first item that starts with
     * title: which would be any entry in the list. So first we have to find the entry in the list adapter
     * then we can delete it.
     * The first entry in my list according to the database I have made is
     * title: title
     * question: question
     * answer: answers
     * I chose this one because it was visible (at top of list) so I can check if the test deletes it or not.
     */
    @Test
    public void testDeleteEntry(){
        onData(hasToString(startsWith("title: ")))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());

       /** onData(hasToString(startsWith("question: EspressoTestQuestion")))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onData(hasToString(startsWith("answer: EspressoTestAnswer")))
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        **/
    }

    @After
    public void afterAllTests(){

    }
}
