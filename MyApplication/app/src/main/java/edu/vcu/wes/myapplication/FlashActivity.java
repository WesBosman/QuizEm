package edu.vcu.wes.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        Button takeFlash = (Button) findViewById(R.id.take_flash_button);
        Button makeFlash = (Button) findViewById(R.id.make_flash_button);
        Button allFlash = (Button) findViewById(R.id.all_flash_button);

        final Intent flashTake = new Intent(this, TakeFlashcards.class);
        final Intent flashMake = new Intent(this, MakeFlashcards.class);
        final Intent flashAll = new Intent(this, AllFlashcards.class);
        FlashDatabaseFunctions flashDb = new FlashDatabaseFunctions(this);
        if(!flashDb.isEmpty()) {
            takeFlash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(flashTake);
                }
            });
        }
        else{
            Toast.makeText(this, "There is nothing in the database.", Toast.LENGTH_SHORT).show();
        }

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
}
