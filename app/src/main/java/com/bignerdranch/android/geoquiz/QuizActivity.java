package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {

    private Button btnTrue;
    private Button btnFalse;
    private ImageButton btnNext;
    private ImageButton btnPrev;


    private int currentIndex = 0;
    private Question[] questions = new Question[]{
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, false),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true),
    };
    private TextView txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);

        btnTrue.setOnClickListener(v -> checkResult(currentIndex, true));
        btnFalse.setOnClickListener(v -> checkResult(currentIndex, false));
        btnNext.setOnClickListener(v -> next(1));
        btnPrev.setOnClickListener(v -> next(-1));


        txtQuestion.setOnClickListener(v -> next(1));

        showQuestion(0);
    }

    private void next(int in) {
        currentIndex = currentIndex + in;

        if (currentIndex < 0)
            currentIndex = questions.length - 1;

        int index = currentIndex % questions.length;
        showQuestion(index);
    }

    private void showQuestion(int index) {
        index = index % questions.length;

        txtQuestion.setText(questions[index].getTextResId());
    }

    private void checkResult(int index, boolean isTrue) {
        index = index % questions.length;

        if (questions[index].isAnswerTrue() == isTrue) {
            show(R.string.correct_toast);
            next(1);
        } else {
            show(R.string.incorrect_toast);
        }
    }

    private void show(int res) {
        Toast toast = Toast.makeText(this, res, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void show(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }
}
