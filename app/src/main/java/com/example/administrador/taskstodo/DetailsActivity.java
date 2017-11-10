package com.example.administrador.taskstodo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.CheckBox;
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
    CheckBox cb_isUrgent;
    Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle(R.string.detailsActivity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        long id = getIntent().getLongExtra("selectedTask", 0);
        currentTask = Task.findById(Task.class, id);
        tv_title_task = findViewById(R.id.taskTitleTV);
        tv_date_task = findViewById(R.id.taskDateTV);
        tv_desc_task = findViewById(R.id.taskDescTV);
        tv_imageS_task = findViewById(R.id.taskImageSTV);
        tv_web_task = findViewById(R.id.taskWebPgTV);
        tv_loc_task = findViewById(R.id.taskLocTV);
        im_task = findViewById(R.id.taskImageIV);
        b_open_map = findViewById(R.id.openMapB);
        cb_isUrgent = findViewById(R.id.isUrgentCB);
        cb_isUrgent.setClickable(false);
        tv_title_task.setText(currentTask.getTitle().toString());
        tv_date_task.setText(currentTask.getDate().toString());
        tv_desc_task.setText(currentTask.getDescription().toString());
        tv_imageS_task.setText(currentTask.getImageSource().toString());
        tv_web_task.setText(currentTask.getWebPage().toString());
        tv_loc_task.setText(currentTask.getLatitude() + currentTask.getLongitude().toString());
        tv_loc_task.setText(currentTask.getLatitude() + currentTask.getLongitude().toString());
        Picasso.with(this).load(tv_imageS_task.getText().toString()).placeholder(this.getResources().getDrawable(R.drawable.no_image)).error(this.getResources().getDrawable(R.drawable.no_image)).into(im_task);
        if (currentTask.isUrgent()) {
            cb_isUrgent.setChecked(true);
        }

        if (currentTask.isDone()) {
            tv_date_task.setTextColor(ContextCompat.getColor(DetailsActivity.this, R.color.colorGreen));
        }

        b_open_map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(DetailsActivity.this, MapsActivity.class);
                intent.putExtra("currentTask", currentTask);
                startActivity(intent);
            }
        });

        tv_web_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String final_url = tv_web_task.getText().toString().trim();
                String search_url = "https://www.google.es/search?q=";
                if (URLUtil.isValidUrl(final_url)) {
                    Uri uri = Uri.parse(final_url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    final_url = search_url.concat(final_url);
                    Uri uri = Uri.parse(final_url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            }
        });

        tv_imageS_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String final_url = tv_imageS_task.getText().toString().trim();
                String search_url = "https://www.google.es/search?q=";
                if (URLUtil.isValidUrl(final_url)) {
                    Uri uri = Uri.parse(final_url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    final_url = search_url.concat(final_url);
                    Uri uri = Uri.parse(final_url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_task_item:
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(DetailsActivity.this);
                alertDialogBuilder.setTitle(R.string.confirmation_dialog_title);
                alertDialogBuilder.setMessage(R.string.delete_one_dialog_message);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                deleteCurrentTask();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteCurrentTask() {
        if (currentTask != null) {
            currentTask.delete();
            MainActivity.newToast(DetailsActivity.this, getString(R.string.deleted_task_txt).replace("REPLACEMENT", currentTask.getTitle().toString()));
        }
    }
}
