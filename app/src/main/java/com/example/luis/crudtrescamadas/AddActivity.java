package com.example.luis.crudtrescamadas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import javax.xml.transform.dom.DOMLocator;

public class AddActivity extends AppCompatActivity implements AdapterView.OnClickListener {

    public EditText name, sex, breed, size;
    public ImageView imageView;
    private byte[] image;
    Button btn_submit, btn_back;
    private DogDB dogDB;
    private Dog dog;
    private static String TAG = "listDogs_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dog = new Dog();
        dogDB = dogDB.getInstance(this);

        name = findViewById(R.id.input_name);
        sex = findViewById(R.id.input_sex);
        breed = findViewById(R.id.input_breed);
        size = findViewById(R.id.input_size);
        imageView = findViewById(R.id.imageView);

        btn_back = findViewById(R.id.btn_back);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !sex.getText().toString().isEmpty() && !breed.getText().toString().isEmpty() && !size.getText().toString().isEmpty()) {
                    if (dog._id == null) {
                        dog = new Dog();
                    }

                    dog.name = name.getText().toString();
                    dog.sex = sex.getText().toString();
                    dog.breed = breed.getText().toString();
                    dog.size = size.getText().toString();
                    dog.image = image;

                    Log.d(TAG, "Dog: " + dog.toString());
                    dogDB.create(dog);
                } else {
                    Toast.makeText(getBaseContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select the image"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            Log.d(TAG, "Image URI: " + uri);
            imageView.setImageURI(uri);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                byte[] img = getBitmapAsByteArray(bitmap);
                image = img;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onClick(View v) {

    }
}
