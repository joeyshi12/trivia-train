package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.trivia.model.QuizRunner;

public class ScoreActivity extends AppCompatActivity {
    QuizRunner quizRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        quizRunner = intent.getParcelableExtra("quizRunner");

        TextView textView = findViewById(R.id.score);
        textView.setText("Score: " + quizRunner.getScore());

        SharedPreferences mPrefs = getSharedPreferences("highscore", 0);
        int highscore = mPrefs.getInt("highscore", 0);

        textView = findViewById(R.id.highScore);
        textView.setText("Highscore: " + quizRunner.getHighScore());

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("highscore", quizRunner.getHighScore()).commit();
    }

    public void back(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() { }
}