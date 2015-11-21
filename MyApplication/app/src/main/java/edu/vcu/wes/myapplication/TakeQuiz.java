package edu.vcu.wes.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
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
import java.util.Collections;

public class TakeQuiz extends AppCompatActivity {
    TextView tv;
    Context context = this;
    RadioGroup rg;
    RadioButton rb1,rb2,rb3,rb4;
    ImageButton btnNext;
    DatabaseFunctions df;
    private String isCorrect;
    public static double percentCorrect;
    public static int correct, wrong, total;
    private int flag = 0;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        df = new DatabaseFunctions(context);
        rg = (RadioGroup)findViewById(R.id.rg);
        tv = (TextView)findViewById(R.id.questionsText);
        rb1 = (RadioButton)findViewById(R.id.questionOneButton);
        rb2 = (RadioButton)findViewById(R.id.questionTwoButton);
        rb3 = (RadioButton)findViewById(R.id.questionThreeButton);
        rb4 = (RadioButton)findViewById(R.id.questionFourButton);
        btnNext = (ImageButton)findViewById(R.id.nextButton);

        //This calls the method for initially setting the questions and answers.
        //If there is information in the database populate the list.
        setQuestions();



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton yourAnswer = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                if(yourAnswer != null) {
                String answerText = yourAnswer.getText().toString();
                ArrayList<String> questions = df.populateQuiz(context, "questions");

                    if ((answerText).equals(getCorrectAnswer())) {
                        yourAnswer.setPadding(10, 10, 10, 10);
                        yourAnswer.setBackgroundColor(Color.GREEN); //Highlight correct answer.
                        correct++;
                        Toast.makeText(TakeQuiz.this, "Correct", Toast.LENGTH_SHORT).show();

                    } else {
                        yourAnswer.setPadding(10, 10, 10, 10);
                        yourAnswer.setBackgroundColor(Color.RED); //Set color red if wrong answer.
                        wrong++;
                        Toast.makeText(TakeQuiz.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    }
                    flag++;

                    //If there are more questions then set them and allow the user to answer them.
                    if (flag < questions.size()) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setQuestions();
                                rb1.setBackgroundColor(Color.TRANSPARENT);
                                rb2.setBackgroundColor(Color.TRANSPARENT);
                                rb3.setBackgroundColor(Color.TRANSPARENT);
                                rb4.setBackgroundColor(Color.TRANSPARENT);
                            }
                        }, 1000);
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

    //This method is not really random. Instead it makes an array list, shuffles it and then
    //Takes the first number out of the list to help with randomizing the correct answers.
    private int randomNumber(){
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        Collections.shuffle(numbers);
        return numbers.remove(0);
    }

    //This method sets up the questions and answers after getting them from the database.
    //Uses a switch statement for randomizing where the correct answers are on the page
    private void setQuestions(){
        ArrayList<String> questions = df.populateQuiz(context, "questions");
        ArrayList<String> answers = df.populateQuiz(context, "answers");

        try {

            switch (randomNumber()) {
                case 1:
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
                    break;
                case 2:
                    tv.setText(questions.get(flag));
                    rb3.setText(answers.get(count));
                    count++;
                    rb4.setText(answers.get(count));
                    count++;
                    rb1.setText(answers.get(count));
                    count++;
                    rb2.setText(answers.get(count));
                    setCorrectAnswer(answers.get(count));
                    count++;
                    break;
                case 3:
                    tv.setText(questions.get(flag));
                    rb2.setText(answers.get(count));
                    count++;
                    rb3.setText(answers.get(count));
                    count++;
                    rb4.setText(answers.get(count));
                    count++;
                    rb1.setText(answers.get(count));
                    setCorrectAnswer(answers.get(count));
                    count++;
                    break;
                case 4:
                    tv.setText(questions.get(flag));
                    rb4.setText(answers.get(count));
                    count++;
                    rb1.setText(answers.get(count));
                    count++;
                    rb2.setText(answers.get(count));
                    count++;
                    rb3.setText(answers.get(count));
                    setCorrectAnswer(answers.get(count));
                    count++;
                    break;

            }
        df.close();

        }catch(IndexOutOfBoundsException ie) {
            Toast.makeText(getApplicationContext(), "Nothing in database.",Toast.LENGTH_LONG).show();
            Log.d("TakeQuiz:", "The database has nothing in it at this time.");
        }

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
