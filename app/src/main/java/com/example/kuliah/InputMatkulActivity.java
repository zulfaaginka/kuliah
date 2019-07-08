package com.example.kuliah;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.kuliah.MainActivity.mSQLiteHelper;

public class InputMatkulActivity extends AppCompatActivity {
    EditText mNamaMatkul,mDosen,mJam,mRuangan;
    Button mAddMatkulBtn;
    Spinner mHari;
    static String hari;


    public static SQLiteHelper mSQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_matkul);

        Intent matkulIntent = getIntent();
        final int id_semester = matkulIntent.getIntExtra("id_semester",0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Matkul");

        mNamaMatkul = findViewById(R.id.nama_matkul);
        mDosen = findViewById(R.id.dosen);
        mJam = findViewById(R.id.jam);
        mRuangan = findViewById(R.id.ruangan);
        mAddMatkulBtn = findViewById(R.id.addMatkulBtn);
        mHari = (Spinner) findViewById(R.id.hari);

        mSQLiteHelper = new SQLiteHelper(this,"KULIAHDB.sqlite",null,1);

        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MATKUL (id INTEGER PRIMARY KEY AUTOINCREMENT, id_semester INTEGER, nama_matkul VARCHAR, dosen VARCHAR, hari INTEGER, jam VARCHAR, ruangan VARCHAR)");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(InputMatkulActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.hari));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHari.setAdapter(myAdapter);
        ArrayList<String> list = new ArrayList<String>();

        mHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMatkulActivity.hari = mHari.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAddMatkulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mSQLiteHelper.insertMatkul(
                            id_semester,
                            mNamaMatkul.getText().toString().trim(),
                            mDosen.getText().toString().trim(),
                            mJam.getText().toString().trim(),
                            mRuangan.getText().toString().trim(),
                            hari

                    );
                    Toast.makeText(InputMatkulActivity.this, "Added Succesfully",Toast.LENGTH_SHORT).show();
                    mNamaMatkul.setText("");
                    mDosen.setText("");
                    mJam.setText("");
                    mRuangan.setText("");
                    Intent intent = new Intent(InputMatkulActivity.this, MatkulList.class);
                    intent.putExtra("id_semester",id_semester);
                    startActivity(intent);
                    finish();
                    }
                    catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void onBackPressed()
    {
        Intent matkulIntent = getIntent();
        final int id_semester = matkulIntent.getIntExtra("id_semester",0);
        super.onBackPressed();
        Intent intent = new Intent(InputMatkulActivity.this, MatkulList.class);
        intent.putExtra("id_semester",id_semester);
        startActivity(intent);
        finish();

    }
}
