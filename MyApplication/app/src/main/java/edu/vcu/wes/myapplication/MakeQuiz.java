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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MakeQuiz extends AppCompatActivity {
    private String titleOfQuiz;
    private String questionOfQuiz;
    private String answerOfQuiz;
    private EditText userMadeTitle;
    private EditText userMadeQuestion;
    private EditText userMadeAnswer;
    private Button submitButton;
    private ImageButton newQuizButton;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_quiz);

        //Set the editText views and buttons
        userMadeTitle = (EditText) findViewById(R.id.editTextTitle);
        userMadeQuestion = (EditText) findViewById(R.id.editTextQuestion);
        userMadeAnswer = (EditText) findViewById(R.id.editTextAnswer);
        submitButton = (Button) findViewById(R.id.submit_button);
        newQuizButton = (ImageButton) findViewById(R.id.addQuestionButton);
        final Intent allQuizzesScreen = new Intent(this, AllQuizzes.class);

        //Submit button should get user input store it in the database and make a toast to let
        //User know that it has been saved in the database.
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleOfQuiz = userMadeTitle.getText().toString();
                questionOfQuiz = userMadeQuestion.getText().toString();
                answerOfQuiz = userMadeAnswer.getText().toString();
                ctx = MakeQuiz.this;
                DatabaseFunctions database = new DatabaseFunctions(ctx);
                database.insertIntoDatabase(database, titleOfQuiz, questionOfQuiz, answerOfQuiz);
                Toast.makeText(MakeQuiz.this, "Quiz Saved", Toast.LENGTH_SHORT).show();

                startActivity(allQuizzesScreen);
            }
        });

        newQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuizButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_quiz, menu);
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
