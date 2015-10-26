package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShowcaseView showcaseview;
    private int count = 0;
    private Target t1, t2, t3;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button quizBtn = (Button) findViewById(R.id.quiz_button);
        Button flashBtn = (Button) findViewById(R.id.flash_button);
        final Intent quizScreen = new Intent(this, QuizActivity.class);
        final Intent flashScreen = new Intent(this, FlashActivity.class);

        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(quizScreen);
            }
        });

        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(flashScreen);
            }
        });

        if(counter < 1) {
        t1 = new ViewTarget(R.id.quiz_button, this);
        t2 = new ViewTarget(R.id.flash_button, this);
        t3 = new ViewTarget(R.id.tutorial_btn, this);


            showcaseview = new ShowcaseView.Builder(this)
                    .setTarget(Target.NONE)
                    .setOnClickListener(this)
                    .setContentTitle("Let's Get Started, Shall We?")
                    .setContentText("Quiz'Em")
                    .setStyle(R.style.Tutorial)
                    .build();
            showcaseview.setButtonText("Next");
            counter ++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater flashInflate = getMenuInflater();
        flashInflate.inflate(R.menu.menu_options_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_help:
                openOptionsMenu();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (count) {
            case 0:
                showcaseview.setShowcase(t1, true);
                showcaseview.setContentTitle("Quiz");
                showcaseview.setContentTitle("Tap The Quiz Button To Create Questions And Quiz Yourself!");
                break;
            case 1:
                showcaseview.setShowcase(t2, true);
                showcaseview.setContentTitle("FlashCards");
                showcaseview.setContentTitle("Tap The Flash Button To Create Flashcards And Study!");
                break;

            case 2:
                showcaseview.setShowcase(t3, true);
                showcaseview.setContentTitle("Tutorial");
                showcaseview.setContentTitle("Need To Review Again? No Worries, Just Tap The Tutorial Icon!");
                showcaseview.setButtonText("Close");
                break;

            case 3:
                showcaseview.hide();
                break;
        }
        count++;
    }

    public void showTutorial(View v) {
        count = 0;
        showcaseview.show();
        showcaseview.setTarget(Target.NONE);
        showcaseview.setContentTitle("Tutorial");
        showcaseview.setContentText("Quiz'Em");
        showcaseview.setButtonText("Next");


    }


}
