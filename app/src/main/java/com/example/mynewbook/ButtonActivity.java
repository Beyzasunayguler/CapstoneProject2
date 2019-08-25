package com.example.mynewbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ButtonActivity extends AppCompatActivity {

    ImageView selectImageView;
    EditText bookNameText;
    EditText novelistText;
    EditText commentText;
    Button saveButton;
    SQLiteDatabase database;
    Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        Intent intent = getIntent();
        selectImageView = findViewById(R.id.selectImageView);
        bookNameText = findViewById(R.id.bookNameEditText);
        novelistText = findViewById(R.id.novelistEditText);
        commentText = findViewById(R.id.commentEditText);
        saveButton = findViewById(R.id.saveButton);

    }
    public void save (View view){

    }
    public void selectImage (View view){

        Intent imageIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntent,1);

    }
//eğer requestCode 1 ise bu işlemleri yap diye kontrol etmek için
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
