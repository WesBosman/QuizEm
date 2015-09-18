package edu.vcu.wes.myapplication;

import android.provider.BaseColumns;

/**
 * Created by Wes on 9/16/2015.
 */
public class QuizTable {

    //Empty default constructer so this class does not get instantiated
    public QuizTable(){
    }

    public static abstract class TableInfo implements BaseColumns{
        //Column names.
        public static final String QUIZ_TITLE = "quiz_title";
        public static final String QUIZ_QUESTION = "quiz_question";
        public static final String QUIZ_ANSWER = "quiz_answer";
        public static final String DATABASE_NAME = "quiz_database";
        public static final String TABLE_NAME = "quiz_collection";
    }
}
