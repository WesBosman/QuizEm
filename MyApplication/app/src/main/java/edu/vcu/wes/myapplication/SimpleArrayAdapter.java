package edu.vcu.wes.myapplication;
/**
 * Created by Wes on 11/20/2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This Is The Old Code incase we still need part of it ............................................
 */

public class SimpleArrayAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> question = new ArrayList<>();
    private ArrayList<String> ans = new ArrayList<>();
    private Context context;
    private static LayoutInflater inflater = null;

    public SimpleArrayAdapter(Context context, ArrayList<String> list, ArrayList<String> questions, ArrayList<String> answers){
        this.list = list;
        this.question = questions;
        this.ans = answers;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Return the size of the Array List.
    @Override
    public int getCount() {
        return list.size();
    }

    //Get a specific position in the Array List.
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    //Return ZERO for now unless there is a list item id we assign later.
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * This method gets called when the minus image button in the list is clicked.
     * It then gets the position of the item in the list view.
     * If the user clicks yes, it deletes the item from the list amd database.
     */
    public void delete(int position){
        final String newQuestion = question.get(position);
        final DatabaseFunctions dataFunctions = new DatabaseFunctions(context);
        Log.d("SimpleArrayAdapter: ", newQuestion);
        AlertDialog.Builder deleteDialogBox = new AlertDialog.Builder(context);
        deleteDialogBox.setTitle("Delete?");
        deleteDialogBox.setMessage("Are you sure you want to delete this?");
        final int positionToBeRemoved = position;
        deleteDialogBox.setPositiveButton("No", null);
        deleteDialogBox.setNegativeButton("Yes", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //If context = AllQuiz then delete question
                if(context instanceof AllQuizzes) {
                    dataFunctions.deleteByQuizQuestion(newQuestion);
                    list.remove(positionToBeRemoved);
                    notifyDataSetChanged();
                }

                //If context != AllQuizzes then delete flashcard
                else{
                    dataFunctions.deleteByFlashQuestion(newQuestion);
                    list.remove(positionToBeRemoved);
                    notifyDataSetChanged();
                }

            }
        });
        deleteDialogBox.show();
        dataFunctions.close();
    }

    /**
     * Initialize the view with the custom created list item that has a delete button in it.
     * When the delete button is clicked call the delete (alert dialog box function).
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null) {
            view = inflater.inflate(R.layout.simple_list_items, null);
        }
        TextView textInList = (TextView) view.findViewById(R.id.list_item_title);
        textInList.setText(list.get(position));

        TextView questionInList = (TextView) view.findViewById(R.id.list_item_question);
        questionInList.setText(question.get(position));

        TextView answerOne = (TextView) view.findViewById(R.id.list_item_answer1);
        answerOne.setText(ans.get(position));

        ImageButton deleteListItem = (ImageButton)view.findViewById(R.id.delete_btn);
        deleteListItem.setTag(position);

        deleteListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SimpleArrayAdapter:", "The delete button has been clicked in the list view.");
                int pos = (int) v.getTag();
                delete(pos);
            }
        });
        return view;
    }
}