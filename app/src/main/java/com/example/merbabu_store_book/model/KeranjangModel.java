package com.example.merbabu_store_book.model;

import com.google.gson.annotations.SerializedName;

public class KeranjangModel {
    private int id, user_id, produk_id, harga, quantity, stok;

    @SerializedName("nama")
    private String name;

    @SerializedName("image")
    private String image;

    private String status;

    public int getId() { return id; }
    public int getUser_id() { return user_id; }
    public int getProduk_id() { return produk_id; }
    public int getHarga() { return harga; }
    public int getQuantity() { return quantity; }
    public int getStok() { return stok; }
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getStatus() { return status; }
}
