package com.example.fcc_sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd;
    Button buttonViewAll;
    EditText editTextName;
    EditText editTextBirthYear;
    Switch switchViewActiveCustomer;
    ListView listViewCustomerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonViewAll = findViewById(R.id.button_viewAll);
        editTextName = findViewById(R.id.editText_customerName);
        editTextBirthYear = findViewById(R.id.editText_birthYear);
        switchViewActiveCustomer = findViewById(R.id.switch_activeCustomer);
        listViewCustomerList = findViewById(R.id.listView_customerList);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerModel customerModel;
                try{
                    customerModel = new CustomerModel(-1, editTextName.getText().toString(), Integer.parseInt(editTextBirthYear.getText().toString()), switchViewActiveCustomer.isChecked());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    customerModel = new CustomerModel(-1, "error", 0, false);
                    Toast.makeText(MainActivity.this, "Error: Wrong input", Toast.LENGTH_SHORT).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this, "success: " + success, Toast.LENGTH_SHORT).show();

            }
        });

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "View All button", Toast.LENGTH_SHORT).show();
            }
        });
    }




}