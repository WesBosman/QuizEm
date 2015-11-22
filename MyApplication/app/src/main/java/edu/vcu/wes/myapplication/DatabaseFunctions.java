package edu.vcu.wes.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Wes on 9/16/2015.
 * This Database consists of Two Tables. It was originally two different databases which was horrible inefficient.
 * I decided after some online research that the same thing could be accomplished using one database
 * With two tables inside the database.
 * DataBase SQLite tutorial on youtube by Prabeesh R. K.
 */
public class DatabaseFunctions extends SQLiteOpenHelper {

    //Quiz Table Strings
    public static final String QUIZ_TITLE = "quiz_title";
    public static final String QUIZ_ID = "_id";
    public static final String QUIZ_QUESTION = "quiz_question";
    public static final String QUIZ_ANSWER1 = "quiz_answer_one";
    public static final String QUIZ_ANSWER2 = "quiz_answer_two";
    public static final String QUIZ_ANSWER3 = "quiz_answer_three";
    public static final String QUIZ_CORRECT_ANSWER = "quiz_answer_correct";
    public static final String DATABASE_NAME = "OurDatabase.db";
    public static final String QUIZ_TABLE = "quiz_collection";

    //FlashCard Table Strings.
    public static final String FLASH_TITLE = "flash_title";
    public static final String FLASH_ID = "_id";
    public static final String FLASH_QUESTION = "flash_question";
    public static final String FLASH_ANSWER = "flash_answer";
    public static final String FLASH_TABLE = "flash_collection";

    //Quiz table creation
    public static final int DATA_BASE_VERSION = 1;
    public String createQuery = "CREATE TABLE " + QUIZ_TABLE
            + "(" + QUIZ_ID + " integer primary key autoincrement, " + QUIZ_TITLE + " TEXT," + QUIZ_QUESTION
            + " TEXT," + QUIZ_ANSWER1 + " TEXT," + QUIZ_ANSWER2
            + " TEXT," + QUIZ_ANSWER3 + " TEXT," + QUIZ_CORRECT_ANSWER
            + " TEXT);";

    //Flash table creation
    public String createFlashQuery = "CREATE TABLE " + FLASH_TABLE
            + "(" + FLASH_ID + " integer primary key autoincrement, " + FLASH_TITLE + " TEXT," + FLASH_QUESTION
            + " TEXT," + FLASH_ANSWER + " TEXT);";

    //Create or Open Database.
    public DatabaseFunctions(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
        Log.d("DataBaseFunctions: ", "Our Database Was Successfully Created.");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createQuery);
        database.execSQL(createFlashQuery);
        Log.d("DatabaseFunctions: ", "Table Was Successfully Created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int a0, int a1) {
        database.execSQL("DROP IF TABLE EXISTS " + QUIZ_TABLE);
        database.execSQL("DROP IF TABLE EXISTS " + FLASH_TABLE);
        onCreate(database);
    }

    //------------------------------ Flash Table Functionality -------------------------------------

    //Boolean returned to tell if Flash Table is Empty.
    public boolean isFlashEmpty(){
        boolean empty;
        SQLiteDatabase db = getReadableDatabase();
        String count = "SELECT count(*) FROM " + FLASH_TABLE;
        Cursor myCursor = db.rawQuery(count, null);
        myCursor.moveToFirst();
        int counter = myCursor.getInt(0);
        if(counter >= 1){
            empty = false;
        }
        else{
            empty = true;
        }
        myCursor.close();
        db.close();
        return empty;
    }

    //Puts FlashCard into the Flash Table in Database.
    public void insertIntoFlashDatabase(DatabaseFunctions df, String title, String question, String answer1) {
        SQLiteDatabase database = df.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(FLASH_TITLE, title);
        content.put(FLASH_QUESTION, question);
        content.put(FLASH_ANSWER, answer1);
        long databaseId = database.insert(FLASH_TABLE, null, content);
        Log.d("FlashTable: ", "A Row Has Been Inserted");
    }

    //Gets FlashCard from the Flash Table in Database
    public Cursor getFromFlashDatabase(DatabaseFunctions df) {
        SQLiteDatabase database = df.getReadableDatabase();
        String[] columns = {FLASH_TITLE, FLASH_QUESTION, FLASH_ANSWER};
        Cursor cursor = database.query(FLASH_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    //Delete a Flash Card from the Flash Table in the Database. Based on title.
    public void deleteByFlashTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(FLASH_TABLE, "flash_title = ?", new String[] { title });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }

    public void deleteByFlashQuestion(String question) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(FLASH_TABLE, "flash_question = ?", new String[] { question });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }

