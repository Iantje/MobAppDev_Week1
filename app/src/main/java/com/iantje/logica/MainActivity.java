package com.iantje.logica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView[] _questionFields;
    private EditText[] _answerFields;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });

        _questionFields = new TextView[]{
                findViewById(R.id.fieldAQuestion1), findViewById(R.id.fieldBQuestion1),
                findViewById(R.id.fieldAQuestion2), findViewById(R.id.fieldBQuestion2),
                findViewById(R.id.fieldAQuestion3), findViewById(R.id.fieldBQuestion3),
                findViewById(R.id.fieldAQuestion4), findViewById(R.id.fieldBQuestion4)
        };

        _answerFields = new EditText[]{
                findViewById(R.id.answerOne),
                findViewById(R.id.answerTwo),
                findViewById(R.id.answerThree),
                findViewById(R.id.answerFour)
        };

        if((float) _questionFields.length / (float) _answerFields.length != 2)
        {
            Log.e(TAG, "question and answer fields don't match up (not 2:1)");
        }

        for (int i = 0; i < _answerFields.length; i++) {
            _answerFields[i].addTextChangedListener(new TextWatcher() {
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

        for (int i = 0; i < _answerFields.length; i++) {
            int questionPlace = i * 2;
            boolean questionAnswer = false;

            if (_questionFields[questionPlace].getText().toString().toUpperCase().equals("T") &&
                    _questionFields[questionPlace + 1].getText().toString().toUpperCase().equals("T")) {
                questionAnswer = true;
            }

            if (!_answerFields[i].getText().toString().toUpperCase().equals("T") && questionAnswer) {
                allCorrect = false;
            } else if (!_answerFields[i].getText().toString().toUpperCase().equals("F") && !questionAnswer) {
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
