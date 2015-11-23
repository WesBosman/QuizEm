package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Adapter;
import android.widget.AdapterView;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Wes on 10/26/2015.
 */
@RunWith(AndroidJUnit4.class)
public class StudyFlashCardTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void beforeAllTests(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    @Test
    public void studyFlashCards() {
        /**
         * NOTE: This test will fail if any information has been entered into the FlashCard Table before
         *       the test is run. The reason it will fail is because it assumes it only has to click
         *       the next button once because it only entered one flashcard.
         */

        //Click flash button on main page.
        onView(withId(R.id.flash_button))
                .perform(click());

        //In flash activity click make flashcards.
        onView(withId(R.id.make_flash_button))
                .perform(click());

        //Enter some info. Submit.
        onView(withId(R.id.flashCardsTitle))
                .perform(typeText("EspressoFlashcard"));
        onView(withId(R.id.flashCardsQuestion))
                .perform(typeText("The sky is blue."));
        onView(withId(R.id.flashCardsAnswer))
                .perform(typeText("True"));
        onView(withId(R.id.submit_FlashCards_button))
                .perform(click());

        //Press back then click the flashcards button.
        pressBack();

        onView(withId(R.id.take_flashcard_button))
                .perform(click());

        //Check the question.
        onView(withId(R.id.flashQuestionText))
                .check(matches(withText("The sky is blue.")));

        //Click get answer.
        onView(withId((R.id.getAnswerButton)))
                .perform(click());

        //Check the answer.
        onView(withId(R.id.flashCardAnswerText))
                .check(matches(withText("True")));

        //Click the flip button again. To flip the flashcard back around to be able to press next.
        onView(withId(R.id.getAnswerButton))
                .perform(click());

        //Click next image button.
        onView(withId(R.id.nextFlashButton))
                .perform(click());

        //Check that we are on results page by checking the text view says done.
        onView(withId(R.id.textView))
                .check(matches(withText("Done")));

        //Should be on results page.
        onView(withId(R.id.mainFlashButton))
                .perform(click());

        //Click flash button. Go to flash activity.
        onView(withId(R.id.flash_button))
                .perform(click());

        //Click to go all flashcards.
        onView(withId(R.id.all_flash_button))
                .perform(click());

        onData(startsWith("Espresso"))
                .inAdapterView(withId(R.id.listAll))
                .onChildView(withId(R.id.delete_btn))
                .perform(click());
        onView(withText(startsWith("Yes")))
                .perform(click());
    }

}