    //This function takes in a context and a string that is used to choose what column of information
    //From the database will be displayed in the study flashcards activity.
    public ArrayList<String> populateFlashCards(Context context, String columns){
        ArrayList<String> fromDatabase = new ArrayList<>();
        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromFlashDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String flashTitle = c.getString(c.getColumnIndex(FLASH_TITLE));
                        String flashQuestion = c.getString(c.getColumnIndex(FLASH_QUESTION));
                        String flashAnswer = c.getString(c.getColumnIndex(FLASH_ANSWER));

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

    //Gets information from the database and displays it in a list view using a custom list adapter.
    //This method populates the Flash Cards List.
    public void populateFlashList(Context context, ExpandableListView listView, String sort){
        ArrayList<String> titleArray = new ArrayList<>();
        HashMap<String, ArrayList<String>> childItems = new HashMap<>();
        ArrayList<String> children;
        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromFlashDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String flashTitle = c.getString(c.getColumnIndex(FLASH_TITLE));
                        String flashQuestion = c.getString(c.getColumnIndex(FLASH_QUESTION));
                        String flashAnswer = c.getString(c.getColumnIndex(FLASH_ANSWER));
                        children = new ArrayList<>();
                        //If the key is the same then the questions and answers are grouped under the same title.
                        if(childItems.containsKey(flashTitle)){
                            children = childItems.get(flashTitle);
                            children.add(flashQuestion);
                            children.add(flashAnswer);
                        }
                        //Other wise they get set as they normally would.
                        else {
                            titleArray.add(flashTitle);
                            children.add(flashQuestion);
                            children.add(flashAnswer);
                            childItems.put(flashTitle, children);
                        }

                    } while (c.moveToNext());
                }
            }
            if(sort.equals("sort")){
                Collections.sort(titleArray, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });
            }
            //This can set how the list can be viewed for example making clickable list items.
            CustomArrayAdapter arrayAdapter = (new CustomArrayAdapter(context, titleArray, childItems));
            listView.setAdapter(arrayAdapter);

            //Close the database function close cursor also.
            c.close();
            df.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }catch(NullPointerException ne){
            Log.d("AllFlash: ", "Null pointer thrown from closing cursor.");
        }
    }

    public void populateFlashListAll(Context context, ListView listView) {
        ArrayList<String> flashTitle = new ArrayList<>();
        ArrayList<String> flashQuestion = new ArrayList<>();
        ArrayList<String> flashAnswer = new ArrayList<>();

        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromFlashDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String title = c.getString(c.getColumnIndex(FLASH_TITLE));
                        String question = c.getString(c.getColumnIndex(FLASH_QUESTION));
                        String answerOne = c.getString(c.getColumnIndex(FLASH_ANSWER));

                        flashTitle.add(title);
                        flashQuestion.add(question);
                        flashAnswer.add(answerOne);

                    } while (c.moveToNext());
                }
            }
            //This can set how the list can be viewed for example making clickable list items.
            SimpleArrayAdapter arrayAdapter = (new SimpleArrayAdapter(context, flashTitle, flashQuestion, flashAnswer));
            listView.setAdapter(arrayAdapter);

            //Close the database function close cursor also.
            c.close();
            df.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }catch(NullPointerException ne){
            Log.d("FlashListAll: ", "Null pointer thrown from closing cursor.");
        }

    }

    //------------------------------  Quiz Table Functionality  ------------------------------------

    //Returns a boolean if Quiz Table is empty.
    public boolean isEmpty(){
        boolean empty;
        SQLiteDatabase db = getReadableDatabase();
        String count = "SELECT count(*) FROM " + QUIZ_TABLE;
        Cursor myCursor = db.rawQuery(count, null);
        myCursor.moveToFirst();
        int counter = myCursor.getInt(0);
        if(counter >= 1){
            empty = false;
        }
        else{
            empty = true;
        }
        myCursor.close();
        db.close();
        return empty;
    }

    //Puts Quiz Question into the database.
    public void insertIntoDatabase(DatabaseFunctions df, String title, String question, String answer1, String answer2, String answer3, String correct) {
        SQLiteDatabase database = df.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(QUIZ_TITLE, title);
        content.put(QUIZ_QUESTION, question);
        content.put(QUIZ_ANSWER1, answer1);
        content.put(QUIZ_ANSWER2, answer2);
        content.put(QUIZ_ANSWER3, answer3);
        content.put(QUIZ_CORRECT_ANSWER, correct);

        long databaseId = database.insert(QUIZ_TABLE, null, content);
        Log.d("DatabaseFunctions: ", "A Row Has Been Inserted");
    }

    //Gets Quiz information from the database
    public Cursor getFromDatabase(DatabaseFunctions df) {
        SQLiteDatabase database = df.getReadableDatabase();
        String[] columns = {QUIZ_TITLE, QUIZ_QUESTION,
                    QUIZ_ANSWER1, QUIZ_ANSWER2, QUIZ_ANSWER3,
                    QUIZ_CORRECT_ANSWER};

        Cursor cursor = database.query(QUIZ_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    //Delete Quiz Question from Database. Based on title.
    public void deleteByQuizTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

            try {
                db.delete(QUIZ_TABLE, "quiz_title = ?", new String[]{title});
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }

    }

    //Delete quiz question based on the question
    public void deleteByQuizQuestion(String question) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(QUIZ_TABLE, "quiz_question = ?", new String[]{question});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

    //This method takes in a context and a string and depending on the string will return
    //An array list of values used in the take quiz activity.
    public ArrayList<String> populateQuiz(Context context, String columns){
        ArrayList<String> fromDatabase = new ArrayList<>();
        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String title = c.getString(c.getColumnIndex(QUIZ_TITLE));
                        String question = c.getString(c.getColumnIndex(QUIZ_QUESTION));
                        String answerOne = c.getString(c.getColumnIndex(QUIZ_ANSWER1));
                        String answerTwo = c.getString(c.getColumnIndex(QUIZ_ANSWER2));
                        String answerThree = c.getString(c.getColumnIndex(QUIZ_ANSWER3));
                        String answerCorrect = c.getString(c.getColumnIndex(QUIZ_CORRECT_ANSWER));

                        switch(columns){
                            case "title":
                                fromDatabase.add(title);
                                break;
                            case "questions":
                                fromDatabase.add(question);
                                break;
                            case "answers":
                                fromDatabase.add(answerOne);
                                fromDatabase.add(answerTwo);
                                fromDatabase.add(answerThree);
                                fromDatabase.add(answerCorrect);
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
            Log.d("TakeQuiz: ", "Null pointer thrown.");
        }
        return fromDatabase;
    }

    //This populate Quiz List uses a custom adapter to display the items in a ListView.
    public void populateQuizList(Context context, ExpandableListView listView, String sort) {
        ArrayList<String> titleArray = new ArrayList<>();
        HashMap<String, ArrayList<String>> childItems = new HashMap<>();
        ArrayList<String> children;

        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        children = new ArrayList<>();
                        String title = c.getString(c.getColumnIndex(QUIZ_TITLE));
                        String question = c.getString(c.getColumnIndex(QUIZ_QUESTION));
                        String answerOne = c.getString(c.getColumnIndex(QUIZ_ANSWER1));
                        String answerTwo = c.getString(c.getColumnIndex(QUIZ_ANSWER2));
                        String answerThree = c.getString(c.getColumnIndex(QUIZ_ANSWER3));
                        String answerCorrect = c.getString(c.getColumnIndex(QUIZ_CORRECT_ANSWER));
                        //If a two titles(keys) are the same. The key will go to all values.
                        if(childItems.containsKey(title)){
                            children = childItems.get(title);
                            children.add(question);
                            children.add(answerOne);
                            children.add(answerTwo);
                            children.add(answerThree);
                            children.add(answerCorrect);
                        }
                        //Otherwise they get set as they normally would.
                        else {
                            titleArray.add(title);
                            children.add(question);
                            children.add(answerOne);
                            children.add(answerTwo);
                            children.add(answerThree);
                            children.add(answerCorrect);
                            childItems.put(title, children);
                        }

                    } while (c.moveToNext());
                }
            }
            //If "sort" is passed in then sort the collection by title.
            if(sort.equals("sort")){
                Collections.sort(titleArray, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });
            }
            //This can set how the list can be viewed for example making clickable list items.
            CustomArrayAdapter arrayAdapter = (new CustomArrayAdapter(context, titleArray, childItems));
            listView.setAdapter(arrayAdapter);

            //Close the database function close cursor also.
            c.close();
            df.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }catch(NullPointerException ne){
            Log.d("AllQuizzes: ", "Null pointer thrown from closing cursor.");
        }
    }

    //Old Code could be used to populate list with individual elements because can not have two keys
    public void populateQuizListAll(Context context, ListView listView) {
        ArrayList<String> quizTitle = new ArrayList<>();
        ArrayList<String> quizQuestion = new ArrayList<>();
        ArrayList<String> quizAnswer = new ArrayList<>();

        try {
            DatabaseFunctions df = new DatabaseFunctions(context);
            Cursor c = df.getFromDatabase(df);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String title = c.getString(c.getColumnIndex(QUIZ_TITLE));
                        String question = c.getString(c.getColumnIndex(QUIZ_QUESTION));
                        String answerOne = c.getString(c.getColumnIndex(QUIZ_ANSWER1));
                        String answerTwo = c.getString(c.getColumnIndex(QUIZ_ANSWER2));
                        String answerThree = c.getString(c.getColumnIndex(QUIZ_ANSWER3));
                        String answerCorrect = c.getString(c.getColumnIndex(QUIZ_CORRECT_ANSWER));

                        quizTitle.add(title);
                        quizQuestion.add(question);
                        quizAnswer.add(answerOne + "\n" + answerTwo + "\n" + answerThree + "\n" + answerCorrect);

                    } while (c.moveToNext());
                }
            }
            //This can set how the list can be viewed for example making clickable list items.
            // arrayAdapter = (new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, results));
            SimpleArrayAdapter arrayAdapter = (new SimpleArrayAdapter(context, quizTitle, quizQuestion, quizAnswer));
            listView.setAdapter(arrayAdapter);


            //Close the database function close cursor also.
            df.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(),
                    "Could not create or open the database");
        }catch(NullPointerException ne){
            Log.d("AllQuizzes: ", "Null pointer thrown from closing cursor.");
        }

    }

}
