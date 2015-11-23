package edu.vcu.wes.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.ProviderTestCase2;
import android.test.RenamingDelegatingContext;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Wes on 11/22/2015.
 * This class should test that we can empty the database and then put information into the database.
 *
 */
@RunWith(AndroidJUnit4.class)
public class testDatabaseFunctionsIsEmpty extends AndroidTestCase {
    private DatabaseFunctions testDatabaseFunctions;
    private static final String DATABASE_TEST = "test_";
    private static final int DATA_BASE_VERSION = 1;

    public static final String testTitle = "This is a test title";
    public static final String testQuestion = "This is a test question";
    public static final String testAns1 = "This is the first answer";
    public static final String testAns2 = "This is the second answer";
    public static final String testAns3 = "This is the third answer";
    public static final String testAns4 = "This is the fourth answer";

    //Set up the database first. Open it.
    @Override
    public void setUp() throws Exception{
        try {
            super.setUp();
            RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), DATABASE_TEST);
            testDatabaseFunctions = new DatabaseFunctions(context);
            SQLiteDatabase db = testDatabaseFunctions.getWritableDatabase();
            testDatabaseFunctions.onCreate(db);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testDatabaseFunctionsIsEmpty(){


    }

    @Override
    public void tearDown()throws Exception{

        try {
            testDatabaseFunctions.close();
            super.tearDown();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
