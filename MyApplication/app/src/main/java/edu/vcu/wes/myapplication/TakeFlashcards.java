package edu.vcu.wes.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TakeFlashcards extends AppCompatActivity {
    TextView flashCardQuestion;
    TextView flashCardAnswer;
    Context context = this;
    ImageButton flashNext;
    Button getAnswer;
    private int flag, count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_flashcards);
        flashCardQuestion = (TextView) findViewById(R.id.flashQuestionText);
        flashCardAnswer = (TextView) findViewById(R.id.flashCardAnswerText);
        flashNext = (ImageButton) findViewById(R.id.flashNextButton);
        getAnswer = (Button) findViewById(R.id.getAnswerButton);
        final Intent flashResults= new Intent (this, FlashResultsActivity.class);
        setQuestions();


        flashNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> questions = populate("questions");

                if (flag < questions.size()) {
                    setQuestions();
                    flag++;
                }
                else{
                    //Start an activity that lets them review flashcards or go back to main menu.
                    startActivity(flashResults);
                }

            }
        });

        getAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnsers();;
            }
        });

    }

    private void setQuestions(){
        ArrayList<String> flashQ = populate("questions");

        try {
            flashCardQuestion.setText(flashQ.get(flag));
        }
        catch(IndexOutOfBoundsException ie){
            ie.printStackTrace();
        }
    }

    private void setAnsers(){
        ArrayList<String> flashA = populate("answers");
        try{
            flashCardAnswer.setText(flashA.get(count));
            count++;
        }
        catch(IndexOutOfBoundsException ie){
            ie.printStackTrace();
        }
    }

    public ArrayList<String> populate(String columns){
            ArrayList<String> fromDatabase = new ArrayList<>();
            try {
                FlashDatabaseFunctions df = new FlashDatabaseFunctions(context);
                Cursor c = df.getFromDatabase(df);
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            String flashTitle = c.getString(c.getColumnIndex(QuizTable.TableInfo.FLASH_TITLE));
                            String flashQuestion = c.getString(c.getColumnIndex(QuizTable.TableInfo.FLASH_QUESTION));
                            String flashAnswer = c.getString(c.getColumnIndex(QuizTable.TableInfo.FLASH_ANSWER));
                            switch(columns){
                                case "title":
                                    fromDatabase.add(flashTitle);
                                    break;
                                case "questions":
                                    fromDatabase.add(flashQuestion);
                                    break;
                                case "answers":
                                    fromDatabase.add(flashAnswer);
                                    break;
                            }
                        } while (c.moveToNext());
                    }
                }
                c.close();
                df.close();
            } catch (SQLiteException se) {
                Log.e(getClass().getSimpleName(), "Could not create or open the database");
            }catch(NullPointerException ne){
                Log.d("TakeQuiz: ", "Nullpointer thrown.");
            }
            return fromDatabase;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_flashcards, menu);
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
