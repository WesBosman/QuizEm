package edu.vcu.wes.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class AllQuizzes extends ListActivity {
    private Context context = AllQuizzes.this;
    private ArrayList<String> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quizzes);
        /** Access the database and populate the all quizzes screen (List View)
         *  UPDATE: Now populates with titles of quizzes.
         */
        populate();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Try to get the item that was clicked
        String newQuestion = results.get(position);
        DatabaseFunctions df = new DatabaseFunctions(context);
        df.deleteFromDatabase(newQuestion.toString());

        //This deletes the item based on its position in the list
        //It then repopulates the list.
        DialogBoxDelete dbd = new DialogBoxDelete(AllQuizzes.this);
        dbd.show();

        //View yesView = findViewById(R.id.yes_button);

        /**
         * This will delete an item when clicked.
         * Be Careful.
         *
         * results.remove(position);
           populate();
         *
         */


        //final Intent takeQuiz = new Intent(this, TakeQuiz.class);
        //startActivity(takeQuiz);
    }

    /**
     * This method populates the list view with user inputs.
     * It uses a cursor to move through the columns of the database and populate the list view.
     */
    public void populate(){
        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String title = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_TITLE));
                        String question = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_QUESTION));
                        String answer = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_ANSWER));
                        results.add("title: " + title + "\n" + "question: " + question + "\n" + "answer: " + answer);
                        ;
                    } while (c.moveToNext());
                }
            }
            //This can set how the list can be viewed for example making clickable list items.
            setListAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_expandable_list_item_1, results));

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }
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
