package com.example.mynewbook;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ButtonActivity extends AppCompatActivity {

    ImageView selectImageView;
    EditText bookNameText;
    EditText novelistText;
    EditText commentText;
    Button saveButton;
    String book;
    String comment;
    String novelist;
    String bookImage;
    int id;
    Bitmap selectedImage;
    AppDatabase database;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectImageView = findViewById(R.id.selectImageView);
        bookNameText = findViewById(R.id.bookNameEditText);
        novelistText = findViewById(R.id.novelistEditText);
        commentText = findViewById(R.id.commentEditText);
        saveButton = findViewById(R.id.saveButton);


    }

    public void save(View view) {
        novelist = novelistText.getText().toString();
        comment = commentText.getText().toString();
        book = bookNameText.getText().toString();
        database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();

        database.getBookDao().insertBook(
                new Book(book, comment, novelist, bookImage, id, false,0)
        );
        finish();
    }

    public void selectImage(View view) {

        //kullanıcı iznini kontrol etmek için

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

        } else {
            Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(imageIntent, 1);
        }


    }

    //eğer requestCode 1 ise bu işlemleri yap diye kontrol etmek için
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //kontrolleri yapacağımız bir if durumu oluşturuyoruz. requestcode verdiğimiz değerde ise , sonuçta bır verimiz varsa ve data boş değilse şeklinde.
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri selectedImageURI = data.getData();
            bookImage = getRealPathFromURI(selectedImageURI);
            Picasso.get().load(new File(bookImage)).into(selectImageView);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    //kullanıcıdan görseli izin aldık,bunu her seferinde izin almamak için yeni bir kod oluşturuyoruz


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent, 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
