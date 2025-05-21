package com.example.merbabu_store_book.ui.keranjang;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merbabu_store_book.R;
import com.example.merbabu_store_book.api.ApiClient;
import com.example.merbabu_store_book.api.ApiService;
import com.example.merbabu_store_book.model.CartItem;
import com.example.merbabu_store_book.model.CartResponse;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangFragment extends Fragment {

    RecyclerView recyclerView;
    TextView totalBayar;
    Button btnCheckout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_keranjang, container, false);

        recyclerView = root.findViewById(R.id.recyclerKeranjang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        totalBayar = root.findViewById(R.id.totalBayar);
        btnCheckout = root.findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v ->
                Toast.makeText(getContext(), "Fitur checkout belum tersedia", Toast.LENGTH_SHORT).show()
        );

        loadKeranjang();

        return root;
    }

    private void loadKeranjang() {
        SharedPreferences prefs = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("userEmail", null);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getCartItems(email).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CartItem> items = response.body().cart;

                    // Buat adapter dengan listener
                    KeranjangAdapter adapter = new KeranjangAdapter(getContext(), items, new KeranjangAdapter.OnCartChangeListener() {
                        @Override
                        public void onCartUpdated() {
                            loadKeranjang(); // refresh total & daftar item
                        }
                    });

                    recyclerView.setAdapter(adapter);

                    // Hitung ulang total
                    double total = 0;
                    for (CartItem item : items) {
                        total += item.price * item.quantity;
                    }

                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                    totalBayar.setText("Total: " + format.format(total));
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal memuat keranjang", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
