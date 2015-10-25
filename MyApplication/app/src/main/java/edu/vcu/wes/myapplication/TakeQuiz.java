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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class TakeQuiz extends AppCompatActivity {
    TextView tv;
    Context context = this;
    RadioGroup rg;
    RadioButton rb1,rb2,rb3,rb4;
    ImageButton btnNext, btnBack;
    private String isCorrect;
    public static double percentCorrect;
    public static int correct, wrong, total;
    private int count = 0;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        rg = (RadioGroup)findViewById(R.id.rg);
        tv = (TextView)findViewById(R.id.questionsText);
        rb1 = (RadioButton)findViewById(R.id.questionOneButton);
        rb2 = (RadioButton)findViewById(R.id.questionTwoButton);
        rb3 = (RadioButton)findViewById(R.id.questionThreeButton);
        rb4 = (RadioButton)findViewById(R.id.questionFourButton);
        btnNext = (ImageButton)findViewById(R.id.nextButton);
        btnBack = (ImageButton) findViewById(R.id.backButton);

        //This calls the method for initially setting the questions and answers.
        //If there is information in the database populate the list.
        setQuestions();



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton yourAnswer = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                if(yourAnswer != null) {
                String answerText = yourAnswer.getText().toString();
                ArrayList<String> questions = populate("questions");

                    if ((answerText).equals(getCorrectAnswer())) {
                        correct++;
                        Toast.makeText(TakeQuiz.this, "Correct", Toast.LENGTH_SHORT).show();

                    } else {
                        wrong++;
                        Toast.makeText(TakeQuiz.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    }
                    flag++;

                    //If there are more questions then set them and allow the user to answer them.
                    if (flag < questions.size()) {
                        setQuestions();
                    }

                    //Otherwise set the percentage of correct answers out of the total
                    //Start the results activity.
                    else {
                        total = correct + wrong;
                        setPercentCorrect(correct, wrong);
                        Intent Intent = new Intent(getApplicationContext(), QuizResultsActivity.class);
                        startActivity(Intent);
                    }
                }
                else{
                    Toast.makeText(TakeQuiz.this, "Select an answer.", Toast.LENGTH_LONG).show();
                }

            }
        });

        // Back button not yet implemented want it invisible on first page and then visible on the following pages.
        // Would be useful so that the user can go back to edit questions before submiting.
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TakeQuiz.this, "Feature not yet implemented",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Set the correct percentage to a static variable that can be called from outside of this class.
    private void setPercentCorrect(double right, double wrong){
        percentCorrect = (right / (right + wrong)) * 100;
    }

    private void setCorrectAnswer(String answer){
        isCorrect = answer;
    }

    private String getCorrectAnswer(){
        return isCorrect;
    }

    //This method sets up the questions and answers after getting them from the database.
    private void setQuestions(){
        ArrayList<String> questions = populate("questions");
        ArrayList<String> answers = populate("answers");

        try {
            tv.setText(questions.get(flag));
            rb1.setText(answers.get(count));
            count++;
            rb2.setText(answers.get(count));
            count++;
            rb3.setText(answers.get(count));
            count++;
            rb4.setText(answers.get(count));
            setCorrectAnswer(answers.get(count));
            count++;
        }catch(IndexOutOfBoundsException ie) {
            Toast.makeText(getApplicationContext(), "Nothing in database.",Toast.LENGTH_LONG).show();
            Log.d("TakeQuiz:", "The database has nothing in it at this time.");
        }

    }

    /**
     * This is an Example of DRY it either needs to be changed or I
     * May need to move this method so it can be called from any class!!!
     */
    public ArrayList<String> populate(String columns){
        ArrayList<String> fromDatabase = new ArrayList<>();
        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String title = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_TITLE));
                        String question = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_QUESTION));
                        String answerOne = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_ANSWER1));
                        String answerTwo = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_ANSWER2));
                        String answerThree = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_ANSWER3));
                        String answerCorrect = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_CORRECT_ANSWER));

                        switch(columns){
                            case "title":
                                fromDatabase.add(title);
                                break;
                            case "questions":
                                fromDatabase.add(question);
                                break;
                            case "answers":
                                fromDatabase.add(answerOne);
                                fromDatabase.add(answerTwo);
                                fromDatabase.add(answerThree);
                                fromDatabase.add(answerCorrect);
                                break;
                        }
                    } while (c.moveToNext());
                }
            }
            c.close();
            df.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or open the database");
        }catch(NullPointerException ne){
            Log.d("TakeQuiz: ", "Nullpointer thrown.");
        }
        return fromDatabase;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questions, menu);
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
