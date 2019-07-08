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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.kuliah.MainActivity.mSQLiteHelper;

public class MatkulList extends AppCompatActivity {
    Button mAddMatkul;
    ListView ListMatkul;
    ArrayList<ModelMatkul> matkulList;
    MatkulAdapter matkulAdapter=null;
    static String hari;
    static int id_semester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul_list);

        Intent matkulIntent = getIntent();
        id_semester = matkulIntent.getIntExtra("id_semester",0);
        String title = Integer.toString(id_semester);

        mAddMatkul = findViewById(R.id.addMatkul);
        ListMatkul = (ListView) findViewById(R.id.ListMatkul);

        mSQLiteHelper = new SQLiteHelper(this,"KULIAHDB.sqlite",null,1);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MATKUL (id INTEGER PRIMARY KEY AUTOINCREMENT, id_semester INTEGER, nama_matkul VARCHAR, dosen VARCHAR, hari VARCHAR, jam VARCHAR, ruangan VARCHAR)");

        String sql = "SELECT * from SEMESTER WHERE id= " + id_semester;
        Cursor c = mSQLiteHelper.getData(sql);
        while(c.moveToNext()){
            title =  c.getString(1);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);

        matkulList = new ArrayList<>();
        matkulAdapter = new MatkulAdapter(this,R.layout.row_matkul,matkulList);
        ListMatkul.setAdapter(matkulAdapter);

        final Cursor cursor = mSQLiteHelper.getData("SELECT * FROM MATKUL where id_semester= " + id_semester );
        matkulList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            int id_sem = cursor.getInt(1);
            String nama_matkul = cursor.getString(2);
            String dosen = cursor.getString(3);
            String hari = cursor.getString(4);
            String jam = cursor.getString(5);
            String ruangan = cursor.getString(6);
            matkulList.add(new ModelMatkul(id,id_sem,nama_matkul,dosen,hari,jam,ruangan));
        }
        matkulAdapter.notifyDataSetChanged();
        if(matkulList.size()==0){
            Toast.makeText(this,"No Record Found",Toast.LENGTH_SHORT).show();;
        }

        ListMatkul.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                final CharSequence[] items = {"Update" , "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(MatkulList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            Cursor c = mSQLiteHelper.getData("SELECT id FROM MATKUL WHERE id_semester = " + id_semester);
                            ArrayList<Integer> arrID = new ArrayList<>();
                            while(c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogUpdate(MatkulList.this, arrID.get(position));

                        }
                        if(which == 1){
                            Cursor c = mSQLiteHelper.getData("SELECT id FROM MATKUL where id_semester=" + id_semester);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while(c.moveToNext()){
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



        mAddMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatkulList.this, InputMatkulActivity.class);
                intent.putExtra("id_semester",id_semester);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showDialogDelete(final int id) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MatkulList.this);
        dialogDelete.setTitle("WARNING!!!");
        dialogDelete.setMessage("Are you sure to Delete ?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    mSQLiteHelper.deleteMatkul(id);
                    Toast.makeText(MatkulList.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
                updateMatkulList(id_semester);
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    public void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_matkul);
        dialog.setTitle("Update");

        final EditText editMatkul = dialog.findViewById(R.id.editMatkul);
        final EditText editDosen = dialog.findViewById(R.id.editDosen);
        final Spinner editHari = dialog.findViewById(R.id.editHari);
        final EditText editJam = dialog.findViewById(R.id.editJam);
        final EditText editRuangan = dialog.findViewById(R.id.editRuangan);
        final Button editBtnMatkul = dialog.findViewById(R.id.editBtnMatkul);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MatkulList.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.hari));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editHari.setAdapter(myAdapter);

        //set value to text
        Cursor c = mSQLiteHelper.getData("SELECT * FROM MATKUL WHERE ID = " + position);
        while(c.moveToNext()){
            editMatkul.setText(c.getString(2));
            editDosen.setText(c.getString(3));
            editJam.setText(c.getString(5));
            editRuangan.setText(c.getString(6));
            String h = c.getString(4);
            String[] hh = {"Senin","Selasa","Rabu","Kamis","Jumat"};
            for (int i = 0; i<hh.length ; i++){
                if (h.equals(hh[i])){
                    editHari.setSelection(i);
                }
            }
        }

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        //tinggi dialog
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        editHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hari = editHari.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editBtnMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mSQLiteHelper.updateMatkul(
                            editMatkul.getText().toString().trim(),
                            editDosen.getText().toString().trim(),
                            editJam.getText().toString().trim(),
                            editRuangan.getText().toString().trim(),
                            hari,
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Update Berhasil",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
                updateMatkulList(id_semester);
            }
        });

    }

    private void updateMatkulList(int id_semester) {
        Cursor cursor = mSQLiteHelper.getData("SELECT * FROM MATKUL WHERE id_semester = " + id_semester);
        matkulList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            int id_sem = cursor.getInt(1);
            String nama_matkul = cursor.getString(2);
            String dosen = cursor.getString(3);
            String hari = cursor.getString(4);
            String jam = cursor.getString(5);
            String ruangan = cursor.getString(6);

            matkulList.add(new ModelMatkul(id,id_sem,nama_matkul,dosen,hari,jam,ruangan));
        }
        matkulAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(MatkulList.this, MainActivity.class));
        finish();
    }

}
