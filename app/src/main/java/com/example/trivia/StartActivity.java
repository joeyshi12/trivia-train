package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.trivia.model.QuizRunner;

public class StartActivity extends AppCompatActivity {
    QuizRunner quizRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();

        SharedPreferences mPrefs = getSharedPreferences("highscore", 0);
        int highscore = mPrefs.getInt("highscore", 0);
        quizRunner = new QuizRunner(highscore);

        TextView textView = findViewById(R.id.highScore);
        textView.setText("Highscore: " + highscore);
    }

    public void start(View view) {
        Intent intent = new Intent(this, SelectActivity.class);
        intent.putExtra("quizRunner", quizRunner);
        startActivity(intent);
    }
}