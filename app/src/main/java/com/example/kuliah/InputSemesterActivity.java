package com.example.kuliah;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputSemesterActivity extends AppCompatActivity {

    EditText msemester,mtahun;
    Button maddBtn;

    public static SQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_semester);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Semester");

        msemester = findViewById(R.id.semester);
        mtahun = findViewById(R.id.tahun);
        maddBtn = findViewById(R.id.addBtn);

        //create database
        mSQLiteHelper = new SQLiteHelper(this,"KULIAHDB.sqlite",null,1);

        //insert table in database
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS SEMESTER (id INTEGER PRIMARY KEY AUTOINCREMENT, semester VARCHAR, tahun VARCHAR)");

        maddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mSQLiteHelper.insertData(
                            msemester.getText().toString().trim(),
                            mtahun.getText().toString().trim()
                    );
                    Toast.makeText(InputSemesterActivity.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
//                    msemester.setText("");
//                    mtahun.setText("");
                    startActivity(new Intent(InputSemesterActivity.this, MainActivity.class));
                    finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(InputSemesterActivity.this, MainActivity.class));
        finish();

    }
}
