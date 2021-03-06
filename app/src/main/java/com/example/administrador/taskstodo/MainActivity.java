package com.example.administrador.taskstodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mlist;
    List<Task> tasks;
    ListAdapter mAdapter;
    Task selectedTask;
    SharedPreferences prefs;

    public static void newToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("MyPreferencies", Context.MODE_PRIVATE);
        String userDetails = prefs.getString("userDetails", "");
        if (userDetails.equalsIgnoreCase("")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        mlist = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view_layout);
        mlist.setEmptyView(emptyView);
        tasks = Task.listAll(Task.class);
        mAdapter = new ListAdapter(this, R.layout.row, tasks);
        mlist.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                selectedTask = (Task) mlist.getItemAtPosition(position);
                intent.putExtra("selectedTask", selectedTask.getId());
                startActivity(intent);
            }
        });

        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                selectedTask = (Task) mlist.getItemAtPosition(position);
                if (selectedTask.isDone()) {
                    newToast(MainActivity.this, getString(R.string.task_is_done).replace("REPLACEMENT", selectedTask.getTitle().toString()));
                } else {
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle(R.string.confirmation_dialog_title);
                    alertDialogBuilder.setMessage(R.string.mark_done_dialog);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    selectedTask.setDone(true);
                                    selectedTask.save();
                                    newToast(MainActivity.this, getString(R.string.task_marked_done));
                                    refreshList(tasks, mAdapter);
                                }
                            })
                            .setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                return true;
            }
        });
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
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
                return true;
            case R.id.insert_dummy_item:
                addDummyTask();
                refreshList(tasks, mAdapter);
                return true;
            case R.id.delete_all_item:
                if (tasks.isEmpty()) {
                    newToast(MainActivity.this, getString(R.string.empty_tasks_list_txt));
                } else {
                    showDeleteConfirmationDialog();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDummyTask() {

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
        newToast(MainActivity.this, getString(R.string.dummy_task_added_txt));
    }

    private void deleteAllTasks() {
        newToast(this, getString(R.string.deleted_tasks_txt));
        Task.deleteAll(Task.class);
        refreshList(tasks, mAdapter);
    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_dialog_message);
        builder.setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAllTasks();
            }
        });
        builder.setNegativeButton(R.string.delete_dialog_button_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(getString(R.string.confirmation_dialog_title));
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        refreshList(tasks, mAdapter);
        super.onResume();
    }

    public void refreshList(List<Task> list, ListAdapter adapter) {
        list.clear();
        list.addAll(Task.listAll(Task.class));
        adapter.notifyDataSetChanged();

    }

}
