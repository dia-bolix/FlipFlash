package com.example.calixta.flashcard;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

public class Question extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
        final View answer_side = findViewById(R.id.answer);
        final View question_side = findViewById(R.id.first_question);
        //if there is something in the database, show it
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) question_side).setText(allFlashcards.get(0).getQuestion());
            ((TextView) answer_side).setText(allFlashcards.get(0).getAnswer());
        } else {
            //else, add initial question to the database
            String inital_question = ((TextView) question_side).getText().toString();
            String inital_ans = ((TextView) answer_side).getText().toString();

            flashcardDatabase.insertCard(new Flashcard(inital_question, inital_ans));
            allFlashcards = flashcardDatabase.getAllCards();
            currentCardDisplayedIndex --;
        }

        //when you click on answer, switch to the question
        answer_side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_side.setVisibility(View.INVISIBLE);
                question_side.setVisibility(View.VISIBLE);


            }
        });
        //when you click on the question, show the answer (with animation)
        findViewById(R.id.first_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the center for the clipping circle
                int cx = answer_side.getWidth() / 2;
                int cy = answer_side.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answer_side, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                question_side.setVisibility(View.INVISIBLE);
                answer_side.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

        //when you click on the add button switch to the add card activity screen
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Question.this, AddCardActivity.class);
                Question.this.startActivityForResult(intent, 100);
                //animate left when we enter, right when we exit
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        //clicking on the next button will advance to the next flashcard in the database
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            currentCardDisplayedIndex ++;
            //go back to the first item
            if (currentCardDisplayedIndex > allFlashcards.size() -1) {
                currentCardDisplayedIndex = 0;
                }
            //switches text to next flashcard
            ((TextView) question_side).setText((allFlashcards.get(currentCardDisplayedIndex).getQuestion()));
            ((TextView) answer_side).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

            //makes sure you see answer first
            answer_side.setVisibility(View.INVISIBLE);
            question_side.setVisibility(View.VISIBLE);

            //animation
            final Animation leftoutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
            final Animation rightinAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
            question_side.startAnimation(leftoutAnim);
            question_side.startAnimation(rightinAnim);


            }


        });
        //if you click on the back button do the reverse of above
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardDisplayedIndex --;
            if (currentCardDisplayedIndex == -1) {
                currentCardDisplayedIndex = allFlashcards.size()-1 ;
            }
            ((TextView) question_side).setText((allFlashcards.get(currentCardDisplayedIndex).getQuestion()));
            ((TextView) answer_side).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

            //makes sure you see answer first
            answer_side.setVisibility(View.INVISIBLE);
            question_side.setVisibility(View.VISIBLE);

            //add animation
            final Animation leftinAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
            final Animation rightoutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_out);
            question_side.startAnimation(rightoutAnim);
            question_side.startAnimation(leftinAnim);

            }
        });
        }
    //after you finish from add card activity, change the text to the inputted data
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            String user_q = data.getExtras().getString("user_q");
            String user_ans = data.getExtras().getString("user_ans");
            ((TextView) findViewById(R.id.first_question)).setText(user_q);
            ((TextView) findViewById(R.id.answer)).setText(user_ans);
            flashcardDatabase.insertCard(new Flashcard(user_q, user_ans));
            allFlashcards = flashcardDatabase.getAllCards();

        }
    }



}

