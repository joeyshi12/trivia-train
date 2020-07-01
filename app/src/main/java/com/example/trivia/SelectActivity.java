package com.example.trivia;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trivia.model.Question;
import com.example.trivia.model.QuizRunner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SelectActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private boolean loadedSelection = false;
    private QuizRunner quizRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        mQueue = Volley.newRequestQueue(this);
        getSelection();
        Intent intent = getIntent();
        quizRunner = intent.getParcelableExtra("quizRunner");

        TextView textView = findViewById(R.id.score);
        textView.setText("Score: " + quizRunner.getScore());

        textView = findViewById(R.id.lives);
        textView.setText("Lives: " + quizRunner.getLives());
    }

    private void getSelection() {
        String url = "https://opentdb.com/api.php?amount=3";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Question> selection = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < 3; i++) {
                                Question question = new Question(jsonArray.getJSONObject(i));
                                selection.add(question);
                            }
                            displaySelection(selection);
                            quizRunner.setSelection(selection);
                            loadedSelection = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
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
        if (loadedSelection) {
            Intent intent = new Intent(this, AnswerActivity.class);
            intent.putExtra("question", quizRunner.getQuestion(i));
            intent.putExtra("quizRunner", quizRunner);
            startActivity(intent);
        }
    }

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
}