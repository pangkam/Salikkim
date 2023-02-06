package com.salikkim.bazar.Helper;

import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.Models.Category;
import com.salikkim.bazar.Models.Order;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.Models.ResponseModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiSet {
    @GET("search.php")
    Call<List<Product>> search(@Query("column") String column,
                               @Query("query") String query);

    @GET("products.php")
    Call<List<Product>> getProducts();

    @GET("cart.php")
    Call<List<Cart>> getCart(@Query("user_id") int user_id);

    @GET("favorite.php")
    Call<List<Product>> getFavorite(@Query("user_id") int user_id);

    @GET("orders.php")
    Call<List<Order>> getOrder(@Query("user_id") int user_id);

    @GET("categories.php")
    Call<List<Category>> getCategories();

    @GET("address.php")
    Call<List<Address>> getAddress();

    @GET("add_favorite.php")
    Call<ResponseModel> addFavorite(
            @Query("user_id") int user_id,
            @Query("product_id") int product_id);

    @GET("add_cart.php")
    Call<ResponseModel> addToCart(
            @Query("user_id") int user_id,
            @Query("seller_id") int seller_id,
            @Query("product_id") int product_id);

    @GET("update_quantity.php")
    Call<ResponseModel> updateQuantity(
            @Query("cart_id") int cart_id,
            @Query("quantity") int quantity);

    @GET("delete_cart.php")
    Call<ResponseModel> deleteCart(
            @Query("cart_id") int cart_id);

    @GET("delete_fav.php")
    Call<ResponseModel> deleteFav(
            @Query("fav_id") int fav_id);

    @GET("move_to_cart.php")
    Call<ResponseModel> moveCart(
            @Query("user_id") int user_id,
            @Query("fav_id") int fav_id,
            @Query("seller_id") int seller_id,
            @Query("product_id") int product_id);

    @Multipart
    @POST("add_order.php")
    Call<ResponseModel> uploadScreenshot(@Part MultipartBody.Part file,
                                         @Part("cus_name") RequestBody customer_name,
                                         @Part("user_id") RequestBody user_id,
                                         @Part("address") RequestBody address);


    @GET("set_userprofile.php")
    Call<ResponseModel> setProfile(
            @Query("user_id") String user_id,
            @Query("user_name") String user_name,
            @Query("mobile") String mobile,
            @Query("alt_mobile") String alt_mobile,
            @Query("email") String email,
            @Query("address_id") int address);
}
