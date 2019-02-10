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

    private TextView[] questionFields;
    private EditText[] answerFields;
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

        questionFields = new TextView[]{
                findViewById(R.id.fieldAQuestion1), findViewById(R.id.fieldBQuestion1),
                findViewById(R.id.fieldAQuestion2), findViewById(R.id.fieldBQuestion2),
                findViewById(R.id.fieldAQuestion3), findViewById(R.id.fieldBQuestion3),
                findViewById(R.id.fieldAQuestion4), findViewById(R.id.fieldBQuestion4)
        };

        answerFields = new EditText[]{
                findViewById(R.id.answerOne),
                findViewById(R.id.answerTwo),
                findViewById(R.id.answerThree),
                findViewById(R.id.answerFour)
        };

        if((float)questionFields.length / (float)answerFields.length != 2)
        {
            Log.e(TAG, "question and answer fields don't match up (not 2:1)");
        }

        for (int i = 0; i < answerFields.length; i++) {
            answerFields[i].addTextChangedListener(new TextWatcher() {
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

    private void checkAnswers() {
        boolean allCorrect = true;

        for (int i = 0; i < answerFields.length; i++) {
            int questionPlace = i * 2;
            boolean questionAnswer = true;

            if (questionFields[questionPlace].getText().toString().toUpperCase().equals("F") ||
                    questionFields[questionPlace + 1].getText().toString().toUpperCase().equals("F")) {
                questionAnswer = false;
            }

            if (answerFields[i].getText().toString().toUpperCase().equals("T") && !questionAnswer) {
                allCorrect = false;
            } else if (answerFields[i].getText().toString().toUpperCase().equals("F") && questionAnswer) {
                allCorrect = false;
            }
        }

        if (allCorrect) {
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_LONG).show();
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
