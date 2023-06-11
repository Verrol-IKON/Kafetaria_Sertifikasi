package com.example.kafetaria_sertifikasi.model;

public class Transaction {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal_item() {
        return total_item;
    }

    public void setTotal_item(Integer total_item) {
        this.total_item = total_item;
    }

    public Integer getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(Integer total_harga) {
        this.total_harga = total_harga;
    }

    private Integer id, total_item, total_harga;
    public Transaction(){

    }
}
