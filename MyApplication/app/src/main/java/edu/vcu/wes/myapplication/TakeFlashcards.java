package edu.vcu.wes.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

public class TakeFlashcards extends AppCompatActivity {
    TextView flashCardQuestion;
    TextView flashCardAnswer;
    Context context = this;
    ImageButton flashNext;
    Button getAnswer;
    private int flag, count = 0;
    DatabaseFunctions df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_flashcards);
        flashCardQuestion = (TextView) findViewById(R.id.flashQuestionText);
        flashCardAnswer = (TextView) findViewById(R.id.flashCardAnswerText);
        flashNext = (ImageButton) findViewById(R.id.nextFlashButton);
        getAnswer = (Button) findViewById(R.id.getAnswerButton);
        final Intent flashResults = new Intent (this, FlashResultsActivity.class);
        df = new DatabaseFunctions(TakeFlashcards.this);
        setQuestions();



        flashNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<String> questions = df.populateFlashCards(context, "questions");

                if (flag < questions.size() - 1) {
                    flag++;
                    count++;
                    setQuestions();
                    //flashNext.setVisibility(View.VISIBLE);

                }
                else{
                    //Start an activity that lets them review flashcards or go back to main menu.
                    startActivity(flashResults);
                    flashCardAnswer.setText("");
                }
                flag++;
            }
        });


        getAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
                //flashNext.setVisibility(View.GONE);
            }
        });
        df.close();
    }

    private void flipCard() {

        View rootLayout = findViewById(R.id.flash_activity_root);
        View cardFace = findViewById(R.id.flash_activity_card_front);
        View cardBack = findViewById(R.id.flash_activity_card_back);

        FlashCardFlip flipAnimation = new FlashCardFlip(cardFace, cardBack);
        //Set the answers once the get answer button is clicked.
        //This has to be delayed in order to make it show up on the back screen
        //Without first showing up on the front screen or having to hit get answer twice.
        flashNext.setVisibility(View.GONE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                setAnswers();
            }
        }, 500);

        if (cardFace.getVisibility() == View.GONE) {

            flipAnimation.reverse();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    flashNext.setVisibility(View.VISIBLE);
                }
            }, 500);
        }
        rootLayout.startAnimation(flipAnimation);

    }

    private void setQuestions(){

        ArrayList<String> flashQ = df.populateFlashCards(this, "questions");

        try {
            flashCardQuestion.setText(flashQ.get(flag));
        }
        catch(IndexOutOfBoundsException ie){
            ie.printStackTrace();
        }

    }

    private void setAnswers(){
        ArrayList<String> flashA = df.populateFlashCards(this, "answers");
        try{
            flashCardAnswer.setText(flashA.get(count));
        }
        catch(IndexOutOfBoundsException ie){
            ie.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_flashcards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}