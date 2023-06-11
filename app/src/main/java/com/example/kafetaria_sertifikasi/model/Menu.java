package com.example.kafetaria_sertifikasi.model;

public class Menu {
    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getKafe_id() {
        return kafe_id;
    }

    public void setKafe_id(Integer kafe_id) {
        this.kafe_id = kafe_id;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    private String jenis, nama;
    private Integer id, kafe_id, harga;

    public Menu(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
