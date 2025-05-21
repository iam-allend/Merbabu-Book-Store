package com.example.merbabu_store_book.ui.produk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.merbabu_store_book.R;
import com.example.merbabu_store_book.api.ApiClient;
import com.example.merbabu_store_book.api.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukDetailActivity extends AppCompatActivity {

    TextView namaProduk, hargaProduk, deskripsiProduk, stokProduk;
    TextView kategoriProduk, statusProduk, dikunjungiProduk;
    Button btnKeranjang, btnKembali;
    ImageView gambarProduk;

    int produkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_detail);

        // Inisialisasi view
        namaProduk = findViewById(R.id.namaProduk);
        hargaProduk = findViewById(R.id.hargaProduk);
        deskripsiProduk = findViewById(R.id.deskripsiProduk);
        gambarProduk = findViewById(R.id.gambarProduk);
        stokProduk = findViewById(R.id.stok);
        statusProduk = findViewById(R.id.status);
        kategoriProduk = findViewById(R.id.category);
        dikunjungiProduk = findViewById(R.id.tvDikunjungi);
        btnKeranjang = findViewById(R.id.btnKeranjang);
        btnKembali = findViewById(R.id.btnKembali);

        // Ambil data intent
        Intent intent = getIntent();
        produkId = intent.getIntExtra("produk_id", -1);
        String nama = intent.getStringExtra("produk_nama");
        double harga = intent.getDoubleExtra("produk_harga", 0);
        String deskripsi = intent.getStringExtra("produk_deskripsi");
        int stok = intent.getIntExtra("produk_stok", 0);
        String gambar = intent.getStringExtra("produk_gambar");
        String status = intent.getStringExtra("produk_status");
        String category = intent.getStringExtra("product_category");
        int dikunjungi = intent.getIntExtra("produk_dikunjungi", 0);

        // Set data ke view
        namaProduk.setText(nama);
        hargaProduk.setText("Rp " + (int) harga);
        deskripsiProduk.setText(deskripsi);
        stokProduk.setText("Stok: " + stok);
        kategoriProduk.setText((category != null ? category : "-"));
        statusProduk.setText((status != null ? status : "-"));
        dikunjungiProduk.setText("Dikunjungi: " + dikunjungi + " kali");

        if (stok > 0) {
            statusProduk.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            btnKeranjang.setVisibility(View.VISIBLE);
        } else {
            statusProduk.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            btnKeranjang.setVisibility(View.GONE);
        }

        Glide.with(this)
                .load("http://10.0.2.2/aplikasi-mobile-2/merbabu_store/Image/" + gambar)
                .into(gambarProduk);

        // Update jumlah dikunjungi
        updateDikunjungi(produkId);

        // Tombol kembali
        btnKembali.setOnClickListener(v -> finish());

        // Tambah ke keranjang
        btnKeranjang.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userEmail = prefs.getString("userEmail", null);

            if (userEmail != null) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                apiService.addToCart(userEmail, produkId).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(ProdukDetailActivity.this, "Produk ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ProdukDetailActivity.this, "Gagal menambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Anda belum login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDikunjungi(int produkId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.tambahKunjungan(produkId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Tidak perlu toast
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Tidak perlu toast
            }
        });
    }
}
