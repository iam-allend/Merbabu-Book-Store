package com.example.merbabu_store_book.ui.keranjang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.merbabu_store_book.R;
import com.example.merbabu_store_book.api.ApiClient;
import com.example.merbabu_store_book.api.ApiService;
import com.example.merbabu_store_book.model.CartItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    private Context context;
    private List<CartItem> list;
    private OnCartChangeListener listener;

    // Constructor
    public KeranjangAdapter(Context context, List<CartItem> list, OnCartChangeListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangAdapter.ViewHolder holder, int position) {
        CartItem item = list.get(position);

        holder.name.setText(item.name);
        holder.harga.setText("Rp. " + (int) item.price);
        holder.qty.setText("" + item.quantity);
        holder.total.setText("Total: Rp. " + (int) item.total);
        holder.status.setText("" + item.status);

        Glide.with(context)
                .load("http://10.0.2.2/aplikasi-mobile-2/merbabu_store/Image/" + item.image)
                .into(holder.image);

        holder.btnHapus.setOnClickListener(v -> {
            ApiService api = ApiClient.getClient().create(ApiService.class);
            api.deleteCartItem(item.cart_id).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(context, "Dihapus dari keranjang", Toast.LENGTH_SHORT).show();
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        list.remove(pos);
                        notifyItemRemoved(pos);
                        if (listener != null) {
                            listener.onCartUpdated(); // Panggil refresh di fragment
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, "Gagal menghapus", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, harga, qty, total, status;
        Button btnHapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageProduk);
            name = itemView.findViewById(R.id.namaProduk);
            harga = itemView.findViewById(R.id.hargaProduk);
            qty = itemView.findViewById(R.id.qty);
            total = itemView.findViewById(R.id.total);
            status = itemView.findViewById(R.id.status);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }

    // Interface untuk komunikasi dengan fragment
    public interface OnCartChangeListener {
        void onCartUpdated();
    }
}
