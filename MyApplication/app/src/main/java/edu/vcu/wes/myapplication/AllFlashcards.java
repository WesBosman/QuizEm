package edu.vcu.wes.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

public class AllFlashcards extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_flashcards);
        Context context = AllFlashcards.this;

        //1st Tab. All Questions
        ListView simple = (ListView) findViewById(R.id.listAll);
        //2nd Tab. Group Unordered
        ExpandableListView listView = (ExpandableListView) findViewById(android.R.id.list);
        //3rd Tab. Ordered Group
        ExpandableListView listOrdered = (ExpandableListView) findViewById(R.id.list_ordered);


        DatabaseFunctions df = new DatabaseFunctions(context);
        df.populateFlashListAll(context, simple);
        df.populateFlashList(context, listView, " ");
        df.populateFlashList(context, listOrdered, "sort");
        df.close();

        //Set Home Button
        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
        final Intent homeScreen = new Intent(this, MainActivity.class);

        //Set up tab activity
        TabHost tabhost=(TabHost)findViewById(R.id.tabHost);
        tabhost.setup();

        //Set Tabs Only need two tabs. Two expandable lists and one non expandable list.
        TabHost.TabSpec spec1=tabhost.newTabSpec("Tab 3");
        spec1.setIndicator("All Flashcards");
        spec1.setContent(R.id.tab1);

        TabHost.TabSpec spec2 = tabhost.newTabSpec("Tab 2");
        spec2.setIndicator("Grouped By Title");
        spec2.setContent(R.id.tab2);

        TabHost.TabSpec spec3 = tabhost.newTabSpec("Tab 3");
        spec3.setIndicator("Ordered By Title");
        spec3.setContent(R.id.tab3);


        //Add Tabs
        tabhost.addTab(spec1);
        tabhost.addTab(spec2);
        tabhost.addTab(spec3);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreen);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_flashcards, menu);
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
