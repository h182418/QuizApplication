package no.hvl.dat153.quizapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!initialized) {
            //  Initialize question list, can be accessed regardless of what activity started first
            Bitmap q1 = BitmapFactory.decodeResource(getResources(), R.drawable.klopp);
            Bitmap q2 = BitmapFactory.decodeResource(getResources(), R.drawable.arteta);
            Bitmap q3 = BitmapFactory.decodeResource(getResources(), R.drawable.tenhag);
            AnswersActivity.initializeQuestions(q1, q2, q3);
            initialized = true;
        }

        // Launches the quiz activity
        Button quizBtn = findViewById(R.id.Quizbutton);
        quizBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("choice", false);
            startActivity(intent);
        });

        // Launches the Answers activity
        Button answersBtn = findViewById(R.id.AnsButton);
        answersBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AnswersActivity.class);
            startActivity(intent);
        });

        // Launches the Add entry activity
        Button addEntryBtn = findViewById(R.id.Addbutton);
        addEntryBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_quiz, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            Intent intentAnswers = new Intent(MainActivity.this, AnswersActivity.class);
            Intent intentQuiz = new Intent(MainActivity.this, QuizActivity.class);
            Intent intentMain = new Intent(MainActivity.this, MainActivity.class);
            Intent intentAdd = new Intent(MainActivity.this, AddActivity.class);
            switch (item.getItemId()){
                case R.id.addQuestions:
                    startActivity(intentAdd);
                    return true;
                case R.id.showAnswers:
                    startActivity(intentAnswers);
                    return true;
                case R.id.easy:
                    intentQuiz.putExtra("choice",false);
                    startActivity(intentQuiz);
                    return true;
                case R.id.hard:
                    intentQuiz.putExtra("choice",true);
                    startActivity(intentQuiz);
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }


/*   @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    } */
