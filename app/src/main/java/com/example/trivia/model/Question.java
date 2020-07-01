package com.example.trivia.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question implements Parcelable {
    private final String category;
    private final boolean isMultiple;
    private int difficulty;
    private String question;
    private final String correctAnswer;
    private ArrayList<String> incorrectAnswers;

    public Question(JSONObject jsonQuestion) throws JSONException {
        category = jsonQuestion.getString("category");
        isMultiple = jsonQuestion.getString("type").equals("multiple");
        String diff = jsonQuestion.getString("difficulty");
        if (diff.equals("easy")) {
            difficulty = 0;
        } else if (diff.equals("medium")) {
            difficulty = 1;
        } else {
            difficulty = 2;
        }
        question = cleanString(jsonQuestion.getString("question"));
        correctAnswer = cleanString(jsonQuestion.getString("correct_answer"));
        if (isMultiple) {
            incorrectAnswers = new ArrayList<>();
            JSONArray jsonIncorrectQuestions = jsonQuestion.getJSONArray("incorrect_answers");
            incorrectAnswers.add(cleanString(jsonIncorrectQuestions.getString(0)));
            incorrectAnswers.add(cleanString(jsonIncorrectQuestions.getString(1)));
            incorrectAnswers.add(cleanString(jsonIncorrectQuestions.getString(2)));
        }
    }

    protected Question(Parcel in) {
        category = in.readString();
        isMultiple = in.readByte() != 0;
        difficulty = in.readInt();
        question = in.readString();
        correctAnswer = in.readString();
        incorrectAnswers = in.createStringArrayList();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getDifficultyString() {
        if (difficulty == 0) {
            return "Easy";
        } else if (difficulty == 1) {
            return "Medium";
        } else {
            return "Hard";
        }
    }

    public String getCategory() {
        return category;
    }

    public boolean getIsMultiple() {
        return isMultiple;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getPrompt() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    private String cleanString(String string) {
        string = string.replaceAll("&#039;", "'");
        string = string.replaceAll("&quot;", "\"");
        return string;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(category);
        parcel.writeByte((byte) (isMultiple ? 1 : 0));
        parcel.writeInt(difficulty);
        parcel.writeString(question);
        parcel.writeString(correctAnswer);
        parcel.writeStringList(incorrectAnswers);
    }
}
