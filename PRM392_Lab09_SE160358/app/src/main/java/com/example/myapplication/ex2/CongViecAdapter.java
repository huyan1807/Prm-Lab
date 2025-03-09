package com.example.myapplication.ex2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;


public class CongViecAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CongViec> congViecs;

    public CongViecAdapter(Context context, int layout, List<CongViec> congViecs) {
        this.context = context;
        this.layout = layout;
        this.congViecs = congViecs;
    }

    @Override
    public int getCount() {
        return congViecs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = convertView.findViewById(R.id.textViewTen);
            holder.imgDelete = convertView.findViewById(R.id.imageViewDelete);
            holder.imgEdit = convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CongViec congViec = congViecs.get(position);
        holder.txtTen.setText(congViec.getTen());
        return convertView;
    }

}