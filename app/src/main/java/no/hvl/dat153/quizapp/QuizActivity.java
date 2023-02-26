package no.hvl.dat153.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    public boolean choice;
    public CountDownTimer timer;
    int score = 0;
    int answered = 0;
    private ArrayList<Pair<Bitmap, String>> questions;
    private ImageView imageView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btnExit;
    public ProgressBar simpleProgressBar;

    private void displayNextQuestion() {

        simpleProgressBar=(ProgressBar) findViewById(R.id.progressBar2); // initiate the progress bar

        TextView scoreBoard = findViewById(R.id.score);
        TextView feedback = findViewById(R.id.feedback);
        scoreBoard.setText("Score: " + score + "/" + answered);

       if(choice) {
           simpleProgressBar.setMax(15);
           CountDownTimer timer = new CountDownTimer(20000, 2000) {

               public void onTick(long millisUntilFinished) {
                   simpleProgressBar.setProgress((int) (millisUntilFinished / 2000));
               }

               public void onFinish() {
                   answered++;
                   if (answered == questions.size()) {
                       returnToMainMenu();
                   } else {
                       displayNextQuestion();
                   }
               }
           }.start();
       }
       else{
           simpleProgressBar.setMax(questions.size());
       }

       simpleProgressBar.setProgress(score);

        // Set the imageView to be the first element in the question array
        imageView.setImageBitmap(questions.get(answered).first);

        // The correct answer
        String correctAnswer = questions.get(answered).second;
        System.out.println("Question order: 1: " + questions.get(0).second + ", 2: " + questions.get(1).second + ", 3:" + questions.get(2).second);
        System.out.println("Current question: (" + (answered+1) + ") " + questions.get(answered).second);
        System.out.println("Current answer: " + correctAnswer);

        // Get two wrong answers at random
        ArrayList<String> wrongAnswers = new ArrayList<>();

        ArrayList<Pair<Bitmap, String>> shuffled = new ArrayList<>(questions);
        Collections.shuffle(shuffled);

        for (Pair<Bitmap, String> p : shuffled) {
            if (!p.second.equals(correctAnswer)) {
                wrongAnswers.add(p.second);
                if (wrongAnswers.size() == 2) break;
            }
        }

        // Store buttons in arraylist and shuffle
        ArrayList<Button> buttons = new ArrayList<>( Arrays.asList(btn1, btn2, btn3));
        Collections.shuffle(buttons);

        // Set the correct answer button
        buttons.get(0).setText(correctAnswer);
        buttons.get(0).setOnClickListener(view -> {
            score++;
            answered++;
            if(answered==questions.size()){
                if(choice){
                timer.cancel();
                }
                returnToMainMenu();
            }
            else {
                feedback.setText("");
                if(choice){
                timer.cancel();
                }
                displayNextQuestion();
            }
        });

        // Set the wrong answer buttons
        for (int i = 1; i <= wrongAnswers.size(); i++) {
            buttons.get(i).setText(wrongAnswers.get(i-1));
            buttons.get(i).setOnClickListener(view -> {
                answered++;
                if(answered==questions.size()){
                    if(choice) {
                        timer.cancel();
                    }
                    returnToMainMenu();
                }
                else {
                    feedback.setText("Correct answer was: " + correctAnswer);
                    if(choice) {
                        timer.cancel();
                    }
                    displayNextQuestion();
                }
            });
        }
        exitButton();
    }

    public void returnToMainMenu(){
        Intent intent = new Intent(QuizActivity.this, new MainActivity().getClass());
        startActivity(intent);
    }
    public void exitButton(){
        Button btnExit = findViewById(R.id.exitButton);
        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(QuizActivity.this, new MainActivity().getClass());
            startActivity(intent);
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_quiz);
        Bundle values = getIntent().getExtras();
        choice = values.getBoolean("choice");
// Fetch questions
        questions = AnswersActivity.getQuestions();

        //Shuffle the questions to preserve randomness
        Collections.shuffle(questions);
        System.out.println("SHUFFLED");

        // Setup imageView and buttons
        imageView = findViewById(R.id.imageView2);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);

        displayNextQuestion();
    }

}