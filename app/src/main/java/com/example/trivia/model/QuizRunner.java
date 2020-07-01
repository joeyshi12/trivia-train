package com.example.trivia.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuizRunner implements Parcelable {
    private int lives = 3;
    private int score = 0;
    private int highScore;
    private List<Question> selection;

    public QuizRunner(int highScore) {
        this.highScore = highScore;
    }


    protected QuizRunner(Parcel in) {
        lives = in.readInt();
        score = in.readInt();
        highScore = in.readInt();
    }

    public static final Creator<QuizRunner> CREATOR = new Creator<QuizRunner>() {
        @Override
        public QuizRunner createFromParcel(Parcel in) {
            return new QuizRunner(in);
        }

        @Override
        public QuizRunner[] newArray(int size) {
            return new QuizRunner[size];
        }
    };

    public void setSelection(List<Question> selection) {
        this.selection = selection;
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

    public int getHighScore() {
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
