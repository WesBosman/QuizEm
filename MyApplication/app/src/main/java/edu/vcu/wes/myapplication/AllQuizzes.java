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

/**
 * This method populated three views. One view is a simple list view that shows all quizzes.
 * The second view is an expandable view that is unordered.
 * The third view is in order based on title or Keys of the hash map data structure.
 */
public class AllQuizzes extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quizzes);
        final Context context = AllQuizzes.this;

        //Set up the home button.
        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
        final Intent homeScreen = new Intent(this, MainActivity.class);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreen);
            }
        });

        //1st Tab
        final ListView simpleList = (ListView) findViewById(R.id.listAll);
        //2nd Tab
        final ExpandableListView listView = (ExpandableListView) findViewById(android.R.id.list);
        //3rd Tab
        final ExpandableListView orderedView =  (ExpandableListView) findViewById(R.id.list_ordered);

        //Set up tab activity
        TabHost tabhost=(TabHost)findViewById(R.id.tabHost);
        tabhost.setup();

        //Set Tabs Only need two tabs. Two expandable lists and one non expandable list.
        TabHost.TabSpec spec1 = tabhost.newTabSpec("Tab 3");
        spec1.setIndicator("All Questions");
        spec1.setContent(R.id.tab1);
        final String tabOneTag = spec1.getTag();

        TabHost.TabSpec spec2 = tabhost.newTabSpec("Tab 2");
        spec2.setIndicator("Grouped By Title");
        spec2.setContent(R.id.tab2);
        final String tabTwoTab = spec2.getTag();

        TabHost.TabSpec spec3 = tabhost.newTabSpec("Tab 3");
        spec3.setIndicator("Ordered By Title");
        spec3.setContent(R.id.tab3);
        final String tabThreeTag = spec3.getTag();


        //This code will populate the list when the tab is switched!
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                DatabaseFunctions df = new DatabaseFunctions(context);


                if(tabOneTag.equals(tabId)){
                    //Populate 1st tab
                    df.populateQuizListAll(context, simpleList);
                }
                if(tabTwoTab.equals(tabId)){
                    //Populate 2nd tab
                    df.populateQuizList(context, listView, "");
                }
                if(tabThreeTag.equals(tabId)){
                    //Populate 3rd tab
                    df.populateQuizList(context, orderedView, "sort");
                }

                df.close();
            }
        });

        //Add Tabs
        tabhost.addTab(spec1);
        tabhost.addTab(spec2);
        tabhost.addTab(spec3);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
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
