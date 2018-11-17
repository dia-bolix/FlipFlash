package com.example.calixta.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Question extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        findViewById(R.id.answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.first_question).setVisibility(View.VISIBLE);


            }
        });

        findViewById(R.id.first_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.first_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer).setVisibility(View.VISIBLE);
            }
        });


        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Question.this, AddCardActivity.class);
                Question.this.startActivityForResult(intent, 100);
            }
        });

        }
        @Override
        protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            String user_q = data.getExtras().getString("user_q");
            String user_ans = data.getExtras().getString("user_ans");
            ((TextView) findViewById(R.id.first_question)).setText(user_q);
            ((TextView) findViewById(R.id.answer)).setText(user_ans);

        }
        }
        }

