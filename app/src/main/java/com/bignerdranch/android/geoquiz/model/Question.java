package com.bignerdranch.android.geoquiz.model;

public class Question {
    private int textResId;

    public Question(int textResId, boolean answerTrue) {
        this.textResId = textResId;
        this.answerTrue = answerTrue;
    }

    public int getTextResId() {

        return textResId;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    private boolean answerTrue;

}
