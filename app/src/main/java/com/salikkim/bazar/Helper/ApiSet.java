package com.salikkim.bazar.Helper;

import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.Models.Category;
import com.salikkim.bazar.Models.Order;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.Models.ResponseModel;
import com.salikkim.bazar.Models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiSet {

    @GET("userprofile.php")
    Call<List<User>> getUserProfile(@Query("user_id") String user_id);

    @GET("search.php")
    Call<List<Product>> search(@Query("column") String column,
                               @Query("query") String query);

    @GET("products.php")
    Call<List<Product>> getProducts();

    @GET("cart.php")
    Call<List<Cart>> getCart(
            @Query("user_id") String user_id,
            @Query("address_id") int address_id);

    @GET("favorite.php")
    Call<List<Product>> getFavorite(@Query("user_id") String user_id);

    @GET("orders.php")
    Call<List<Order>> getOrder(@Query("user_id") String user_id);

    @GET("categories.php")
    Call<List<Category>> getCategories();

    @GET("address.php")
    Call<List<Address>> getAddress();

    @GET("addfavorite.php")
    Call<ResponseModel> addFavorite(
            @Query("user_id") String user_id,
            @Query("product_id") int product_id);

    @GET("addcart.php")
    Call<ResponseModel> addToCart(
            @Query("user_id") String user_id,
            @Query("seller_id") String seller_id,
            @Query("product_id") int product_id);

    @GET("updatequantity.php")
    Call<ResponseModel> updateQuantity(
            @Query("cart_id") int cart_id,
            @Query("quantity") int quantity);

    @GET("deletecart.php")
    Call<ResponseModel> deleteCart(
            @Query("cart_id") int cart_id);

    @GET("deletefav.php")
    Call<ResponseModel> deleteFav(
            @Query("fav_id") int fav_id);

    @GET("move_to_cart.php")
    Call<ResponseModel> moveCart(
            @Query("user_id") String user_id,
            @Query("fav_id") int fav_id,
            @Query("seller_id") String seller_id,
            @Query("product_id") int product_id);

    @Multipart
    @POST("addorder.php")
    Call<ResponseModel> addOrder(@Part MultipartBody.Part image,
                                 @Part("user_id") RequestBody user_id,
                                 @Part("address") RequestBody address);


    @GET("setuserprofile.php")
    Call<ResponseModel> setProfile(
            @Query("user_id") String user_id,
            @Query("user_name") String user_name,
            @Query("mobile") String mobile,
            @Query("alt_mobile") String alt_mobile,
            @Query("email") String email,
            @Query("address_id") int address_id);
}
