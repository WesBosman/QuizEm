package edu.vcu.wes.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class creates a custom array adapter that uses a custom list item xml file to create
 * A text view next to an image button
 * The text view holds the name of the question/quiz entered.
 * The item can now be clicked an expanded.
 * The image button holds an X which is used to delete the question/quiz from the database.
 * Created by Wes on 10/19/2015.
 */
public class CustomArrayAdapter extends BaseExpandableListAdapter {
    //For the titles of Quizzes
    private ArrayList<String> parentList = new ArrayList<>();
    //Hash map for the child items. Ex. Questions and Answers...
    private HashMap<String, ArrayList<String>> childItems = new HashMap<>();
    private Context context;

    public CustomArrayAdapter(Context context, ArrayList<String> list, HashMap<String, ArrayList<String>> hashMap){
        this.parentList = list;
        this.context = context;
        this.childItems = hashMap;
    }

    /**
     * Child Methods. ------------------------------------------------------------------------------
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childItems.get(this.parentList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater childInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = childInflater.inflate(R.layout.child_list_items, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.child_list_item1);
        txtListChild.setText(childText);

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childItems.get(this.parentList.get(groupPosition)).size();

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * Group Methods -------------------------------------------------------------------------------
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this.parentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.parentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String parentListItem = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater parentInflate = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = parentInflate.inflate(R.layout.list_items, null);
        }
        //Set the text for the title of the question
        TextView parentString = (TextView) convertView.findViewById(R.id.list_item_string);
        parentString.setText(parentListItem);
        //Set the image button
        ImageButton deleteListItem = (ImageButton)convertView.findViewById(R.id.delete_btn);
        deleteListItem.setTag(groupPosition);

        deleteListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CustomArrayAdapter:", "The delete button has been clicked in the list view.");
                int pos = (int) v.getTag();
                delete(pos);
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getGroupType(int groupPosition) {
        return super.getGroupType(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return super.getGroupTypeCount();
    }

    /**
     * This method gets called when the minus image button in the list is clicked.
     * It then gets the position of the item in the list view.
     * If the user clicks yes, it deletes the item from the list amd database.
     */
    public void delete(int position){
        final String newQuestion = parentList.get(position);
        final DatabaseFunctions dataFunctions = new DatabaseFunctions(context);
        Log.d("CustomArrayAdapter: ", newQuestion);
        AlertDialog.Builder deleteDialogBox = new AlertDialog.Builder(context);
        deleteDialogBox.setTitle("Delete?");
        deleteDialogBox.setMessage("Are you sure you want to delete this?");
        final int positionToBeRemoved = position;
        deleteDialogBox.setPositiveButton("No", null);
        deleteDialogBox.setNegativeButton("Yes", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //If context = AllQuiz then delete question
                if (context instanceof AllQuizzes) {
                    dataFunctions.deleteByQuizTitle(newQuestion);
                    parentList.remove(positionToBeRemoved);
                    notifyDataSetChanged();
                }

                //If context != AllQuizzes then delete flashcard
                else {
                    dataFunctions.deleteByFlashTitle(newQuestion);
                    parentList.remove(positionToBeRemoved);
                    notifyDataSetChanged();
                }

            }
        });
        deleteDialogBox.show();
        dataFunctions.close();
    }
}