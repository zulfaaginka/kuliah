package com.example.kuliah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SemesterAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<ModelSemester> recordList;

    public SemesterAdapter(Context context, int layout, ArrayList<ModelSemester> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView txtSemester, txtTahun;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();
        if (row == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtSemester = row.findViewById(R.id.txtSemester);
            holder.txtTahun = row.findViewById(R.id.txtTahun);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();
        }

        ModelSemester modelSemester = recordList.get(i);
        holder.txtSemester.setText(modelSemester.getSemester());
        holder.txtTahun.setText(modelSemester.getTahun());
        return row;
    }
}
