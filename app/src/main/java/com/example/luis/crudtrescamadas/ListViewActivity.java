package com.example.luis.crudtrescamadas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Serializable {

    ListView listView;
    Dog dog;
    DogDB dogDB;

    private String TAG = "listDogs_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        dog = new Dog();
        dogDB =  DogDB.getInstance(this);

        listView = findViewById(R.id.listviewview);
        listView.setOnItemClickListener(this);
        loadListView(dogDB.getAll());
    }

    public void loadListView(List<Dog> dogs) {
        ListAdapter listAdapter = new ListAdapter(this, dogs);
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadListView(dogDB.getAll());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Long code;

        dog = (Dog) parent.getAdapter().getItem(position);
        Log.d(TAG, dog.toString());

        Intent newOne = new Intent(this, EditDelete.class);
        newOne.putExtra("Object", dog);
        startActivity(newOne);


    }
}
