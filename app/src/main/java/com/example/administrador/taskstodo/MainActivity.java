package com.example.administrador.taskstodo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button ejemplo;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task_item:
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
                return true;
            case R.id.insert_dummy_item:
                addDummyItem();
                return true;
            case R.id.delete_all_item:
                deleteAllTasks();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void newToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    private void addDummyItem() {
        newToast(this, getString(R.string.dummy_task_added_txt));
    }

    private void deleteAllTasks() {
        newToast(this, getString(R.string.deleted_tasks_txt));
    }
}
