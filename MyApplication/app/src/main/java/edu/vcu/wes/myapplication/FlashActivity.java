package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;



public class FlashActivity extends AppCompatActivity implements View.OnClickListener{
    private ShowcaseView showcaseview;
    private int count = 0;
    private Target t1, t2, t3,t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        Button takeFlash = (Button) findViewById(R.id.take_flash_button);
        Button makeFlash = (Button) findViewById(R.id.make_flash_button);
        Button allFlash = (Button) findViewById(R.id.all_flash_button);
        ImageButton helpBtn = (ImageButton) findViewById(R.id.help_btn1);

        final Intent flashTake = new Intent(this, TakeFlashcards.class);
        final Intent flashMake = new Intent(this, MakeFlashcards.class);
        final Intent flashAll = new Intent(this, AllFlashcards.class);



            takeFlash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseFunctions df = new DatabaseFunctions(FlashActivity.this);
                    if(!df.isFlashEmpty()) {
                    startActivity(flashTake);
                    }
                    else{
                        Toast.makeText(FlashActivity.this, "There is nothing in the database.", Toast.LENGTH_LONG).show();
                    }
                }
            });


        makeFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(flashMake);
            }
        });

        allFlash.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(flashAll);
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
        MenuInflater flashInflate = getMenuInflater();
        flashInflate.inflate(R.menu.menu_options_bar, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // THE BUTTONS ARE NOT WORKING YET.
        switch (item.getItemId()) {
            case R.id.action_settings:
                openOptionsMenu();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (count) {
            case 0:
                showcaseview.setShowcase(t1, true);
                showcaseview.setContentTitle(getString(R.string.take_flashcards));
                showcaseview.setContentTitle(getString(R.string.flash_help_takeflashcards));
                break;
            case 1:
                showcaseview.setShowcase(t2, true);
                showcaseview.setContentTitle(getString(R.string.make_flashcards));
                showcaseview.setContentTitle(getString(R.string.flash_help_makeflashcards));
                break;

            case 2:
                showcaseview.setShowcase(t3, true);
                showcaseview.setContentTitle(getString(R.string.all_flashcards));
                showcaseview.setContentTitle(getString(R.string.flash_help_allflashcards));
                break;
            case 3:
                showcaseview.setShowcase(t4, true);
                showcaseview.setContentTitle(getString(R.string.quizem));
                showcaseview.setContentTitle(getString(R.string.flash_help_help));
                showcaseview.setButtonText(getString(R.string.close));
                break;

            case 4:
                showcaseview.hide();
                break;
        }
        count++;
    }

    public void showTutorial(View v) {
        t1 = new ViewTarget(R.id.take_flash_button, this);
        t2 = new ViewTarget(R.id.make_flash_button, this);
        t3 = new ViewTarget(R.id.all_flash_button, this);
        t4 = new ViewTarget(R.id.help_btn1, this);

        showcaseview = new ShowcaseView.Builder(this)
                .setTarget(Target.NONE)
                .setOnClickListener(this)
                .setContentTitle("Let's Get Started, Shall We?")
                .setContentText("Quiz'Em")
                .setStyle(R.style.Tutorial3)
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
