package com.coolgames.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import trivia.R;

public class ScoreActivity extends AppCompatActivity {
    QuizState quizState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();
        quizState = intent.getParcelableExtra(getString(R.string.quizStateKey));

        TextView textView = findViewById(R.id.score);
        textView.setText(getString(R.string.score, quizState.getScore()));

        String highscoreKey = getString(R.string.highscoreKey);
        SharedPreferences mPrefs = getSharedPreferences(highscoreKey, 0);
        int highscore = mPrefs.getInt(highscoreKey, 0);

        textView = findViewById(R.id.highScore);
        textView.setText(getString(R.string.highscore, quizState.getCurrentHighscore()));

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt(highscoreKey, quizState.getCurrentHighscore()).apply();
    }

    public void back(View view) {
        Intent intent = new Intent(this, StartMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() { }
}