package com.example.merbabu_store_book.model;

import com.google.gson.annotations.SerializedName;

public class ProdukModel {
    public int id;
    public String name;
    public String category;
    public String description;
    public double price;
    public String image;
    public String status;
    public int stok;

    @SerializedName("dikunjungi")
    public int dikunjungi;


}
