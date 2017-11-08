package com.example.administrador.taskstodo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ListAdapter extends ArrayAdapter<Task> {

    int layoutResourceId;
    Context context;
    List<Task> data;

    public ListAdapter(Context context, int layoutResourceId, List<Task> data) {

        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);
        TextView tv_taskDate = row.findViewById(R.id.taskDateRow);
        TextView tv_taskTitle = row.findViewById(R.id.taskNameRow);
        tv_taskDate.setText(data.get(position).getDate());
        tv_taskTitle.setText(data.get(position).getTitle());

        if (data.get(position).isUrgent())
            tv_taskTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));

        if (data.get(position).isDone()) {
           // tv_taskDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success, 0, 0, 0);


            tv_taskTitle.setTypeface(null, Typeface.BOLD);



            int imgResource = R.drawable.success;
            tv_taskDate.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);




        }
        return row;
    }
}