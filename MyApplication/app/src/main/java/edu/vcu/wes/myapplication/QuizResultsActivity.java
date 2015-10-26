package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);



        TextView textView = (TextView) findViewById(R.id.resultsStats);
        //Set the text view to display the users statistics
        // after completing the quiz of all questions in the database.
        textView.setText("Correct: " + String.format("%5s", TakeQuiz.correct) + "\n" +
                        "Missed: " + String.format("%5s",TakeQuiz.wrong) + "\n" +
                        "Ratio: " + String.format("%10s", TakeQuiz.correct + " / "
                                 + TakeQuiz.total) + "\n" +
                        "Percent: " + String.format("%4.2f  ", TakeQuiz.percentCorrect) + "%");

        final Intent mainMenu = new Intent(this, MainActivity.class);
        final Intent restart = new Intent(this, TakeQuiz.class);
        Button restartButton = (Button) findViewById(R.id.restartButton);
        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);

        //Reset the counters for the right and wrong answers before restarting the activity.
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeQuiz.correct = 0;
                TakeQuiz.wrong = 0;
                TakeQuiz.total = 0;

                //Prevents user from going backwards after deleting a quiz into results screen
                //and trying to retake the last quiz when it is no longer there.
                DatabaseFunctions df = new DatabaseFunctions(QuizResultsActivity.this);
                if(!df.isEmpty()) {
                    startActivity(restart);
                }
                else{
                    Toast.makeText(QuizResultsActivity.this, "Nothing was found in the database", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Take the user back to the Take/Make/All Quizzes screen.
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainMenu);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
