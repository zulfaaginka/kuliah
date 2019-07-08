package com.example.kuliah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

    //constructor
    SQLiteHelper(Context context,
                 String name,
                 SQLiteDatabase.CursorFactory factory,
                 int version){
        super(context,name,factory,version);
    }

    public void queryData ( String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //insert data
    public void insertData (String semester, String tahun){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "INSERT INTO SEMESTER VALUES(NULL,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,semester);
        statement.bindString(2,tahun);

        statement.executeInsert();
    }

    public void updateData(String semester, String tahun, int id){
        SQLiteDatabase database = getWritableDatabase(

        );

        String sql = "UPDATE SEMESTER SET semester=?, tahun=? WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,semester);
        statement.bindString(2,tahun);
        statement.bindDouble(3,(double)id);

        statement.execute();
        statement.close();
    }

    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM SEMESTER WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        statement.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    //Matkul
    public void insertMatkul(int id_semester,String nama_matkul, String dosen,String jam, String ruangan, String hari){
    SQLiteDatabase database = getWritableDatabase();

    String sql = "INSERT INTO MATKUL VALUES(NULL,?,?,?,?,?,?)";

    SQLiteStatement statement = database.compileStatement(sql);
    statement.clearBindings();

    statement.bindDouble(1,id_semester);
    statement.bindString(2,nama_matkul);
    statement.bindString(3,dosen);
    statement.bindString(4,hari);
    statement.bindString(5,jam);
    statement.bindString(6,ruangan);

    statement.executeInsert();
    statement.close();

    }

    public void deleteMatkul(int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM MATKUL WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        statement.close();
    }

    public void updateMatkul(String nama_matkul, String dosen,String jam, String ruangan, String hari, int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE matkul set nama_matkul=?,dosen = ?,hari =?,jam=?,ruangan=? where id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,nama_matkul);
        statement.bindString(2,dosen);
        statement.bindString(3,hari);
        statement.bindString(4,jam);
        statement.bindString(5,ruangan);
        statement.bindDouble(6,id);

        statement.execute();
        statement.close();

    }


    public Cursor getMatkul(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
