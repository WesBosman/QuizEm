package edu.vcu.wes.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Wes on 11/22/2015.
 * This class should test that we can empty the database and then put information into the database.
 */
 @RunWith(AndroidJUnit4.class)
public class testDatabaseFunctionsInsert extends InstrumentationTestCase{

    private static DatabaseFunctions testDF;
    private static RenamingDelegatingContext context;
    private static SQLiteDatabase db;
    private static Cursor cursor;

    public static final String testTitle = "This is a test title";
    public static final String testQuestion = "This is a test question";
    public static final String testAns1 = "This is the first answer";
    public static final String testAns2 = "This is the second answer";
    public static final String testAns3 = "This is the third answer";
    public static final String testAns4 = "This is the fourth answer";

    @Override
    public void setUp()throws Exception{
        super.setUp();
        context = new RenamingDelegatingContext(getInstrumentation().getContext(), "_test");
        testDF = new DatabaseFunctions(context);
        assertNotNull("TestDatabaseFunctions: ", testDF);
        db = testDF.getWritableDatabase();
        assertNotNull("Assert db not null ", db);
        testDF.onCreate(db);
        //Make sure the database functions populate the database.
        assertTrue(testDF.isEmpty());
        //Test that we can insert items into the database.
        testDF.insertIntoDatabase(testDF, testTitle, testQuestion, testAns1, testAns2, testAns3, testAns4);
        cursor =  testDF.getFromDatabase(testDF);
    }

    @Test
    public void testInsert(){
        //Use a cursor to access the database.
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String title = cursor.getString(cursor.getColumnIndex(testDF.QUIZ_TITLE));
                    String question = cursor.getString(cursor.getColumnIndex(testDF.QUIZ_QUESTION));
                    String answer = cursor.getString(cursor.getColumnIndex(testDF.QUIZ_ANSWER1));
                    String answer1 = cursor.getString(cursor.getColumnIndex(testDF.QUIZ_ANSWER2));
                    String answer2 = cursor.getString(cursor.getColumnIndex(testDF.QUIZ_ANSWER3));
                    String answer3 = cursor.getString(cursor.getColumnIndex(testDF.QUIZ_CORRECT_ANSWER));

                    assertTrue(title.equals(testTitle));
                    assertTrue(question.equals(testQuestion));
                    assertTrue(answer.equals(testAns1));
                    assertTrue(answer1.equals(testAns2));
                    assertTrue(answer2.equals(testAns3));
                    assertTrue(answer3.equals(testAns4));

                }while(cursor.moveToNext());
            }
        }
    }

    @Override
    public void tearDown()throws Exception{
        try {
            testDF.close();
            super.tearDown();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
