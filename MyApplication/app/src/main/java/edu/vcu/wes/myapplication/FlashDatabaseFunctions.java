package edu.vcu.wes.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wes on 10/24/2015.
 */
public class FlashDatabaseFunctions extends SQLiteOpenHelper {
    public static final int FLASH_DATABASE_VERSION = 1;
    public String createFlashQuery = "CREATE TABLE " + QuizTable.TableInfo.FLASH_TABLE
            + "(" + QuizTable.TableInfo.FLASH_TITLE + " TEXT," + QuizTable.TableInfo.FLASH_QUESTION
            + " TEXT," + QuizTable.TableInfo.FLASH_ANSWER + " TEXT);";

    public FlashDatabaseFunctions(Context context) {
        super(context, QuizTable.TableInfo.FLASH_DATABASE , null, FLASH_DATABASE_VERSION);
        Log.d("FLASHCARDS_DataBase: ", "Our Database Was Successfully Created.");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createFlashQuery);
        Log.d("FLASHCARDS_Database: ", "Table Was Successfully Created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int a0, int a1) {
        database.execSQL("DROP IF TABLE EXISTS " + QuizTable.TableInfo.FLASH_TABLE);
        onCreate(database);
    }

    public boolean isEmpty(){
        boolean empty;
        SQLiteDatabase db = getReadableDatabase();
        String count = "SELECT count(*) FROM " + QuizTable.TableInfo.FLASH_TABLE;
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
    public void insertIntoDatabase(FlashDatabaseFunctions df, String title, String question, String answer1) {
        SQLiteDatabase database = df.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(QuizTable.TableInfo.FLASH_TITLE, title);
        content.put(QuizTable.TableInfo.FLASH_QUESTION, question);
        content.put(QuizTable.TableInfo.FLASH_ANSWER, answer1);

        long databaseId = database.insert(QuizTable.TableInfo.FLASH_TABLE, null, content);
        Log.d("DatabaseFunctions: ", "A Row Has Been Inserted");
    }

    //Gets information from the database
    public Cursor getFromDatabase(FlashDatabaseFunctions df) {
        SQLiteDatabase database = df.getReadableDatabase();
        String[] columns = {QuizTable.TableInfo.FLASH_TITLE, QuizTable.TableInfo.FLASH_QUESTION,
                QuizTable.TableInfo.FLASH_ANSWER};
        Cursor cursor = database.query(QuizTable.TableInfo.FLASH_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    //Delete from database
    //This works for deleting an entry from the database permantly.
    public void deleteFromDatabase(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(QuizTable.TableInfo.FLASH_TABLE, "flash_title = ?", new String[] { title });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }
}
