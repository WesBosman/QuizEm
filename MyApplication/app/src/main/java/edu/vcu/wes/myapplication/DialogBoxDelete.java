package edu.vcu.wes.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Wes on 9/29/2015.
 */
public class DialogBoxDelete extends Dialog implements android.view.View.OnClickListener {
    private Activity delete;
    private Dialog dialog;
    public Button yes, no;

    public DialogBoxDelete(Activity activity){
        super(activity);
        this.delete = activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_dialog);
        yes = (Button) findViewById(R.id.yes_button);
        no = (Button) findViewById(R.id.no_button);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_button:

                delete.finish();
                break;

            case R.id.no_button:
                dismiss();
                break;

            default:
                break;
        }
        dismiss();
        }

    }

