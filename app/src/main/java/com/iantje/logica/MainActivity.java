package com.iantje.logica;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TextView> questionFields;
    private ArrayList<EditText> answerFields;
    private Button mCheckButton;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCheckButton = findViewById(R.id.checkButton);
        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });

        ViewGroup constraintLayout = findViewById(R.id.constraintLayout);

        questionFields = getTextViewsByTag(constraintLayout, "questionVar");

        answerFields = getEditTextsByTag(constraintLayout, "answerVar");

        if((float)questionFields.size() / (float)answerFields.size() != 2)
        {
            Log.e(TAG, "question and answer fields don't match up (not 2:1)");
        }

        for (int i = 0; i < answerFields.size(); i++) {
            answerFields.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!checkValidResponse(s.toString())) {
                        // Not correct response
                        s.clear();
                    }
                }
            });
        }
    }

    private static ArrayList<TextView> getTextViewsByTag(ViewGroup root, String tag){
        // Create the array list
        ArrayList<TextView> textViews = new ArrayList<TextView>();

        // Check how many children the root has
        final int childCount = root.getChildCount();
        // Loop through all the children
        for (int i = 0; i < childCount; i++) {
            // Get the child
            final View child = root.getChildAt(i);
            // If the child has more children
            if (child instanceof ViewGroup) {
                // Add all of them
                textViews.addAll(getTextViewsByTag((ViewGroup) child, tag));
            }

            // Get tag of the child
            final Object tagObj = child.getTag();
            // If there is a tag, and it equals what we want, add it to our list
            if (tagObj != null && tagObj.equals(tag) && tagObj instanceof TextView) {
                textViews.add((TextView)child);
            }

        }
        return textViews;
    }

    private static ArrayList<EditText> getEditTextsByTag(ViewGroup root, String tag){
        // Create the array list
        ArrayList<EditText> editTexts = new ArrayList<>();

        // Check how many children the root has
        final int childCount = root.getChildCount();
        // Loop through all the children
        for (int i = 0; i < childCount; i++) {
            // Get the child
            final View child = root.getChildAt(i);
            // If the child has more children
            if (child instanceof ViewGroup) {
                // Add all of them
                editTexts.addAll(getEditTextsByTag((ViewGroup) child, tag));
            }

            // Get tag of the child
            final Object tagObj = child.getTag();
            // If there is a tag, and it equals what we want, add it to our list
            if (tagObj != null && tagObj.equals(tag) && tagObj instanceof EditText) {
                editTexts.add((EditText)child);
            }

        }
        return editTexts;
    }

    private void checkAnswers() {
        for (int i = 0; i < answerFields.size(); i++) {
            int questionPlace = i * 2;
            boolean questionAnswer = true;

            if (questionFields.get(questionPlace).getText().toString().toUpperCase().equals("F") ||
                    questionFields.get(questionPlace + 1).getText().toString().toUpperCase().equals("F")) {
                questionAnswer = false;
            }

            if (answerFields.get(i).getText().toString().toUpperCase().equals("T") && questionAnswer) {
                Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkValidResponse(String value) {
        if(value.toUpperCase().equals("T") || value.toUpperCase().equals("F") || value.equals("")) {
            return true;
        }

        Toast.makeText(this, getString(R.string.wrong_input), Toast.LENGTH_LONG).show();
        return false;
    }
}
