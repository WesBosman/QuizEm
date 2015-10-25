package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Button takeQuiz = (Button) findViewById(R.id.quiz_take);
        Button makeQuiz = (Button) findViewById(R.id.quiz_make);
        Button allQuiz = (Button) findViewById(R.id.quiz_all);



        final Intent quizMaker = new Intent(this,  MakeQuiz.class);
        final Intent allQuizzesScreen = new Intent(this, AllQuizzes.class);
        final Intent takeQuizScreen = new Intent(this, TakeQuiz.class);


            //Check that there is information in the database.
            //Before user can click the take quiz button.
            takeQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseFunctions df = new DatabaseFunctions(QuizActivity.this);
                    if (!df.isEmpty()) {
                        startActivity(takeQuizScreen);
                    }
                    else {
                        Toast.makeText(QuizActivity.this, "There is no info in database to Take a Quiz.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        makeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(quizMaker);
            }
        });

        allQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
