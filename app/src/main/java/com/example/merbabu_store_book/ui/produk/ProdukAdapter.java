package com.example.merbabu_store_book.ui.produk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.merbabu_store_book.R;
import com.example.merbabu_store_book.api.ApiClient;
import com.example.merbabu_store_book.api.ApiService;
import com.example.merbabu_store_book.model.ProdukModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {

    private final Context context;
    private final List<ProdukModel> list;
    private final List<ProdukModel> fullList;

    public ProdukAdapter(Context context, List<ProdukModel> list) {
        this.context = context;
        this.list = list;
        this.fullList = new ArrayList<>(list);
    }

    public void filter(String text) {
        list.clear();
        if (text.isEmpty()) {
            list.addAll(fullList);
        } else {
            text = text.toLowerCase();
            for (ProdukModel item : fullList) {
                if (item.name.toLowerCase().contains(text)) {
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produk, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProdukAdapter.ViewHolder holder, int position) {


        ProdukModel produk = list.get(position);

        holder.dikunjungi.setText("Dikunjungi: " + produk.dikunjungi + "x");

        holder.nama.setText(produk.name);
        holder.harga.setText("Rp " + (int) produk.price);
        holder.stok.setText("Stok: " + produk.stok);
        holder.category.setText(produk.category);

        if (produk.stok > 0) {
            holder.status.setText("Tersedia");
            holder.status.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            holder.btnKeranjang.    setVisibility(View.VISIBLE); // tampilkan tombol keranjang
        } else {
            holder.status.setText("Tidak tersedia");
            holder.status.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
            holder.btnKeranjang.setVisibility(View.GONE); // sembunyikan tombol keranjang
        }


        Glide.with(context)
                .load("http://10.0.2.2/aplikasi-mobile-2/merbabu_store/Image/" + produk.image)
                .into(holder.gambar);

        // Ke halaman detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProdukDetailActivity.class);
            intent.putExtra("produk_id", produk.id);
            intent.putExtra("produk_nama", produk.name);
            intent.putExtra("produk_harga", produk.price);
            intent.putExtra("produk_deskripsi", produk.description);
            intent.putExtra("produk_gambar", produk.image);
            intent.putExtra("produk_stok", produk.stok);
            intent.putExtra("produk_status", produk.status);
            intent.putExtra("product_category", produk.category);
            intent.putExtra("produk_dikunjungi", produk.dikunjungi); // dikirim untuk ditampilkan
//
//            ApiService apiService = ApiClient.getClient().create(ApiService.class);
//            apiService.tambahKunjungan(produk.id).enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    // Optional: Log jika ingin melihat keberhasilan
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    // Optional: Log error
//                }
//            });

            context.startActivity(intent);
        });

        // Tambah ke keranjang
        holder.btnKeranjang.setOnClickListener(v -> {
            SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            String userEmail = prefs.getString("userEmail", null);

            if (userEmail != null) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                apiService.addToCart(userEmail, produk.id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Produk ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Gagal menambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "Anda belum login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView nama, harga, stok, status, category;
        Button btnKeranjang;
        TextView dikunjungi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.imageProduk);
            nama = itemView.findViewById(R.id.namaProduk);
            harga = itemView.findViewById(R.id.hargaProduk);
            stok = itemView.findViewById(R.id.stok);
            status = itemView.findViewById(R.id.status);
            category = itemView.findViewById(R.id.category);
            btnKeranjang = itemView.findViewById(R.id.btnKeranjang);

            dikunjungi = itemView.findViewById(R.id.dikunjungi);


        }
    }
}
