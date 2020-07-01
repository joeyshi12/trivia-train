package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.trivia.model.Question;
import com.example.trivia.model.QuizRunner;

public class AnswerActivity extends AppCompatActivity {
    private Question question;
    private QuizRunner quizRunner;
    private int ansIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        question = intent.getParcelableExtra("question");
        quizRunner = intent.getParcelableExtra("quizRunner");

        TextView textView = findViewById(R.id.lives);
        assert question != null;
        textView.setText(question.getPrompt());

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton button;

        if (question.getIsMultiple()) {
            ansIdx = (int) (Math.random() * 4);
            if (ansIdx == 0) {
                button = new RadioButton(this);
                button.setText(question.getCorrectAnswer());
                button.setId(1);
                button.setTextColor(getResources().getColor(R.color.colorAccent));
                group.addView(button);
            }

            for (int i = 0; i < 3; i++) {
                button = new RadioButton(this);
                button.setText(question.getIncorrectAnswers().get(i));
                button.setId(i + 2);
                button.setTextColor(getResources().getColor(R.color.colorAccent));
                group.addView(button);
                if (i == ansIdx - 1) {
                    button = new RadioButton(this);
                    button.setText(question.getCorrectAnswer());
                    button.setId(1);
                    button.setTextColor(getResources().getColor(R.color.colorAccent));
                    group.addView(button);
                }
            }
        } else {
            int trueValue = (question.getCorrectAnswer().equals("True")) ? 1 : 0;
            button = new RadioButton(this);
            button.setText("True");
            button.setId(trueValue);
            button.setTextColor(getResources().getColor(R.color.colorAccent));
            group.addView(button);

            button = new RadioButton(this);
            button.setText("False");
            button.setId(1 - trueValue);
            button.setTextColor(getResources().getColor(R.color.colorAccent));
            group.addView(button);
        }

    }

    @Override
    public void onBackPressed() { }

    public void confirm(View view) {
        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        int id = group.getCheckedRadioButtonId();
        if (id == -1) {
            return;
        }


        quizRunner.mark(question, id);

        if (quizRunner.isGameOver()) {
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("quizRunner", quizRunner);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SelectActivity.class);
            intent.putExtra("quizRunner", quizRunner);
            startActivity(intent);
        }
    }
}