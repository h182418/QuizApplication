package no.hvl.dat153.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {


    private Bitmap picture;
    private EditText answerText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_add);

        imageView = findViewById(R.id.imageView3);

        Button selectPhoto = (Button) findViewById(R.id.select_picture);
        selectPhoto.setOnClickListener(view -> selectPicture());

        Button takePhoto = (Button) findViewById(R.id.take_picture);
        takePhoto.setOnClickListener(view -> takePicture());

        answerText = (EditText) findViewById(R.id.answer_entry);

        Button submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(view -> submitButton());

    }
    public void exitButton(){
        Button btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(AddActivity.this, new MainActivity().getClass());
            startActivity(intent);
        });
    }
    private void selectPicture() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        getPhoto.launch(i);
    }

    private void takePicture() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        getPhoto.launch(i);
    }

    ActivityResultLauncher<Intent> getPhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    if (data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        System.out.println("Data got");
                        try {
                            picture = BitmapFactory.decodeStream(
                                    getContentResolver().openInputStream(uri)
                            );
                            imageView.setImageBitmap(picture);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void submitButton() {
        String answer = answerText.getText().toString();
        System.out.println("Added image with answer " + answer);
        if (picture != null && !answer.equals("")) {
            AnswersActivity.addQuestion(picture, answer);
        }
    }
}