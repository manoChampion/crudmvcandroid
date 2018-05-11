package com.example.luis.crudtrescamadas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Dog> {

    public Dog dog;
    public ListAdapter(Context context, List<Dog> dogs) { super (context, 0, dogs); }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        dog = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_layout, viewGroup, false);
        }

        TextView tvName = view.findViewById(R.id.tv_item_name);
        TextView tvSex = view.findViewById(R.id.tv_item_sex);
        TextView tvBreed = view.findViewById(R.id.tv_item_breed);
        TextView tvSize = view.findViewById(R.id.tv_item_size);
        ImageView imvImage = view.findViewById(R.id.imv_item);

        if (dog.image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(dog.image, 0, dog.image.length);
            imvImage.setImageBitmap(bitmap);
        } else {
            imvImage.setImageResource(R.drawable.ic_launcher_background);
        }

        tvName.setText(dog.name);
        tvSex.setText(dog.sex);
        tvBreed.setText(dog.breed);
        tvSize.setText(dog.size);

        return view;
    }


}
