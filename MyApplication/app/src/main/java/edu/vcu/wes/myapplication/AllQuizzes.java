package edu.vcu.wes.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class AllQuizzes extends ListActivity {
    Context context;
    ArrayList<String> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quizzes);
        context = AllQuizzes.this;
        results = new ArrayList<>();

        /** Access the database and populate the all quizzes screen (List View)
         *
         *  UPDATE: Now populates with titles of quizzes.
         */
        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String title = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_TITLE));
                        results.add("" + title + "");
                    } while (c.moveToNext());
                }
            }

            //This can set how the list can be viewed for example making clickable list items.
            this.setListAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_expandable_list_item_1, results));

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }
        //May want a finally clause for some reason later.


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_quizzes, menu);
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
