package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {
    private TextView txtQuestion;
    private Button btnTrue;
    private Button btnFalse;
    private Button btnCheat;
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private final static String KEY_INDEX = "index";
    private final static String KEY_COUNT = "count";
    private final static String KEY_OKCOUNT = "okCount";
    private final static String KEY_ISSEND = "questionsIsSend";
    private final static String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";

    private static final String TAG = "QuizActivity";

    private Question[] questions = new Question[]{
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, false),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true),
    };

    private int currentIndex = 0;
    //对应的问题是否回答 需保存状态
    private boolean[] questionsIsSend = new boolean[questions.length];

    //答对的问题数 需保存状态
    private int okCount;
    //总答题数 需保存状态
    private int count;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: " + currentIndex);
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentIndex);

        outState.putInt(KEY_OKCOUNT, okCount);
        outState.putInt(KEY_COUNT, count);
        outState.putBooleanArray(KEY_ISSEND, questionsIsSend);

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        Log.d(TAG, "onCreate: " + currentIndex);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnCheat = (Button) findViewById(R.id.btn_cheat);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);

        btnTrue.setOnClickListener(v -> checkResult(currentIndex, true));
        btnFalse.setOnClickListener(v -> checkResult(currentIndex, false));
        btnCheat.setOnClickListener(v -> {
            Intent intent=newIntent(currentIndex);
            startActivity(intent);
        });
        btnNext.setOnClickListener(v -> next(1));
        btnPrev.setOnClickListener(v -> next(-1));
        txtQuestion.setOnClickListener(v -> next(1));

//        showQuestion(currentIndex);
        next(0);
    }

    private Intent newIntent(int index) {
        Intent intent=new Intent(QuizActivity.this,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, questions[index].isAnswerTrue());
        return intent;
    }

    /**
     * 显示第几题
     *
     * @param in
     */
    private void next(int in) {
        currentIndex = currentIndex + in;

//        if (currentIndex < 0)
//            currentIndex = questions.length - 1;

        pageAction();

        int index = currentIndex % questions.length;
        showQuestion(index);
        //如果答过题 则隐藏选项按钮
        checkShowQuestionButton(index);
    }

    /**
     * 页码处理
     */
    private void pageAction() {
        if (currentIndex <= 0) {
            currentIndex = 0;
            btnPrev.setVisibility(View.INVISIBLE);
        } else
            btnPrev.setVisibility(View.VISIBLE);

        if (currentIndex >= questions.length - 1) {
            currentIndex = questions.length - 1;
            btnNext.setVisibility(View.INVISIBLE);
        } else
            btnNext.setVisibility(View.VISIBLE);

    }

    /**
     * 显示题目
     *
     * @param index
     */
    private void showQuestion(int index) {
        index = index % questions.length;
        txtQuestion.setText(questions[index].getTextResId());
    }

    /**
     * 答题并记录
     *
     * @param index
     * @param isOk
     */
    private void questionSend(int index, boolean isOk) {
        questionsIsSend[index] = true;
        if (isOk) {
            okCount++;
        }
        count++;
        if (count == questions.length) {
            //显示分数
            double score = (double) okCount / questions.length * 100;
            show(String.format(getResources().getString(R.string.score_toast), score));
            restart();
        }
    }


    /**
     * 是否显示题目答题按钮
     *
     * @param index
     */
    private void checkShowQuestionButton(int index) {
        if (questionsIsSend[index]) {
            btnTrue.setVisibility(View.INVISIBLE);
            btnFalse.setVisibility(View.INVISIBLE);
        } else {
            btnTrue.setVisibility(View.VISIBLE);
            btnFalse.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 判断答题是否正确
     *
     * @param index
     * @param isTrue
     */
    private void checkResult(int index, boolean isTrue) {
        index = index % questions.length;
        boolean result = false;
        if (questions[index].isAnswerTrue() == isTrue) {
            result = true;
            show(R.string.correct_toast);
        } else {
            show(R.string.incorrect_toast);
        }

        questionSend(index, result);
        next(1);
    }



    /**
     * 答完所有题后重置
     */
    private void restart() {
        currentIndex = 0;
        questionsIsSend = new boolean[questions.length];
        okCount = 0;
        count = 0;
    }

    /**
     * 显示提示信息
     *
     * @param res
     */
    private void show(int res) {
        Toast toast = Toast.makeText(this, res, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示提示信息
     *
     * @param message
     */
    private void show(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }
}
