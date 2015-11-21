package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

        ImageButton takeQuiz = (ImageButton) findViewById(R.id.quiz_take);
        ImageButton makeQuiz = (ImageButton) findViewById(R.id.quiz_make);
        ImageButton allQuiz = (ImageButton) findViewById(R.id.quiz_all);
        ImageButton helpBtn = (ImageButton) findViewById(R.id.help_btn);


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
                df.close();
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

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorial(v);
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

    @Override
    public void onClick(View v) {
        switch (count) {
            case 0:
                showcaseview.setShowcase(t1, true);
                showcaseview.setContentTitle(getString(R.string.take_quiz));
                showcaseview.setContentTitle(getString(R.string.quiz_help_takequiz));
                break;
            case 1:
                showcaseview.setShowcase(t2, true);
                showcaseview.setContentTitle(getString(R.string.make_quiz));
                showcaseview.setContentTitle(getString(R.string.quiz_help_makequiz));
                break;

            case 2:
                showcaseview.setShowcase(t3, true);
                showcaseview.setContentTitle(getString(R.string.all_quizzes));
                showcaseview.setContentTitle(getString(R.string.quiz_help_allquizzes));
                break;
            case 3:
                showcaseview.setShowcase(t4, true);
                showcaseview.setContentTitle(getString(R.string.quizem));
                showcaseview.setContentTitle(getString(R.string.quiz_help_help));
                showcaseview.setButtonText(getString(R.string.close));
                break;

            case 4:
                showcaseview.hide();
                break;
        }
        count++;
    }

    public void showTutorial(View v) {
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

        count = 0;
        showcaseview.show();
        showcaseview.setTarget(Target.NONE);
        showcaseview.setContentTitle(getString(R.string.help_button));
        showcaseview.setContentText(getString(R.string.quizem));
        showcaseview.setButtonText(getString(R.string.help_next));
    }
}
