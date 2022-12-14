package com.example.fcc_sqlite_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ZXingActivity extends AppCompatActivity {

    private String reminderTitle;
    private String reminderDate;
    private String reminderTime;
    private int reminderLevel;
    private boolean reminderImportance;

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);

        // ZXing camera
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ZXingActivity.this, result.getText(), Toast.LENGTH_LONG).show();

                        reminderTitle = getIntent().getStringExtra("Title_Scan_Code");
                        reminderDate = getIntent().getStringExtra("Date_Scan_Code");
                        reminderTime = getIntent().getStringExtra("Time_Scan_Code");
                        reminderLevel = getIntent().getIntExtra("Level_Scan_Code", -999);
                        reminderImportance = getIntent().getBooleanExtra("Important_Scan_Code", false);

                        // Toast.makeText(ZXingActivity.this, "titel: " + reminderTitle + " | våning: " + reminderLevel + " | viktigt: " + reminderImportance, Toast.LENGTH_LONG).show();

                        // Save value (scanned code) to send to main activity
                        // Switch back to main activity
                        // on below line we are calling an intent.
                        Intent intentZXing = new Intent(ZXingActivity.this, MainActivity.class);
                        // below we are passing all our values.
                        intentZXing.putExtra("scannedCode", result.getText());

                        intentZXing.putExtra("Title_Scan_Code", reminderTitle);
                        intentZXing.putExtra("Date_Scan_Code", reminderDate);
                        intentZXing.putExtra("Time_Scan_Code", reminderTime);
                        intentZXing.putExtra("Level_Scan_Code", reminderLevel);
                        intentZXing.putExtra("Important_Scan_Code", reminderImportance);

                        // starting our activity.
                        startActivity(intentZXing);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    // ZXing
    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }
    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}