package edu.vcu.wes.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MakeFlashcards extends AppCompatActivity {
    private String titleFlash;
    private String questionFlash;
    private String answerFlash;
    private EditText userFlashTitle;
    private EditText userFlashQuestion;
    private EditText userFlashAnswer;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_flashcards);

        //Set editText fields and buttons/intents
        userFlashTitle = (EditText) findViewById(R.id.flashCardsTitle);
        userFlashQuestion = (EditText) findViewById(R.id.flashCardsQuestion);
        userFlashAnswer = (EditText) findViewById(R.id.flashCardsAnswer);
        ImageButton submitFlash = (ImageButton) findViewById(R.id.submit_FlashCards_button);
        ImageButton allFlash = (ImageButton) findViewById(R.id.all_flashCards_button);
        ImageButton homeButton = (ImageButton) findViewById(R.id.home_Button);
        final Intent allFlashScreen = new Intent(this, AllFlashcards.class);
        final Intent homeScreen = new Intent(this, MainActivity.class);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreen);
            }
        });

        submitFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleFlash = userFlashTitle.getText().toString();
                questionFlash = userFlashQuestion.getText().toString();
                answerFlash = userFlashAnswer.getText().toString();

                final boolean checkHasNumberOrLetter = titleFlash.matches(".*\\w.*") &&
                        questionFlash.matches(".*\\w.*") &&
                        answerFlash.matches(".*\\w.*");

                ctx = MakeFlashcards.this;
                DatabaseFunctions flashDb = new DatabaseFunctions(ctx);
                //Check that blank input can't be put into the database.
                if(checkHasNumberOrLetter) {
                    flashDb.insertIntoFlashDatabase(flashDb, titleFlash, questionFlash, answerFlash);
                    Toast.makeText(MakeFlashcards.this, "FlashCard Saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ctx, "Not valid input", Toast.LENGTH_SHORT).show();
                }
                flashDb.close();
                //Set all back Strings back to blank.
                userFlashTitle.setText("");
                userFlashQuestion.setText("");
                userFlashAnswer.setText("");
            }
        });

        allFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(allFlashScreen);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_flashcards, menu);
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
