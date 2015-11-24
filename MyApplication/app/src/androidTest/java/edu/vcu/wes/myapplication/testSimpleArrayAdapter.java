package edu.vcu.wes.myapplication;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Wes on 11/22/2015.
 * Unit Test
 * Testing simple array adapter class.
 */
public class testSimpleArrayAdapter extends AndroidTestCase{
    private SimpleArrayAdapter simpleAdapt;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();

    public testSimpleArrayAdapter(){

    }

    public void setUp() throws Exception{
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "simple_test");
        title.add("Math");
        title.add("History");
        questions.add("2 + 2");
        questions.add("The first president of the US was?");
        //Math answers.
        answers.add("2");
        answers.add("3");
        answers.add("1");
        answers.add("4");
        //History answers.
        answers.add("Franklin");
        answers.add("Delano");
        answers.add("roosevelt");
        answers.add("George Washington");
        simpleAdapt = new SimpleArrayAdapter(context, title, questions, answers);
    }

    public void testGetCount(){
        //Should return size of title array. which is 2
        //boolean size = title.size() == 2;
        assertEquals(2, simpleAdapt.getCount());
    }

    public void testGetItem(){
        //Should get the first itme in the list of titles.
        assertEquals(title.get(0), simpleAdapt.getItem(0));
    }

    public void testDelete(){
        //This should delete the first quiz item.
        //simpleAdapt.delete(0);
        //assertEquals(simpleAdapt.getCount(), 1);
    }

    public void testGetView(){
        View view = simpleAdapt.getView(0, null, null);
        TextView titleView = (TextView) view.findViewById(R.id.list_item_title);

        TextView questionView = (TextView) view.findViewById(R.id.list_item_question);

        TextView answerView = (TextView) view.findViewById(R.id.list_item_answer1);

        ImageButton deleteItem = (ImageButton)view.findViewById(R.id.delete_btn);


        assertNotNull("View is null ", view);
        assertNotNull("Title TextView is null ", titleView);
        assertNotNull("Question TextView is null " , questionView);
        assertNotNull("Answer TextView is null " , answerView);
        assertNotNull("ImageButton is null" , deleteItem);

        assertEquals("Titles don't match ", title.get(0), titleView.getText());
        assertEquals("Questions don't match ", questions.get(0), questionView.getText());
        assertEquals("Answers don't match ", answers.get(0), answerView.getText());
    }
}
