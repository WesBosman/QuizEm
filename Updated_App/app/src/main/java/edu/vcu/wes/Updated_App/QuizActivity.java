package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity{
    private Button takeQuiz;
    private Button makeQuiz;
    private Button allQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        takeQuiz = (Button) findViewById(R.id.quiz_take);
        makeQuiz = (Button) findViewById(R.id.quiz_make);
        allQuiz = (Button) findViewById(R.id.quiz_all);

        final Intent quizMaker = new Intent(this,  MakeQuiz.class);
        final Intent allQuizzesScreen = new Intent(this, AllQuizzes.class);

        takeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Toast.makeText(QuizActivity.this, "Thie function has not been created yet",Toast.LENGTH_LONG).show();
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

/**
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case (R.id.quiz_take):
                Toast.makeText(this, "Thie function has not been created yet",Toast.LENGTH_LONG).show();
                break;
            case (R.id.quiz_make):
                final Intent quizMaker = new Intent(this,  MakeQuiz.class);
                startActivity(quizMaker);
                break;
            case (R.id.quiz_all):
                final Intent allQuizzesScreen = new Intent(this, AllQuizzes.class);
                startActivity(allQuizzesScreen);
                break;
            default:
                break;
        }
    }
 **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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
