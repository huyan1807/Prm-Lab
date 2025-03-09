package com.example.myapplication.ex2;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;

public class CustomImageAdapter extends ArrayAdapter<Integer> {
    private Context context;
    private List<Integer> imageResources;

    public CustomImageAdapter(Context context, List<Integer> imageResources) {
        super(context, R.layout.list_item_image, imageResources);
        this.context = context;
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_image, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageItem);
        imageView.setImageResource(imageResources.get(position));

        return convertView;
    }
}
