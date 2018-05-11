package com.example.luis.crudtrescamadas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String[] items = new String[]{"ADD", "LIST", "EXIT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            }

            case 1: {
                Intent intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                break;
            }

            case 2: {
                finish();
                break;
            }
        }
    }
}
