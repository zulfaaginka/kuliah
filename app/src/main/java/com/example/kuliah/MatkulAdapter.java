package com.example.kuliah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MatkulAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ModelMatkul> matkulList;

    public MatkulAdapter(Context context, int layout, ArrayList<ModelMatkul> matkulList) {
        this.context = context;
        this.layout = layout;
        this.matkulList = matkulList;
    }

    @Override
    public int getCount() {
        return matkulList.size();
    }

    @Override
    public Object getItem(int position) {
        return matkulList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtMatkul, txtDosen, txtHari, txtJam, txtRuangan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();
        if (row == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);
            holder.txtMatkul = row.findViewById(R.id.txtMatkul);
            holder.txtDosen = row.findViewById(R.id.txtDosen);
            holder.txtHari = row.findViewById(R.id.txtHari);
            holder.txtJam = row.findViewById(R.id.txtJam);
            holder.txtRuangan = row.findViewById(R.id.txtRuangan);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();
        }
        ModelMatkul modelMatkul = matkulList.get(position);
        holder.txtMatkul.setText(modelMatkul.getNama_matkul());
        holder.txtDosen.setText(modelMatkul.getDosen());
        holder.txtHari.setText(modelMatkul.getHari());
        holder.txtJam.setText(modelMatkul.getJam());
        holder.txtRuangan.setText(modelMatkul.getRuangan());
        return row;
    }
}
