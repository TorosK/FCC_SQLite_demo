package com.example.fcc_sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;

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
                try{
                    reminderModel = new ReminderModel(-1, editTextTitle.getText().toString(), Integer.parseInt(editTextDate.getText().toString()), switchViewReminderIsImportant.isChecked());
                    Toast.makeText(MainActivity.this, reminderModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    reminderModel = new ReminderModel(-1, "error", 0, false);
                    Toast.makeText(MainActivity.this, "Error: Wrong input", Toast.LENGTH_SHORT).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(reminderModel);
                Toast.makeText(MainActivity.this, "success: " + success, Toast.LENGTH_SHORT).show();
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

            Toast.makeText(MainActivity.this, dataBaseHelper.getAll().toString(), Toast.LENGTH_SHORT).show();
        });

        listViewReminderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReminderModel clickedReminder = (ReminderModel) adapterView.getItemAtPosition(i);
                dataBaseHelper.deleteOne(clickedReminder);
                showRemindersOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "DELETED " + clickedReminder.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showRemindersOnListView(DataBaseHelper dataBaseHelper) {
        reminderArrayAdapter = new ArrayAdapter<ReminderModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAll());
        listViewReminderList.setAdapter(reminderArrayAdapter);
    }
}