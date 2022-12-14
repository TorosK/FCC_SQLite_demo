package com.example.fcc_sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateReminderActivity extends AppCompatActivity {

    // variables for our edit text, button, strings and dbhandler class.
    private EditText reminderTitleEdit;
    private EditText reminderDateEdit;
    private EditText reminderTimeEdit;
    private EditText reminderLevelEdit;
    private EditText reminderScannedCodeEdit;

    private Switch reminderImportanceSwitch;

    private Button updateReminderButton;

    private DataBaseHelper dataBaseHelper;

    private int reminderID;
    private String reminderTitle;
    private String reminderDate;
    private String reminderTime;
    private int reminderLevel;
    private String reminderScannedCode;
    private boolean reminderImportance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reminder);

        // initializing all our variables.
        reminderTitleEdit = findViewById(R.id.idEdtReminderTitle);
        reminderDateEdit = findViewById(R.id.idEdtReminderDate);
        reminderTimeEdit = findViewById(R.id.idEdtReminderTime);
        reminderLevelEdit = findViewById(R.id.idEdtReminderLevel);
        reminderScannedCodeEdit = findViewById(R.id.idEdtReminderScannedCode);
        reminderImportanceSwitch = findViewById(R.id.switch_reminder_Is_Important);
        updateReminderButton = findViewById(R.id.idBtnUpdateReminder);

        // on below line we are initialing our dbhandler class.
        dataBaseHelper = new DataBaseHelper(UpdateReminderActivity.this);

        // on below lines we are getting data which
        // we passed in our adapter class.
        reminderID = getIntent().getIntExtra("ID", 0);
        reminderTitle = getIntent().getStringExtra("Title");
        reminderDate = getIntent().getStringExtra("Date");
        reminderTime = getIntent().getStringExtra("Time");
        reminderLevel = getIntent().getIntExtra("Level", 0);
        reminderScannedCode = getIntent().getStringExtra("ScannedCode");
        reminderImportance = getIntent().getBooleanExtra("Important", false);

        // setting data to edit text
        // of our update activity.
        reminderTitleEdit.setText(reminderTitle);
        reminderDateEdit.setText(reminderDate);
        reminderTimeEdit.setText(reminderTime);
        reminderLevelEdit.setText(Integer.toString(reminderLevel));
        reminderScannedCodeEdit.setText(reminderScannedCode);
        reminderImportanceSwitch.setChecked(reminderImportance);

        // on below line we are adding click listener
        // for our pick date button
        reminderDateEdit.setOnClickListener(new View.OnClickListener() {
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
                        UpdateReminderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                reminderDateEdit.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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

        // on below line we are adding click
        // listener for our pick date button
        reminderTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateReminderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                reminderTimeEdit.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        // adding on click listener to our update course button.
        updateReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inside this method we are calling an update course
                // method and passing all our edit text values.
                // System.out.println("reminderImportanceEdit" + reminderImportanceEdit);
                // System.out.println("reminderImportanceEdit.getText()" + reminderImportanceEdit.getText());
                // System.out.println("reminderImportanceEdit.getText().toString()" + reminderImportanceEdit.getText().toString());
                // System.out.println("Boolean.parseBoolean(reminderImportanceEdit.getText().toString())" + Boolean.parseBoolean(reminderImportanceEdit.getText().toString()));

                dataBaseHelper.updateReminder(reminderID, reminderTitleEdit.getText().toString(), reminderDateEdit.getText().toString(), reminderTimeEdit.getText().toString(), Integer.parseInt(reminderLevelEdit.getText().toString()), reminderScannedCodeEdit.getText().toString(), reminderImportanceSwitch.isChecked());

                // displaying a toast message that our course has been updated.
                Toast.makeText(UpdateReminderActivity.this, "P??minnelse uppdaterad...", Toast.LENGTH_LONG).show();
                // Toast.makeText(UpdateReminderActivity.this, Boolean.parseBoolean(reminderImportanceEdit.getText().toString()) + "", Toast.LENGTH_LONG).show();

                // launching our main activity.
                Intent intent = new Intent(UpdateReminderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}