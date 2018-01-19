package com.tech.rotobash.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.rotobash.R;

import java.util.ArrayList;

public class PossibleCombinationInflator extends AppCompatActivity  {

    private String mFor;
    private ArrayList<String> contactNumbers;
    private LinearLayout llParent;
    private EditText etFirstValue;
    private ArrayList<String> mArrayList;
    private boolean isOnlyShowing = false;
    private Button btnDone;
    private ImageView imgFirstAdd;
    private LinearLayout llTopView;
    private Toolbar toolbar;
    private TextView tvToolbarWriteNoteTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_ranks_layout);

        initVariables();




/*
        if (mFor.equalsIgnoreCase("phone")) {
            etFirstValue.setHint(getString(R.string.phone));

        } else {
            etFirstValue.setHint(getString(R.string.socialAccounts));
            etFirstValue.setInputType(InputType.TYPE_CLASS_TEXT);
        }*/

        mArrayList.clear();
        if (contactNumbers != null) {
            for (int i = 0; i < contactNumbers.size(); i++) {
                addFieldsToList(contactNumbers.get(i));
            }
        }
    }

    private void initVariables() {

        mFor = getIntent().getStringExtra("for");
        contactNumbers = getIntent().getStringArrayListExtra("contacts");





        mArrayList = new ArrayList<>();
    }

    private void addFieldsToList(String aValue) {
        mArrayList.add(aValue);
        addMoreFields(aValue);
    }

    private void addMoreFields(final String value) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // final View myView = inflater.inflate(R.layout.item_add_values, null);




     //   myView.setTag(value);







        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
      //  myView.setLayoutParams(layoutParams);
       // llParent.addView(myView);
        etFirstValue.setText("");
    }



}