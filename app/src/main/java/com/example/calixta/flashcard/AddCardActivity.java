package com.example.calixta.flashcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this, Question.class);
                AddCardActivity.this.startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);

            }
        });

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(AddCardActivity.this, Question.class);
                //AddCardActivity.this.startActivityForResult(data, 100);
                data.putExtra("user_q", ((EditText) findViewById(R.id.cust_question)).getText().toString());
                data.putExtra("user_ans", ((EditText) findViewById(R.id.cust_answer)).getText().toString());
                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);

            }
        });
    }
        }

