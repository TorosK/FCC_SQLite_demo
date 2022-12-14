package com.example.fcc_sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String reminderTitle;
    private String reminderDate;
    private String reminderTime;
    private int reminderLevel;
    private String reminderScannedCode;
    private boolean reminderImportant;

    private Button buttonAdd;
    private Button buttonScan;

    private EditText editTextTitle;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextLevel;
    private EditText editTextScannedCode;

    private Switch switchViewReminderIsImportant;

    private ListView listViewReminderList;

    private ArrayAdapter reminderArrayAdapter;

    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on below line we are initializing our variables.
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonScan = findViewById(R.id.buttonScan);
        editTextTitle = findViewById(R.id.editText_reminderTitle);
        editTextDate = findViewById(R.id.editText_reminderDate);
        editTextTime = findViewById(R.id.editText_reminderTime);
        editTextLevel = findViewById(R.id.editText_reminderLevel);
        editTextScannedCode = findViewById(R.id.editText_reminderScannedCode);
        switchViewReminderIsImportant = findViewById(R.id.switch_reminder_Is_Important);
        listViewReminderList = findViewById(R.id.listView_reminder_List);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        reminderScannedCode = getIntent().getStringExtra("scannedCode");
        editTextScannedCode.setText(reminderScannedCode);

        reminderTitle = getIntent().getStringExtra("Title_Scan_Code");
        editTextTitle.setText(reminderTitle);

        reminderDate = getIntent().getStringExtra("Date_Scan_Code");
        editTextDate.setText(reminderDate);

        reminderTime = getIntent().getStringExtra("Time_Scan_Code");
        editTextTime.setText(reminderTime);

        reminderLevel = getIntent().getIntExtra("Level_Scan_Code", -999);
        if (reminderLevel != -999) {
            editTextLevel.setText(String.valueOf(reminderLevel));
        }

        reminderImportant = getIntent().getBooleanExtra("Important_Scan_Code", false);
        // Toast.makeText(MainActivity.this, reminderImportant + "", Toast.LENGTH_LONG).show();
        // switchViewReminderIsImportant.isChecked();
        switchViewReminderIsImportant.setChecked(reminderImportant);

        showRemindersOnListView(dataBaseHelper);

        // on below line we are adding click listener
        // for our pick date button
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                editTextTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReminderModel reminderModel;

                System.out.println(editTextLevel);
                System.out.println(editTextLevel.getText());
                System.out.println(editTextLevel.getText().toString());
                // System.out.println(Integer.parseInt(editTextLevel.getText().toString()));
                // System.out.println(Integer.parseInt(editTextLevel.getText()));

                System.out.println(editTextLevel.getText().toString().equals(""));

                // To fix NumberException Error
                // java.lang.NumberFormatException: For input string: ""
                // editTextLevel.getText().toString() was "" when no input
                if (editTextLevel.getText().toString().equals("")) {
                    editTextLevel.setText("0");
                }

                try {
                    reminderModel = new ReminderModel(-1,
                            editTextTitle.getText().toString(),
                            editTextDate.getText().toString(),
                            editTextTime.getText().toString(),
                            Integer.parseInt(editTextLevel.getText().toString()),
                            editTextScannedCode.getText().toString(),
                            switchViewReminderIsImportant.isChecked());
                    System.out.println(reminderModel);
                    Toast.makeText(MainActivity.this, "Ny påminnelse: " + reminderModel, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    System.out.println(e);
                    reminderModel = new ReminderModel(-1,
                            "",
                            "",
                            "",
                            0,
                            "",
                            false);
                    System.out.println(reminderModel);
                    Toast.makeText(MainActivity.this, "Fel. Ingen input.", Toast.LENGTH_LONG).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(reminderModel);
                // Toast.makeText(MainActivity.this, "success: " + success, Toast.LENGTH_LONG).show();
                showRemindersOnListView(dataBaseHelper);

                editTextTitle.getText().clear();
                editTextDate.getText().clear();
                editTextTime.getText().clear();
                editTextLevel.getText().clear();
                editTextScannedCode.getText().clear();
                switchViewReminderIsImportant.setChecked(false);
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
        buttonScan.setOnClickListener(view -> {
            /* Previous functionality to View All
            dataBaseHelper = new DataBaseHelper(MainActivity.this);
            showRemindersOnListView(dataBaseHelper);
            Toast.makeText(MainActivity.this, dataBaseHelper.getAll().toString(), Toast.LENGTH_LONG).show();
            */

            // When button is pressed, switch over to new fragment / activity for camera
            // Allow scanning of barcodes, QR-codes with camera
            // Save values from barcode / QR codes
            // Show it in a TOAST message / system.out
            // Populate title input field with value

            // Switch to ZXing activity
            // on below line we are calling an intent.
            Intent intent = new Intent(MainActivity.this, ZXingActivity.class);

            // below we are passing all our values.
            intent.putExtra("Title_Scan_Code", editTextTitle.getText().toString());
            reminderTitle = editTextTitle.getText().toString();

            intent.putExtra("Date_Scan_Code", editTextDate.getText().toString());
            reminderDate = editTextDate.getText().toString();

            intent.putExtra("Time_Scan_Code", editTextTime.getText().toString());
            reminderTime = editTextTime.getText().toString();

            // Toast.makeText(MainActivity.this, "titel: " + reminderTitle, Toast.LENGTH_LONG).show();

            if (editTextLevel.getText().toString().equals("") == false) {
                intent.putExtra("Level_Scan_Code", Integer.parseInt(editTextLevel.getText().toString()));
                reminderLevel = Integer.parseInt(editTextLevel.getText().toString());
            }

            // Toast.makeText(MainActivity.this, "våning: " + reminderLevel, Toast.LENGTH_LONG).show();

            intent.putExtra("Important_Scan_Code", switchViewReminderIsImportant.isChecked());
            reminderImportant = switchViewReminderIsImportant.isChecked();

            // Toast.makeText(MainActivity.this, "viktigt: " + reminderImportant, Toast.LENGTH_LONG).show();

            // starting our activity.
            startActivity(intent);
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

                                ReminderModel clickedReminder = (ReminderModel) adapterView.getItemAtPosition(position);

                                // on below line we are calling an intent.
                                Intent intent = new Intent(MainActivity.this, UpdateReminderActivity.class);
                                // below we are passing all our values.
                                intent.putExtra("ID", clickedReminder.getId());
                                intent.putExtra("Title", clickedReminder.getTitle());
                                intent.putExtra("Date", clickedReminder.getDate());
                                intent.putExtra("Time", clickedReminder.getTime());
                                intent.putExtra("Level", clickedReminder.getLevel());
                                intent.putExtra("ScannedCode", clickedReminder.getScannedCode());
                                intent.putExtra("Important", clickedReminder.isImportant());
                                // starting our activity.
                                startActivity(intent);
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

    private void showRemindersOnListView(DataBaseHelper dataBaseHelper) {
        reminderArrayAdapter = new ArrayAdapter<ReminderModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAll());
        listViewReminderList.setAdapter(reminderArrayAdapter);
    }
}