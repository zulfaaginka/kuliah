package com.example.kuliah;

public class ModelSemester {
    private int id;
    private String semester;
    private String tahun;

    public ModelSemester(int id, String semester, String tahun){
        this.id = id;
        this.semester =  semester;
        this.tahun = tahun;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}
