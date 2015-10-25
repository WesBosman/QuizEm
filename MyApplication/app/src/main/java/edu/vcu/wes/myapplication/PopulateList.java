package edu.vcu.wes.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**This is a VERY important helper method for creating list views for either flashcards or quizzes.
 * Created by Wes on 10/24/2015.
 */
public class PopulateList {
    public PopulateList(String list, Context ctx, ListView lv, String col){
        switch(list){
            case "quiz":
                populateQuizList(ctx, lv, col);
                break;
            case "flash":
                populateFlashList(ctx, lv, col);
                break;
        }
    }

    private void populateQuizList(Context context, ListView listView, String columns) {
        ArrayList<String> titleArray = new ArrayList<>();
        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String title = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_TITLE));
                        String question = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_QUESTION));
                        String answerOne = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_ANSWER1));
                        String answerTwo = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_ANSWER2));
                        String answerThree = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_ANSWER3));
                        String answerCorrect = c.getString(c.getColumnIndex(QuizTable.TableInfo.QUIZ_CORRECT_ANSWER));

                        switch(columns){
                            case "title":
                                titleArray.add(title);
                                break;
                            case "questions":
                                titleArray.add(question);
                                break;
                            case "answers":
                                titleArray.add(answerOne);
                                titleArray.add(answerTwo);
                                titleArray.add(answerThree);
                                titleArray.add(answerCorrect);
                                break;
                        }
                    } while (c.moveToNext());
                }
            }
            //This can set how the list can be viewed for example making clickable list items.
            // arrayAdapter = (new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, results));
            CustomArrayAdapter arrayAdapter = (new CustomArrayAdapter(context, titleArray));
            listView.setAdapter(arrayAdapter);

            //Close the database function close cursor also.
            c.close();
            df.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }catch(NullPointerException ne){
            Log.d("AllQuizzes: ", "Nullpointer thrown from closing cursor.");
        }

    }

    private void populateFlashList(Context context, ListView listView, String columns){
        ArrayList<String> titleArray = new ArrayList<>();
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
                                titleArray.add(flashTitle);
                                break;
                            case "questions":
                                titleArray.add(flashQuestion);
                                break;
                            case "answers":
                                titleArray.add(flashAnswer);
                                break;
                        }
                    } while (c.moveToNext());
                }
            }
            //This can set how the list can be viewed for example making clickable list items.
            // arrayAdapter = (new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, results));
            CustomArrayAdapter arrayAdapter = (new CustomArrayAdapter(context, titleArray));
            listView.setAdapter(arrayAdapter);

            //Close the database function close cursor also.
            c.close();
            df.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }catch(NullPointerException ne){
            Log.d("AllQuizzes: ", "Nullpointer thrown from closing cursor.");
        }
    }
}

