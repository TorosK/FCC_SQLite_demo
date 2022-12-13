package com.example.fcc_sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class UpdateReminderActivity extends AppCompatActivity {

    // variables for our edit text, button, strings and dbhandler class.
    private EditText reminderTitleEdit;
    private EditText reminderLevelEdit;
    private EditText reminderScannedCodeEdit;
    private Switch reminderImportanceSwitch;
    private Button updateReminderButton;
    private DataBaseHelper dataBaseHelper;
    int reminderID;
    String reminderTitle;
    int reminderLevel;
    String reminderScannedCode;
    boolean reminderImportance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reminder);

        // initializing all our variables.
        reminderTitleEdit = findViewById(R.id.idEdtReminderTitle);
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
        reminderLevel = getIntent().getIntExtra("Level", 0);
        reminderScannedCode = getIntent().getStringExtra("ScannedCode");
        reminderImportance = getIntent().getBooleanExtra("Important", false);

        // setting data to edit text
        // of our update activity.
        reminderTitleEdit.setText(reminderTitle);
        reminderLevelEdit.setText(Integer.toString(reminderLevel));
        reminderScannedCodeEdit.setText(reminderScannedCode);
        reminderImportanceSwitch.setChecked(reminderImportance);

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

                dataBaseHelper.updateReminder(reminderID, reminderTitleEdit.getText().toString(), Integer.parseInt(reminderLevelEdit.getText().toString()), reminderScannedCodeEdit.getText().toString(), reminderImportanceSwitch.isChecked());

                // displaying a toast message that our course has been updated.
                Toast.makeText(UpdateReminderActivity.this, "Påminnelse uppdaterad...", Toast.LENGTH_LONG).show();
                // Toast.makeText(UpdateReminderActivity.this, Boolean.parseBoolean(reminderImportanceEdit.getText().toString()) + "", Toast.LENGTH_LONG).show();

                // launching our main activity.
                Intent intent = new Intent(UpdateReminderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}