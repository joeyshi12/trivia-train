package com.coolgames.trivia;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuizState implements Parcelable {
    private int lives;
    private int score;
    private int highScore;
    private List<Question> selection;

    public QuizState(int highScore) {
        this.lives = 3;
        this.score = 0;
        this.highScore = highScore;
    }

    protected QuizState(Parcel in) {
        lives = in.readInt();
        score = in.readInt();
        highScore = in.readInt();
    }

    public int getCurrentHighscore() {
        highScore = Math.max(score, highScore);
        return highScore;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public Question getQuestion(int i) {
        return selection.get(i);
    }

    public void mark(Question question, int id) {
        if (id == 1) {
            score += Math.pow(2, question.getDifficulty());
        } else {
            lives -= 1;
        }
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public void setSelection(List<Question> selection) {
        this.selection = selection;
    }

    public static final Creator<QuizState> CREATOR = new Creator<QuizState>() {
        @Override
        public QuizState createFromParcel(Parcel in) {
            return new QuizState(in);
        }

        @Override
        public QuizState[] newArray(int size) {
            return new QuizState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(lives);
        parcel.writeInt(score);
        parcel.writeInt(highScore);
    }
}
