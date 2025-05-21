package com.example.merbabu_store_book.model;

public class Product {
    private int id;
    private String nama_produk;
    private double harga;
    private int stok;
    private String gambar;

    private String status;

    private String category;

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama_produk() { return nama_produk; }
    public void setNama_produk(String nama_produk) { this.nama_produk = nama_produk; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }


        private String name;
        private String image;
        private int price;
        private int stock;

        // Getter & Setter
        public String getName() { return name; }
        public String getImage() { return image; }
        public int getPrice() { return price; }
        public int getStock() { return stock; }


}