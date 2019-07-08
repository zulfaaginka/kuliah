
package com.example.kuliah;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView mListSemester;
    Button maddSemester;
    ArrayList<ModelSemester> mList;
    SemesterAdapter mAdapter = null;

    public static SQLiteHelper mSQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Semester");

        mListSemester = findViewById(R.id.ListSemester);
        maddSemester = findViewById(R.id.addSemester);

        //create database
        mSQLiteHelper = new SQLiteHelper(this,"KULIAHDB.sqlite",null,1);

        //insert table in database
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS SEMESTER (id INTEGER PRIMARY KEY AUTOINCREMENT, semester VARCHAR, tahun VARCHAR)");

        mList = new ArrayList<>();
        mAdapter = new SemesterAdapter(this,R.layout.row_semester,mList);
        mListSemester.setAdapter(mAdapter);

        final Cursor cursor = mSQLiteHelper.getData("SELECT * FROM SEMESTER");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String semester = cursor.getString(1);
            String tahun = cursor.getString(2);
            mList.add(new ModelSemester(id,semester,tahun));
        }
        mAdapter.notifyDataSetChanged();
        if(mList.size()==0){
            Toast.makeText(this,"No Record Found",Toast.LENGTH_SHORT).show();

        }

        mListSemester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = mSQLiteHelper.getData("SELECT id FROM SEMESTER");
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (c.moveToNext()){
                    arrID.add(c.getInt(0));
                }
                int id_semester = arrID.get(position);
                Intent intent = new Intent(MainActivity.this,MatkulList.class);
                intent.putExtra("id_semester",id_semester);
                startActivity(intent);
            }
        });

        mListSemester.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l){
                CharSequence[] items = {"Update", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Chose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0){
                            Cursor c = mSQLiteHelper.getData("SELECT id FROM SEMESTER");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while( c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogUpdate(MainActivity.this,arrID.get(position));
                        }
                        if (i == 1){
                            Cursor c = MainActivity.mSQLiteHelper.getData("SELECT id  FROM SEMESTER");
                            ArrayList<Integer> arrID =  new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });

                dialog.show();
                return true;
            }
        });


        maddSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InputSemesterActivity.class));
                finish();
            }
        });
    }

    private void showDialogDelete(final int idSemester) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MainActivity.this);
        dialogDelete.setTitle("Warning!!!");
        dialogDelete.setMessage("Are you sure to delete ?");
        dialogDelete.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    MainActivity.mSQLiteHelper.deleteData(idSemester);
                    Toast.makeText(MainActivity.this,"Delete Successfully",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error",e.getMessage());
                }
                updateSemesterList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void showDialogUpdate(Activity activity ,final int position){
        final Dialog dialog = new Dialog (activity);
        dialog.setContentView(R.layout.update_dialog);
        dialog.setTitle("Update");

        final EditText editSemester = dialog.findViewById(R.id.editSemester);
        final EditText editTahun = dialog.findViewById(R.id.editTahun);
        Button editBtn = dialog.findViewById(R.id.editBtn);

        Cursor c = mSQLiteHelper.getData("SELECT * FROM SEMESTER WHERE ID = " + position);
        while(c.moveToNext()){
            editSemester.setText(c.getString(1));
            editTahun.setText(c.getString(2));
        }
        //lebar dialog
        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        //tinggi dialog
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    MainActivity.mSQLiteHelper.updateData(
                            editSemester.getText().toString().trim(),
                            editTahun.getText().toString().trim(),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Update Berhasil",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Update Error",error.getMessage());
                }
                updateSemesterList();
            }
        });
    }
    private void updateSemesterList(){
        Cursor cursor = MainActivity.mSQLiteHelper.getData("SELECT * FROM SEMESTER");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String semester = cursor.getString(1);
            String tahun = cursor.getString(2);

            mList.add(new ModelSemester(id,semester,tahun));
        }
        mAdapter.notifyDataSetChanged();
    }
}