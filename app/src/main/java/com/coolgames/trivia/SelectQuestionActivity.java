package com.coolgames.trivia;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import trivia.R;


public class SelectQuestionActivity extends AppCompatActivity {
    private boolean isSelectionLoaded = false;
    private RequestQueue requestQueue;
    private QuizState quizState;

    public void firstChoice(View view) {
        startAnswerActivity(0);
    }

    public void secondChoice(View view) {
        startAnswerActivity(1);
    }

    public void thirdChoice(View view) {
        startAnswerActivity(2);
    }

    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        requestQueue = Volley.newRequestQueue(this);
        initQuestionSelection();
        Intent intent = getIntent();
        quizState = intent.getParcelableExtra(getString(R.string.quizStateKey));

        TextView textView = findViewById(R.id.score);
        textView.setText(getString(R.string.score, quizState.getScore()));

        textView = findViewById(R.id.lives);
        textView.setText(getString(R.string.lives, quizState.getLives()));
    }

    private void initQuestionSelection() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://opentdb.com/api.php?amount=3",
                null,
                response -> {
                    try {
                        List<Question> selection = new ArrayList<>();
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i = 0; i < 3; i++) {
                            Question question = new Question(jsonArray.getJSONObject(i));
                            selection.add(question);
                        }
                        displaySelection(selection);
                        quizState.setSelection(selection);
                        isSelectionLoaded = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        );
        requestQueue.add(request);
    }

    private void displaySelection(List<Question> selection) {
        Button button = (Button)findViewById(R.id.button1);
        button.setText(selection.get(0).getCategory());
        TextView textView = findViewById(R.id.textView1);
        textView.setText(selection.get(0).getDifficultyString());

        button = (Button)findViewById(R.id.button2);
        button.setText(selection.get(1).getCategory());
        textView = findViewById(R.id.textView2);
        textView.setText(selection.get(1).getDifficultyString());

        button = (Button)findViewById(R.id.button3);
        button.setText(selection.get(2).getCategory());
        textView = findViewById(R.id.textView3);
        textView.setText(selection.get(2).getDifficultyString());
    }

    private void startAnswerActivity(int i) {
        if (isSelectionLoaded) {
            Intent intent = new Intent(this, AnswerActivity.class);
            intent.putExtra(getString(R.string.questionKey), quizState.getQuestion(i));
            intent.putExtra(getString(R.string.quizStateKey), quizState);
            startActivity(intent);
        }
    }
}