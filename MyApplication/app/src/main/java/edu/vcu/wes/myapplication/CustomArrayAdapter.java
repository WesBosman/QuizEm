package edu.vcu.wes.myapplication;

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
 * This class creates a custom array adapter that uses a custom list item xml file to create
 * A text view next to an image button
 * The text view holds the name of the question/quiz entered.
 * The image button holds an X which is used to delete the question/quiz from the database.
 * Created by Wes on 10/19/2015.
 */
public class CustomArrayAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<>();
    private Context context;
    private static LayoutInflater inflater = null;

    public CustomArrayAdapter(Context context, ArrayList<String> list){
        this.list = list;
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
            final String newQuestion = list.get(position);
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
                    if(context instanceof AllQuizzes) {
                        dataFunctions.deleteFromDatabase(newQuestion);
                        list.remove(positionToBeRemoved);
                        notifyDataSetChanged();
                    }

                    //If context != AllQuizzes then delete flashcard
                    else{
                        dataFunctions.deleteFromFlashDatabase(newQuestion);
                        list.remove(positionToBeRemoved);
                        notifyDataSetChanged();
                    }

                }
            });
            deleteDialogBox.show();
    }

    /**
     * Initialize the view with the custom created list item that has a delete button in it.
     * When the delete button is clicked call the delete (alert dialog box function).
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null) {
            view = inflater.inflate(R.layout.list_items, null);
        }
        TextView textInList = (TextView) view.findViewById(R.id.list_item_string);
        textInList.setText(list.get(position));

        ImageButton deleteListItem = (ImageButton)view.findViewById(R.id.delete_btn);
        deleteListItem.setTag(position);

        deleteListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CustomArrayAdapter:", "The delete button has been clicked in the list view.");
                int pos = (int) v.getTag();
                delete(pos);
            }
        });

        return view;
    }
}
