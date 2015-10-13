package edu.vcu.wes.myapplication;



import edu.vcu.wes.Updated_App.ContentFragment;
import edu.vcu.wes.myapplication.R;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button quizBtn;
    private Button flashBtn;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize ToolBar and set it as actionbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            //This method will trigger on item Click of navigation
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    //Replacing the main content with ContentFragment
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home Selected", Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.quizzes:
                        Toast.makeText(getApplicationContext(), "Quiz Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.flashies:
                        Toast.makeText(getApplicationContext(), "FlashCards Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.helpers:
                        Toast.makeText(getApplicationContext(), "Help Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.Results:
                        Toast.makeText(getApplicationContext(), "Results Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.favorite:
                        Toast.makeText(getApplicationContext(), "Favorites Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.share:
                        Toast.makeText(getApplicationContext(), "Share Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Selected", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        //Initialize Drawer Layout
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        quizBtn = (Button) findViewById(R.id.quiz_button);
        flashBtn = (Button) findViewById(R.id.flash_button);
        final Intent quizScrn = new Intent(this, QuizActivity.class);
        final Intent flashScrn = new Intent(this, FlashActivity.class);

        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(quizScrn);
            }
        });

        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(flashScrn);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
