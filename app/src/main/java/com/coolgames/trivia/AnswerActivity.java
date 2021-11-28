package com.coolgames.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import trivia.R;

public class AnswerActivity extends AppCompatActivity {
    private Question question;
    private QuizState quizState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        question = intent.getParcelableExtra(getString(R.string.questionKey));
        quizState = intent.getParcelableExtra(getString(R.string.quizStateKey));

        TextView textView = findViewById(R.id.lives);
        assert question != null;
        textView.setText(question.getPrompt());

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton button;

        if (question.getIsMultiple()) {
            int ansIdx = (int) (Math.random() * 4);
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

        quizState.mark(question, id);
        Intent intent;
        if (quizState.isGameOver()) {
            intent = new Intent(this, ScoreActivity.class);
        } else {
            intent = new Intent(this, SelectQuestionActivity.class);
        }
        intent.putExtra(getString(R.string.quizStateKey), quizState);
        startActivity(intent);
    }
}