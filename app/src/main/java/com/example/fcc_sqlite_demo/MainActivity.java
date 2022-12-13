package com.example.fcc_sqlite_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {

    String reminderTitle;
    Button buttonAdd;
    Button buttonScan;
    EditText editTextTitle;
    EditText editTextDate;
    Switch switchViewReminderIsImportant;
    ListView listViewReminderList;
    ArrayAdapter reminderArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonScan = findViewById(R.id.buttonScan);
        editTextTitle = findViewById(R.id.editText_reminderTitle);
        editTextDate = findViewById(R.id.editText_reminderDate);
        switchViewReminderIsImportant = findViewById(R.id.switch_reminder_Is_Important);
        listViewReminderList = findViewById(R.id.listView_reminder_List);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        reminderTitle = getIntent().getStringExtra("scannedCode");
        editTextTitle.setText(reminderTitle);

        showRemindersOnListView(dataBaseHelper);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReminderModel reminderModel;
                try {
                    reminderModel = new ReminderModel(-1, editTextTitle.getText().toString(), Integer.parseInt(editTextDate.getText().toString()), switchViewReminderIsImportant.isChecked());
                    Toast.makeText(MainActivity.this, "Ny påminnelse: " + reminderModel.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    reminderModel = new ReminderModel(-1, "error", 0, false);
                    Toast.makeText(MainActivity.this, "Error: Wrong input", Toast.LENGTH_LONG).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(reminderModel);
                // Toast.makeText(MainActivity.this, "success: " + success, Toast.LENGTH_LONG).show();
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