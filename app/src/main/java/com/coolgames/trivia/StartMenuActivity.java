package com.coolgames.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import trivia.R;

public class StartMenuActivity extends AppCompatActivity {
    QuizState quizState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        String highscoreKey = getString(R.string.highscoreKey);
        SharedPreferences mPrefs = getSharedPreferences(highscoreKey, 0);
        int highscore = mPrefs.getInt(highscoreKey, 0);
        quizState = new QuizState(highscore);
        TextView textView = findViewById(R.id.highScore);
        textView.setText(getString(R.string.highscore, highscore));
    }

    public void start(View view) {
        Intent intent = new Intent(this, SelectQuestionActivity.class);
        intent.putExtra(getString(R.string.quizStateKey), quizState);
        startActivity(intent);
    }
}