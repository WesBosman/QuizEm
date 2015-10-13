package edu.vcu.wes.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Wes on 9/16/2015.
 * DataBase SQLite tutorial on youtube by Prabeesh R. K.
 */
public class DatabaseFunctions extends SQLiteOpenHelper {
    public static final int DATA_BASE_VERSION = 1;
    public String createQuery = "CREATE TABLE " + QuizTable.TableInfo.TABLE_NAME
            + "(" + QuizTable.TableInfo.QUIZ_TITLE + " TEXT," + QuizTable.TableInfo.QUIZ_QUESTION
            + " TEXT," + QuizTable.TableInfo.QUIZ_ANSWER + " TEXT);";

    public DatabaseFunctions(Context context) {
        super(context, QuizTable.TableInfo.DATABASE_NAME, null, DATA_BASE_VERSION);
        Log.d("DataBase Creation...", "Our Database Was Successfully Created.");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createQuery);
        Log.d("Database Creation...", "Table Was Successfully Created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int a0, int a1) {
        database.execSQL("DROP IF TABLE EXISTS " + QuizTable.TableInfo.TABLE_NAME);
        onCreate(database);
    }

    //Puts info into the database.
    public void insertIntoDatabase(DatabaseFunctions df, String title, String question, String answer) {
        SQLiteDatabase database = df.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(QuizTable.TableInfo.QUIZ_TITLE, title);
        content.put(QuizTable.TableInfo.QUIZ_QUESTION, question);
        content.put(QuizTable.TableInfo.QUIZ_ANSWER, answer);
        long databaseId = database.insert(QuizTable.TableInfo.TABLE_NAME, null, content);
        Log.d("Database Creation...", "A Ros Has Been Inserted");
    }

    //Gets information from the database
    public Cursor getFromDatabase(DatabaseFunctions df) {
        SQLiteDatabase database = df.getReadableDatabase();
        String[] columns = {QuizTable.TableInfo.QUIZ_TITLE, QuizTable.TableInfo.QUIZ_QUESTION, QuizTable.TableInfo.QUIZ_ANSWER};
        Cursor cursor = database.query(QuizTable.TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    //Delete from database
    //Not sure if this works yet.
    //May need to print out a log message also to track what the database is doing.
    public void deleteFromDatabase(String question) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + QuizTable.TableInfo.TABLE_NAME + " WHERE " + QuizTable.TableInfo.QUIZ_QUESTION +
        "=\"" + question + "\";");
    }
}
