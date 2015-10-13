package edu.vcu.wes.myapplication;

<<<<<<< HEAD:Indecisives-app_project_355/app/src/main/java/edu/vcu/wes/Updated_App/TakeQuiz.java
import android.support.v7.app.AppCompatActivity;
=======
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
>>>>>>> refs/remotes/origin/master:MyApplication/app/src/main/java/edu/vcu/wes/myapplication/AllQuizzes.java
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

<<<<<<< HEAD:Indecisives-app_project_355/app/src/main/java/edu/vcu/wes/Updated_App/TakeQuiz.java
public class TakeQuiz extends AppCompatActivity {
=======
public class AllQuizzes extends ListActivity {
    private Context context;
    private ArrayList<String> results;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
>>>>>>> refs/remotes/origin/master:MyApplication/app/src/main/java/edu/vcu/wes/myapplication/AllQuizzes.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:Indecisives-app_project_355/app/src/main/java/edu/vcu/wes/Updated_App/TakeQuiz.java
        setContentView(R.layout.activity_take_quiz);
    }

=======
        setContentView(R.layout.activity_all_quizzes);
        listView = (ListView) findViewById(android.R.id.list);
        results = new ArrayList<>();
        context = AllQuizzes.this;
        populate();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /**
         * This method gets called when an item in the list is clicked.
         * It then gets the position of the item in the list view.
         * If the user clicks yes it deletes the item from the list.
         * we could later make it for take a quiz or edit a quiz.
         */
        final String newQuestion = results.get(position).toString();
        final DatabaseFunctions dataFunctions = new DatabaseFunctions(context);
        AlertDialog.Builder deleteDialogBox = new AlertDialog.Builder(AllQuizzes.this);
        deleteDialogBox.setTitle("Delete?");
        deleteDialogBox.setMessage("Are you sure you want to delete this?");
        final int positionToBeRemoved = position;
        deleteDialogBox.setNegativeButton("No", null);
        deleteDialogBox.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dataFunctions.deleteFromDatabase(newQuestion);
                removeFromList(positionToBeRemoved);
            }
        });
        deleteDialogBox.show();
    }

    public void removeFromList(int position){
        results.remove(position);
        arrayAdapter.notifyDataSetChanged();
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
                    } while (c.moveToNext());
                }
            }
            //This can set how the list can be viewed for example making clickable list items.
            arrayAdapter = (new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, results));
            listView.setAdapter(arrayAdapter);
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }
    }

>>>>>>> refs/remotes/origin/master:MyApplication/app/src/main/java/edu/vcu/wes/myapplication/AllQuizzes.java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_quiz, menu);
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
