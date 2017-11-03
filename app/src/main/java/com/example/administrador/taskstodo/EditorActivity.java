package com.example.administrador.taskstodo;

import android.app.DatePickerDialog;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class EditorActivity extends AppCompatActivity {

    Calendar pDay = Calendar.getInstance();

    EditText et_title;
    EditText et_desc;
    EditText et_web;
    EditText et_date;
    EditText et_imageS;
    EditText et_lat;
    EditText et_lon;
    ImageButton b_pDate;
    ImageButton b_location;
    LocationManager mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        b_pDate = findViewById(R.id.dateB);

        et_title = findViewById(R.id.taskTitleET);
        et_desc = findViewById(R.id.taskDescET);
        et_web = findViewById(R.id.taskWebPgET);
        et_date = findViewById(R.id.taskDateET);
        et_imageS = findViewById(R.id.taskImageSET);
        et_lat = findViewById(R.id.taskLatET);
        et_lon = findViewById(R.id.taskLonET);

        String text = et_title.getText().toString();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b_pDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog dateDialog = new DatePickerDialog(EditorActivity.this, pDateSetListener, 0, 0, 0);
                dateDialog.getDatePicker().setMinDate(pDay.getTimeInMillis());
                dateDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_task_item:

                finish();
                return true;
            case android.R.id.home:

                if (areEmptyFields(et_title.getText().toString(),
                        et_desc.getText().toString(),
                        et_web.getText().toString(),
                        et_date.getText().toString(),
                        et_imageS.getText().toString(),
                        et_lat.getText().toString(),
                        et_lon.getText().toString())){
                    MainActivity.newToast(EditorActivity.this,"hola");

            }

                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String fromDay = Integer.toString(dayOfMonth);
            String fromMonth = Integer.toString(monthOfYear + 1);
            if (fromDay.length() == 1) fromDay = "0" + fromDay;
            if (fromMonth.length() == 1) fromMonth = "0" + fromMonth;
            et_date.setText(fromDay + "/" + fromMonth + "/" + year);
        }
    };

    public boolean areEmptyFields (String msg1, String msg2, String msg3, String msg4, String msg5, String msg6, String msg7){
        if (msg1.isEmpty() || msg2.isEmpty() || msg3.isEmpty() || msg4.isEmpty() || msg5.isEmpty() || msg6.isEmpty() || msg7.isEmpty());
        return true;
    }

    private void addTask() {

        Task dummyTask = new Task(getString(R.string.dummy_task_title_txt),
                getString(R.string.dummy_task_description_txt),
                getString(R.string.dummy_task_date_txt),
                getString(R.string.dummy_task_webPage_txt),
                Double.parseDouble(getString(R.string.dummy_task_lat_txt)),
                Double.parseDouble(getString(R.string.dummy_task_lon_txt)),
                Boolean.parseBoolean(getString(R.string.dummy_task_isDone_txt)),
                Boolean.parseBoolean(getString(R.string.dummy_task_isUrgent_txt)),
                getString(R.string.dummy_task_imageS_txt));
        dummyTask.save();
        MainActivity.newToast(EditorActivity.this,getString(R.string.dummy_task_added_txt));

    }


}
