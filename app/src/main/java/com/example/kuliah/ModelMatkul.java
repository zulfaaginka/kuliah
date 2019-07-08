package com.example.kuliah;

public class ModelMatkul {
    private int id;
    private int id_semester;
    private String nama_matkul;
    private String dosen;
    private String hari;
    private String jam;
    private String ruangan;

    public ModelMatkul(int id, int id_semester,String nama_matkul,String dosen, String hari, String jam, String ruangan ){
        this.id = id;
        this.id_semester = id_semester;
        this.nama_matkul = nama_matkul;
        this.dosen = dosen;
        this.hari = hari;
        this.jam = jam;
        this.ruangan = ruangan;
    }

    public int getId(){ return id;}

    public void setId(){ this.id = id;}

    public int getId_semester(){ return id_semester;}

    public void setId_semester(){ this.id = id_semester;}

    public String getNama_matkul() {
        return nama_matkul;
    }

    public void setNama_matkul(String nama_matkul) {
        this.nama_matkul = nama_matkul;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }
}
