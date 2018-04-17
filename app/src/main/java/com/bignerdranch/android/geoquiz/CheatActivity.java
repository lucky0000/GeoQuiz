package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class CheatActivity extends AppCompatActivity {
    private final static String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    public final static String EXTRA_ANSWER_IS_SHOW = "com.bignerdranch.android.geoquiz.answer_is_show";

    private final static String KEY_ISCHEAT = "com.bignerdranch.android.geoquiz.isCheat";
    private final static String KEY_ANSWER = "com.bignerdranch.android.geoquiz.answer";

    private TextView txtAnswer;
    private TextView txtSDKVersion;
    private Button btnShowAnswer;

    private static final String TAG = "CheatActivity";

    private boolean isCheat = false;
    private boolean answerIsTrue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        txtAnswer = (TextView) findViewById(R.id.txt_answer);
        txtSDKVersion = (TextView) findViewById(R.id.txtSDKVersion);
        btnShowAnswer = (Button) findViewById(R.id.btn_show_answer);

        if (savedInstanceState != null) {
            isCheat = savedInstanceState.getBoolean(KEY_ISCHEAT);
            if (isCheat) {
                answerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER);
                showAnswer();
            }
        } else {
            answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        }


        btnShowAnswer.setOnClickListener(v -> {
            Log.d(TAG, "setOnClickListener:btnShowAnswer ");
            showAnswer();
            hideButton();
        });

        String sdkVersion = String.format(getResources().getString(R.string.sdk_version), Build.VERSION.SDK_INT);
        txtSDKVersion.setText(sdkVersion);
    }

    private void showAnswer() {
        boolean isTure = answerIsTrue;
        txtAnswer.setText(String.valueOf(isTure));
        isCheat = true;
        newIndex(isCheat);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");

        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_ISCHEAT, isCheat);
        outState.putBoolean(KEY_ANSWER, answerIsTrue);


    }

    /**
     * 隐藏按钮
     * 判断版本号是否显示动画效果
     */
    private void hideButton() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "hideButton: true");
            int cx = btnShowAnswer.getWidth() / 2;
            int cy = btnShowAnswer.getHeight() / 2;
            float radius = btnShowAnswer.getWidth();
            Animator anim = null;

            anim = ViewAnimationUtils.createCircularReveal(btnShowAnswer, cx, cy, radius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    btnShowAnswer.setVisibility(View.INVISIBLE);
                }
            });

            anim.start();
        } else {
            Log.d(TAG, "hideButton: true");
            btnShowAnswer.setVisibility(View.INVISIBLE);
        }


    }

    private void newIndex(boolean isAnswershown) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_IS_SHOW, isAnswershown);
        setResult(RESULT_OK, intent);
    }
}
