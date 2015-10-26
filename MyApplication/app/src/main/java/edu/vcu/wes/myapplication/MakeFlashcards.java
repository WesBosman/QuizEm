package edu.vcu.wes.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        Button submitFlash = (Button) findViewById(R.id.submit_FlashCards_button);
        Button allFlash = (Button) findViewById(R.id.all_flashCards_button);
        final Intent allFlashScreen = new Intent(this, AllFlashcards.class);

        submitFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleFlash = userFlashTitle.getText().toString();
                questionFlash = userFlashQuestion.getText().toString();
                answerFlash = userFlashAnswer.getText().toString();
                ctx = MakeFlashcards.this;
                DatabaseFunctions flashDb = new DatabaseFunctions(ctx);
                flashDb.insertIntoFlashDatabase(flashDb,titleFlash, questionFlash, answerFlash);
                Toast.makeText(MakeFlashcards.this, "FlashCard Saved", Toast.LENGTH_SHORT).show();

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
