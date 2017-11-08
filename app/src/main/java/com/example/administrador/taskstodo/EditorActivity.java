package com.example.administrador.taskstodo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class EditorActivity extends AppCompatActivity implements LocationListener {

    Calendar pDay = Calendar.getInstance();

    EditText et_title;
    EditText et_desc;
    EditText et_web;
    EditText et_date;
    EditText et_imageS;
    EditText et_lat;
    EditText et_lon;
    TextView tv_LocNA;
    CheckBox cb_urgent;
    ImageButton b_pDate;
    ImageButton b_location;
    LocationManager mLocationManager;
    Location retrieved_location;
    int MY_PERMISSIONS_REQUEST_LOCATION = 1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editorActivity_title);

        b_pDate = findViewById(R.id.dateB);
        b_location = findViewById(R.id.locationB);
        tv_LocNA = findViewById(R.id.locNotAvailableTV);
        et_title = findViewById(R.id.taskTitleET);
        et_desc = findViewById(R.id.taskDescET);
        et_web = findViewById(R.id.taskWebPgET);
        et_date = findViewById(R.id.taskDateET);
        et_imageS = findViewById(R.id.taskImageSET);
        et_lat = findViewById(R.id.taskLatET);
        et_lon = findViewById(R.id.taskLonET);
        cb_urgent = findViewById(R.id.isUrgentCB);

        tv_LocNA.setVisibility(View.GONE);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(EditorActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, EditorActivity.this);
            //           retrieved_location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            b_location.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (retrieved_location == null) {
                        tv_LocNA.setVisibility(view.VISIBLE);
                    } else {
                        et_lat.setText(String.valueOf(retrieved_location.getLatitude()));
                        et_lon.setText(String.valueOf(retrieved_location.getLongitude()));
                        tv_LocNA.setVisibility(view.GONE);
                    }
                }
            });

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }


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
                if (et_title.getText().toString().trim().equalsIgnoreCase("") ||
                        et_desc.getText().toString().trim().equalsIgnoreCase("") ||
                        et_web.getText().toString().trim().equalsIgnoreCase("") ||
                        et_date.getText().toString().trim().equalsIgnoreCase("") ||
                        et_imageS.getText().toString().trim().equalsIgnoreCase("") ||
                        et_lat.getText().toString().trim().equalsIgnoreCase("") ||
                        et_lon.getText().toString().trim().equalsIgnoreCase("")) {
                    MainActivity.newToast(EditorActivity.this, getString(R.string.empty_fields));
                } else {
                    addTask();
                    finish();
                }
                return true;
            case android.R.id.home:
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

    private void addTask() {

        Task newTask = new Task(et_title.getText().toString().trim(),
                et_desc.getText().toString().trim(),
                et_date.getText().toString().trim(),
                et_web.getText().toString().trim(),
                Double.parseDouble(et_lat.getText().toString().trim()),
                Double.parseDouble(et_lon.getText().toString().trim()),
                Boolean.parseBoolean(getString(R.string.dummy_task_isDone_txt)),
                isItUrgent(),
                et_imageS.getText().toString().trim());
        newTask.save();
        MainActivity.newToast(EditorActivity.this, getString(R.string.new_task_added_txt));
    }

    public boolean isItUrgent() {
        if (cb_urgent.isChecked()) return true;
        return false;
    }


    @Override
    public void onLocationChanged(Location location) {
        retrieved_location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v(getString(R.string.app_name), "onRequestPermissionsResult");
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                }
            }
        }
    }


}


