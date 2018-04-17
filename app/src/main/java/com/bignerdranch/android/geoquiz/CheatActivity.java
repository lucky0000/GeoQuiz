package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private final static String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";

    private TextView txtAnswer;
    private Button btnShowAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        txtAnswer = (TextView) findViewById(R.id.txt_answer);
        btnShowAnswer = (Button) findViewById(R.id.btn_show_answer);

        btnShowAnswer.setOnClickListener(v -> {
            boolean isTure=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
            txtAnswer.setText(String.valueOf(isTure));
        });

    }
}