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

public class MakeQuiz extends AppCompatActivity {
    private String titleOfQuiz;
    private String questionOfQuiz;
    private String answerOneQuiz;
    private String answerTwoQuiz;
    private String answerThreeQuiz;
    private String answerCorrectQuiz;
    private EditText userMadeTitle;
    private EditText userMadeQuestion;
    private EditText userMadeAnswer1;
    private EditText userMadeAnswer2;
    private EditText userMadeAnswer3;
    private EditText userMadeCorrectAnswer;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_quiz);

        //Set the editText views and buttons
        userMadeTitle = (EditText) findViewById(R.id.editTextTitle);
        userMadeQuestion = (EditText) findViewById(R.id.editTextQuestion);
        userMadeAnswer1 = (EditText) findViewById(R.id.editTextAnswerOne);
        userMadeAnswer2 = (EditText) findViewById(R.id.editTextAnswerTwo);
        userMadeAnswer3 = (EditText) findViewById(R.id.editTextAnswerThree);
        userMadeCorrectAnswer = (EditText) findViewById(R.id.editTextCorrectAnswer);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        Button allQuizzesButton = (Button) findViewById(R.id.all_quizzes_button);
        final Intent allQuizzesScreen = new Intent(this, AllQuizzes.class);



        //Submit button should get user input store it in the database and make a toast to let
        //User know that it has been saved in the database.
        //We are saving 4 answers to a question now and the last question should be the correct one.


            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleOfQuiz = userMadeTitle.getText().toString();
                    questionOfQuiz = userMadeQuestion.getText().toString();
                    answerOneQuiz = userMadeAnswer1.getText().toString();
                    answerTwoQuiz = userMadeAnswer2.getText().toString();
                    answerThreeQuiz = userMadeAnswer3.getText().toString();
                    answerCorrectQuiz = userMadeCorrectAnswer.getText().toString();

                    final boolean checkHasLetter = titleOfQuiz.matches(".*\\D.*") &&
                            questionOfQuiz.matches(".*\\D.*") &&
                            answerOneQuiz.matches(".*\\D.*") &&
                            answerTwoQuiz.matches(".*\\D.*") &&
                            answerThreeQuiz.matches(".*\\D.*") &&
                            answerCorrectQuiz.toString().matches(".*\\D.*");

                    final boolean checkHasNumber = titleOfQuiz.matches(".*\\d.*") &&
                            questionOfQuiz.matches(".*\\d.*") &&
                            answerOneQuiz.matches(".*\\d.*") &&
                            answerTwoQuiz.matches(".*\\d.*") &&
                            answerThreeQuiz.matches(".*\\d.*") &&
                            answerCorrectQuiz.matches(".*\\d.*");

                    ctx = MakeQuiz.this;
                    DatabaseFunctions database = new DatabaseFunctions(ctx);
                    if(checkHasLetter || checkHasNumber) {
                        database.insertIntoDatabase(database, titleOfQuiz, questionOfQuiz,
                                answerOneQuiz, answerTwoQuiz, answerThreeQuiz, answerCorrectQuiz);
                        Toast.makeText(MakeQuiz.this, "Quiz Saved", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ctx,"Not valid input",Toast.LENGTH_SHORT).show();
                    }

                    //Set all the fields to blank and start over.
                    userMadeTitle.setText("");
                    userMadeQuestion.setText("");
                    userMadeAnswer1.setText("");
                    userMadeAnswer2.setText("");
                    userMadeAnswer3.setText("");
                    userMadeCorrectAnswer.setText("");
                }
            });

        allQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(allQuizzesScreen);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options_bar, menu);
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
