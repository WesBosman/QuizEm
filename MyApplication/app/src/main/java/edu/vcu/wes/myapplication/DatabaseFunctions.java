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
            + " TEXT," + QuizTable.TableInfo.QUIZ_ANSWER1 + " TEXT," + QuizTable.TableInfo.QUIZ_ANSWER2
            + " TEXT," + QuizTable.TableInfo.QUIZ_ANSWER3 + " TEXT," + QuizTable.TableInfo.QUIZ_CORRECT_ANSWER
            + " TEXT);";

    public DatabaseFunctions(Context context) {
        super(context, QuizTable.TableInfo.DATABASE_NAME, null, DATA_BASE_VERSION);
        Log.d("DataBaseFunctions: ", "Our Database Was Successfully Created.");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createQuery);
        Log.d("DatabaseFunctions: ", "Table Was Successfully Created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int a0, int a1) {
        database.execSQL("DROP IF TABLE EXISTS " + QuizTable.TableInfo.TABLE_NAME);
        onCreate(database);
    }

    public boolean isEmpty(){
        boolean empty;
        SQLiteDatabase db = getReadableDatabase();
        String count = "SELECT count(*) FROM " + QuizTable.TableInfo.TABLE_NAME;
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

    //Puts info into the database.
    public void insertIntoDatabase(DatabaseFunctions df, String title, String question, String answer1, String answer2, String answer3, String correct) {
        SQLiteDatabase database = df.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(QuizTable.TableInfo.QUIZ_TITLE, title);
        content.put(QuizTable.TableInfo.QUIZ_QUESTION, question);
        content.put(QuizTable.TableInfo.QUIZ_ANSWER1, answer1);
        content.put(QuizTable.TableInfo.QUIZ_ANSWER2, answer2);
        content.put(QuizTable.TableInfo.QUIZ_ANSWER3, answer3);
        content.put(QuizTable.TableInfo.QUIZ_CORRECT_ANSWER, correct);

        long databaseId = database.insert(QuizTable.TableInfo.TABLE_NAME, null, content);
        Log.d("DatabaseFunctions: ", "A Row Has Been Inserted");
    }

    //Gets information from the database
    public Cursor getFromDatabase(DatabaseFunctions df) {
        SQLiteDatabase database = df.getReadableDatabase();
        String[] columns = {QuizTable.TableInfo.QUIZ_TITLE, QuizTable.TableInfo.QUIZ_QUESTION,
                QuizTable.TableInfo.QUIZ_ANSWER1 , QuizTable.TableInfo.QUIZ_ANSWER2, QuizTable.TableInfo.QUIZ_ANSWER3,
                QuizTable.TableInfo.QUIZ_CORRECT_ANSWER};
        Cursor cursor = database.query(QuizTable.TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    //Delete from database
    //This works for deleting an entry from the database permantly.
    public void deleteFromDatabase(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(QuizTable.TableInfo.TABLE_NAME, "quiz_title = ?", new String[] { title });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }
}
