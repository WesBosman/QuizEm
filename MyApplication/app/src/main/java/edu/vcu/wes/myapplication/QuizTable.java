package edu.vcu.wes.myapplication;

import android.provider.BaseColumns;

/**
 * Created by Wes on 9/16/2015.
 */
public class QuizTable {

    //Empty default constructor so this class does not get instantiated.
    public QuizTable(){
    }

    public static abstract class TableInfo implements BaseColumns{
        //Database SQLite strings
        public static final String QUIZ_TITLE = "quiz_title";
        public static final String QUIZ_QUESTION = "quiz_question";
        public static final String QUIZ_ANSWER1 = "quiz_answer_one";
        public static final String QUIZ_ANSWER2 = "quiz_answer_two";
        public static final String QUIZ_ANSWER3 = "quiz_answer_three";
        public static final String QUIZ_CORRECT_ANSWER = "quiz_answer_correct";
        public static final String DATABASE_NAME = "OurQuestionDatabse.db";
        public static final String TABLE_NAME = "quiz_collection";

        //FlashCard Database Strings.
        public static final String FLASH_TITLE = "flash_title";
        public static final String FLASH_QUESTION = "flash_question";
        public static final String FLASH_ANSWER = "flash_answer";
        public static final String FLASH_DATABASE = "OurFlashDatabase.db";
        public static final String FLASH_TABLE = "flash_collection";
    }
}
