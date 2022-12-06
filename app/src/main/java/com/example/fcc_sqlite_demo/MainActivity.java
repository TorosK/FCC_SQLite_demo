package com.example.fcc_sqlite_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd;
    Button buttonViewAll;
    EditText editTextTitle;
    EditText editTextDate;
    Switch switchViewReminderIsImportant;
    ListView listViewReminderList;
    ArrayAdapter reminderArrayAdapter;
    DataBaseHelper dataBaseHelper;
    private Context context;
    private ArrayList<CourseModal> courseModalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonViewAll = findViewById(R.id.button_viewAll);
        editTextTitle = findViewById(R.id.editText_reminderTitle);
        editTextDate = findViewById(R.id.editText_reminderDate);
        switchViewReminderIsImportant = findViewById(R.id.switch_reminder_Is_Important);
        listViewReminderList = findViewById(R.id.listView_reminder_List);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        showRemindersOnListView(dataBaseHelper);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReminderModel reminderModel;
                try {
                    reminderModel = new ReminderModel(-1, editTextTitle.getText().toString(), Integer.parseInt(editTextDate.getText().toString()), switchViewReminderIsImportant.isChecked());
                    Toast.makeText(MainActivity.this, reminderModel.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    reminderModel = new ReminderModel(-1, "error", 0, false);
                    Toast.makeText(MainActivity.this, "Error: Wrong input", Toast.LENGTH_LONG).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(reminderModel);
                Toast.makeText(MainActivity.this, "success: " + success, Toast.LENGTH_LONG).show();
                showRemindersOnListView(dataBaseHelper);

            }
        });

        /*
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "View All button", Toast.LENGTH_SHORT).show();
            }
        });
        */
        buttonViewAll.setOnClickListener(view -> {
            dataBaseHelper = new DataBaseHelper(MainActivity.this);

            showRemindersOnListView(dataBaseHelper);

            Toast.makeText(MainActivity.this, dataBaseHelper.getAll().toString(), Toast.LENGTH_LONG).show();
        });

        /*
        // DELETE ON CLICK
        listViewReminderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReminderModel clickedReminder = (ReminderModel) adapterView.getItemAtPosition(i);
                dataBaseHelper.deleteOne(clickedReminder);
                showRemindersOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "DELETED " + clickedReminder.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        */

        listViewReminderList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                new AlertDialog.Builder(MainActivity.this).
                        setTitle("Vad vill du göra?").

                        // Edit option
                                setNeutralButton("Ändra", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Save long-pressed reminders 3 values
                                // Switch to New Update Fragment / Activity
                                // Pre-populate three input fields
                                // Save Button underneath

                                // @Override
                                // public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                                    // on below line we are setting data
                                    // to our views of recycler view item.
                                    CourseModal modal = courseModalArrayList.get(position);
                                    holder.courseNameTV.setText(modal.getCourseName());
                                    holder.courseDescTV.setText(modal.getCourseDescription());
                                    holder.courseDurationTV.setText(modal.getCourseDuration());
                                    holder.courseTracksTV.setText(modal.getCourseTracks());

                                // below line is to add on click listener for our recycler view item.
                                // holder.itemView.setOnClickListener(new View.OnClickListener() {
                                // @Override
                                // public void onClick(View v) {

                                // on below line we are calling an intent.
                                Intent intent = new Intent(context, UpdateReminderActivity.class);

                                // below we are passing all our values.
                                intent.putExtra("name", modal.getCourseName());
                                intent.putExtra("description", modal.getCourseDescription());
                                intent.putExtra("duration", modal.getCourseDuration());
                                intent.putExtra("tracks", modal.getCourseTracks());

                                // starting our activity.
                                context.startActivity(i);
                            // }
                        // });
            }
        }).

                // Abort
                        setPositiveButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).

                // Delete the long-pressed reminder
                        setNegativeButton("Radera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ReminderModel clickedReminder = (ReminderModel) adapterView.getItemAtPosition(position);
                        dataBaseHelper.deleteOne(clickedReminder);
                        showRemindersOnListView(dataBaseHelper);
                        reminderArrayAdapter.notifyDataSetChanged();

                        // Below caused error
                        // Toast.makeText(MainActivity.this, "DELETED " + clickedReminder.toString(), Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
        return false;
    }
});
        }

private void showRemindersOnListView(DataBaseHelper dataBaseHelper){
        reminderArrayAdapter=new ArrayAdapter<ReminderModel>(MainActivity.this,android.R.layout.simple_list_item_1,dataBaseHelper.getAll());
        listViewReminderList.setAdapter(reminderArrayAdapter);
        }
        }