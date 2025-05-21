package com.example.merbabu_store_book.api;

import com.example.merbabu_store_book.model.CartResponse;
import com.example.merbabu_store_book.model.LoginResponse;
import com.example.merbabu_store_book.model.RegisterResponse;
import com.example.merbabu_store_book.model.SimpleResponse;
import com.example.merbabu_store_book.model.ProdukModel;
import com.example.merbabu_store_book.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("produk_visit.php")
    Call<ResponseBody> tambahKunjungan(@Field("id") int produkId);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("getProducts.php")
    Call<List<ProdukModel>> getAllProducts();

    @GET("getProductDetail.php")
    Call<ProdukModel> getProductDetail(@Query("id") int id);

    @FormUrlEncoded
    @POST("addToCart.php")
    Call<SimpleResponse> addToCart(
            @Field("user_id") int userId,
            @Field("product_id") int productId,
            @Field("quantity") int quantity
    );


    @FormUrlEncoded
    @POST("add_to_cart.php")
    Call<ResponseBody> addToCart(
            @Field("email") String email,
            @Field("product_id") int productId
    );


    @GET("get_cart.php")
    Call<CartResponse> getCartItems(@Query("email") String email);

    @FormUrlEncoded
    @POST("delete_cart_item.php")
    Call<ResponseBody> deleteCartItem(@Field("cart_id") int cartId);


    @GET("user_detail.php")
    Call<User> getUserByEmail(@Query("email") String email);

    @FormUrlEncoded
    @POST("user_update.php")
    Call<ResponseBody> updateUser(
            @Field("email") String email,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("city") String city,
            @Field("province") String province,
            @Field("postal_code") String postal_code
    );



}
