package com.example.merbabu_store_book.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.merbabu_store_book.LoginActivity;
import com.example.merbabu_store_book.R;
import com.example.merbabu_store_book.api.ApiClient;
import com.example.merbabu_store_book.api.ApiService;
import com.example.merbabu_store_book.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    EditText etNama, etEmail, etPhone, etAlamat, etKota, etProvinsi, etKodePos;
    Button btnUpdate, btnLogout, btnContact;

    String userEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi UI
        etNama = root.findViewById(R.id.etNama);
        etEmail = root.findViewById(R.id.etEmail);
        etAlamat = root.findViewById(R.id.etAlamat);
        etKota = root.findViewById(R.id.etKota);
        etProvinsi = root.findViewById(R.id.etProvinsi);
        etPhone = root.findViewById(R.id.etPhone);
        etKodePos = root.findViewById(R.id.etKodePos);
        btnUpdate = root.findViewById(R.id.btnUpdate);
        btnLogout = root.findViewById(R.id.btnLogout);
        btnContact = root.findViewById(R.id.btnContact);

        // Email tidak bisa diedit
        etEmail.setEnabled(false);
        etEmail.setFocusable(false);

        // Ambil email dari SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userEmail = prefs.getString("userEmail", null);

        // Load data user
        loadUserData(userEmail);

        // Update user
        btnUpdate.setOnClickListener(v -> updateUser());

        // Logout
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getContext(), LoginActivity.class));
            requireActivity().finish();
        });

        // Contact
        btnContact.setOnClickListener(v -> showContactPopup());

        return root;
    }

    private void loadUserData(String email) {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.getUserByEmail(email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    etNama.setText(user.getName());
                    etEmail.setText(user.getEmail());
                    etAlamat.setText(user.getAddress());
                    etKota.setText(user.getCity());
                    etProvinsi.setText(user.getProvince());
                    etPhone.setText(user.getPhone());
                    etKodePos.setText(user.getPostal_code());
                } else {
                    Toast.makeText(getContext(), "Gagal mengambil data user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal koneksi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser() {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.updateUser(
                userEmail,
                etNama.getText().toString(),
                etAlamat.getText().toString(),
                etKota.getText().toString(),
                etProvinsi.getText().toString(),
                etPhone.getText().toString(),
                etKodePos.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Update gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showContactPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_contact, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        ImageView btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> dialog.dismiss());
    }
}
