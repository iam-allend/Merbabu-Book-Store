package com.example.merbabu_store_book.ui.produk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merbabu_store_book.R;
import com.example.merbabu_store_book.api.ApiClient;
import com.example.merbabu_store_book.api.ApiService;
import com.example.merbabu_store_book.model.ProdukModel;

import androidx.appcompat.widget.SearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukFragment extends Fragment {
    private static final int DETAIL_PRODUK_REQUEST = 1;

    RecyclerView recyclerView;
    ProdukAdapter adapter;
    SearchView searchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_produk, container, false);

        recyclerView = root.findViewById(R.id.recyclerProduk);
        searchView = root.findViewById(R.id.searchView);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView = root.findViewById(R.id.recyclerProduk);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // ← 2 kolom


        // Pasang listener pencarian
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null) adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) adapter.filter(newText);
                return true;
            }
        });

        loadProduk();

        return root;
    }

    private void loadProduk() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getAllProducts().enqueue(new Callback<List<ProdukModel>>() {
            @Override
            public void onResponse(Call<List<ProdukModel>> call, Response<List<ProdukModel>> response) {
                if (response.isSuccessful()) {
                    adapter = new ProdukAdapter(getContext(), response.body());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProdukModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengambil data produk", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
