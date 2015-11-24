package edu.vcu.wes.myapplication;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wes on 11/22/2015.
 * Unit Test
 * Tests the functionality of the custom array adapter.
 */
public class testCustomArrayAdapter extends AndroidTestCase{
    private CustomArrayAdapter custom;
    private HashMap<String, ArrayList<String>> hash = new HashMap<>();
    private ArrayList<String> listTitle;
    private ArrayList<String> children;

    public void setUp() throws Exception{
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "custom_test");
        //Set hash map and array list.
        listTitle = new ArrayList<>();
        listTitle.add("Math");
        //Not sure the titles are correct.
        children = new ArrayList<>();
        children.add("2 + 2");
        children.add("1");
        children.add("2");
        children.add("3");
        children.add("4");
        //Set the hashmap.
        hash.put(listTitle.get(0), children);
        custom = new CustomArrayAdapter(context, listTitle, hash);
    }

    public void testGetChildView(){
        View view  = custom.getChildView(0, 0, false, null, null);
        TextView  textView = (TextView) view.findViewById(R.id.child_list_item1);

        assertNotNull("View is null ", view);
        assertNotNull("TextView is not null", textView);
        //This should be equal to our first question. Should equal Math
        assertEquals("Child items match", children.get(0) , textView.getText());
        addMoreDataAndTest();
    }

    public void addMoreDataAndTest(){
        //Add another expandable list item and test.
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "custom_test_two");
        listTitle.add("History");
        children = new ArrayList<>();
        children.add("Who was the first president of the US?");
        children.add("Gene");
        children.add("Alexander");
        children.add("Mary");
        children.add("George");
        //Set the hash map.
        hash.put(listTitle.get(1), children);
        custom = new CustomArrayAdapter(context, listTitle, hash);

    }

    public void testGetChildViewAgain(){
        View view  = custom.getChildView(0, 0, false, null, null);
        TextView  textView = (TextView) view.findViewById(R.id.child_list_item1);

        assertNotNull("View is null ", view);
        assertNotNull("TextView is not null", textView);
        //This should be equal to our first question. Should equal Math
        assertEquals("Child items match", children.get(0), textView.getText());
    }

    public void testGetGroupView(){
        View group = custom.getGroupView(0,false,null,null);
        TextView titleOfQuestion = (TextView) group.findViewById(R.id.list_item_string);
        ImageButton deleteButton = (ImageButton) group.findViewById(R.id.delete_btn);

        assertNotNull("View is null ", group);
        assertNotNull("TextView title is not null ", titleOfQuestion);
        assertNotNull("Delete Button ", deleteButton);

        assertEquals("Title matches ", listTitle.get(0), titleOfQuestion.getText());

    }
}
