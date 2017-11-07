package com.example.administrador.taskstodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {


    TextView tv_title_task;
    TextView tv_date_task;
    TextView tv_desc_task;
    TextView tv_imageS_task;
    TextView tv_web_task;
    TextView tv_loc_task;
    ImageView im_task;
    ImageButton b_open_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Task currentTask = (Task) getIntent().getSerializableExtra("selectedTask");

        tv_title_task = findViewById(R.id.taskTitleTV);
        tv_date_task = findViewById(R.id.taskDateTV);
        tv_desc_task = findViewById(R.id.taskDescTV);
        tv_imageS_task = findViewById(R.id.taskImageSTV);
        tv_web_task = findViewById(R.id.taskWebPgTV);
        tv_loc_task = findViewById(R.id.taskLocTV);
        im_task= findViewById(R.id.taskImageIV);
        b_open_map = findViewById(R.id.openMapB);

        tv_title_task.setText(currentTask.getTitle().toString());
        tv_date_task.setText(currentTask.getDate().toString());
        tv_desc_task.setText(currentTask.getDescription().toString());
        tv_imageS_task.setText(currentTask.getImageSource().toString());
        tv_web_task.setText(currentTask.getWebPage().toString());
        tv_loc_task.setText(currentTask.getLatitude()+currentTask.getLongitude().toString());


        Picasso.with(this).load(tv_imageS_task.getText().toString()).into(im_task);


        b_open_map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });


    }
}