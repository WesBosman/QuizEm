package edu.vcu.wes.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        setQuestions();



        flashNext.setOnClickListener(new View.OnClickListener() {
            DatabaseFunctions df = new DatabaseFunctions(TakeFlashcards.this);
            @Override
            public void onClick(View v) {
                ArrayList<String> questions = df.populateFlashCards(context, "questions");

                if (flag < questions.size() - 1) {
                    flag++;
                    count++;
                    setQuestions();
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
                setAnswers();
            }
        });
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
