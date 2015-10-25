package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private ShowcaseView showcaseview;
    private int count = 0;
    private Target t1, t2, t3, t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Button takeQuiz = (Button) findViewById(R.id.quiz_take);
        Button makeQuiz = (Button) findViewById(R.id.quiz_make);
        Button allQuiz = (Button) findViewById(R.id.quiz_all);


        final Intent quizMaker = new Intent(this, MakeQuiz.class);
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
                } else {
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

        t1 = new ViewTarget(R.id.quiz_take, this);
        t2 = new ViewTarget(R.id.quiz_make, this);
        t3 = new ViewTarget(R.id.quiz_all, this);
        t4 = new ViewTarget(R.id.help_btn, this);

        showcaseview = new ShowcaseView.Builder(this)
                .setTarget(Target.NONE)
                .setOnClickListener(this)
                .setContentTitle("Don't know what to do next?\n Fear not! We've gotcha covered!")
                .setContentText("Quiz'Em")
                .setStyle(R.style.Tutorial2)
                .build();
        showcaseview.setButtonText("Next");
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

    @Override
    public void onClick(View v) {
        switch (count) {
            case 0:
                showcaseview.setShowcase(t1, true);
                showcaseview.setContentTitle("Take A Quiz");
                showcaseview.setContentTitle("Tap The 'Take A Quiz' Button To Quiz Yourself After Creating Questions! ");
                break;
            case 1:
                showcaseview.setShowcase(t2, true);
                showcaseview.setContentTitle("Make A Quiz");
                showcaseview.setContentTitle("Tap The 'Make A Quiz' Button To Make Questions And Answers!");
                break;

            case 2:
                showcaseview.setShowcase(t3, true);
                showcaseview.setContentTitle("All Quizzes");
                showcaseview.setContentTitle("Tap The 'All Quizzes' Button To View All Questions!");
                showcaseview.setButtonText("Close");
                break;
            case 3:
                showcaseview.setShowcase(t4, true);
                showcaseview.setContentTitle("Quiz'Em");
                showcaseview.setContentTitle("Need To Review Again? No Worries, Just Tap The Help Button!");
                break;

            case 4:
                showcaseview.hide();
                break;
        }
        count++;
    }

    public void showTutorial(View v) {
        count = 0;
        showcaseview.show();
        showcaseview.setTarget(Target.NONE);
        showcaseview.setContentTitle("Help");
        showcaseview.setContentText("Quiz'Em");
        showcaseview.setButtonText("Next");
    }
}
