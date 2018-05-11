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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class EditDelete extends AppCompatActivity {

    public EditText name, sex, breed, size;
    TextView aliasID;
    Button btn_submit, btn_delete;
    public ImageView imageView;
    private byte[] image = null;
    private DogDB dogDB;
    private Dog dog;
    private String TAG = "editTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        dog = new Dog();
        dogDB = DogDB.getInstance(this);

        name = findViewById(R.id.input_name);
        sex = findViewById(R.id.input_sex);
        breed = findViewById(R.id.input_breed);
        size = findViewById(R.id.input_size);

        imageView = findViewById(R.id.imvImage);

        aliasID = findViewById(R.id.tv_id);

        btn_submit = findViewById(R.id.btn_submit);
        btn_delete = findViewById(R.id.btn_delete);
    }
    
    public void save(View view){
        if (!name.getText().toString().isEmpty() && !sex.getText().toString().isEmpty() && !breed.getText().toString().isEmpty() && !size.getText().toString().isEmpty()) {
            dog._id = Long.parseLong(aliasID.getText().toString());
            dog.name = name.getText().toString();
            dog.sex = breed.getText().toString();
            dog.breed = breed.getText().toString();
            dog.size = size.getText().toString();
            dog.image = image;
            
            Log.d(TAG, "Dog: " + dog.toString());
            
            dogDB.create(dog);
        } else {
            Toast.makeText(getBaseContext(), "Complrete all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view) {
        dog._id = Long.parseLong(aliasID.getText().toString());

        Log.d(TAG, "Dog: " + dog.toString());

        dogDB.delete(dog);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        Dog dog = (Dog) intent.getSerializableExtra("Object");
        aliasID.setText(dog._id.toString());

        name.setText(dog.name);
        sex.setText(dog.sex);
        breed.setText(dog.breed);
        size.setText(dog.size);
        if (dog.image != null) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(dog.image, 0, dog.image.length));
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
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
}
