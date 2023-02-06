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


    /*@GET("products.php")
    Call<List<Products>> getProducts();

    @GET("search.php")
    Call<List<Products>> getSearchLists(
            @Query("column") String column,
            @Query("keyword") String keyword
    );

    @GET("categories.php")
    Call<List<Category>> getCategory(
            @Query("table") String table,
            @Query("column1") String column1,
            @Query("column2") String column2,
            @Query("column3") String column3);

    @GET("wishlists.php")
    Call<List<Products>> getWistlists(@Query("user_id") String query);

    @GET("cart.php")
    Call<List<Cart>> getCart(@Query("user_id") String query);

    @GET("orders.php")
    Call<List<Orders>> getOrder(@Query("user_id") String user_id);

   */

   /* @GET("addresses.php")
    Call<List<Address>> getAddresses();

    @GET("getsellerprofile.php")
    Call<List<Seller>> getSellerProfile(@Query("phone") String phone);

    @GET("products.php")
    Call<List<Products>> getProducts(@Query("seller_id") String seller_id);

    @GET("orders.php")
    Call<List<Orders>> getOrders(@Query("seller_id") String seller_id);

    @GET("getsellerupi.php")
    Call<List<JsonObject>> getSellerUpi(@Query("seller_id") String seller_id);

    @GET("getcolors.php")
    Call<List<JsonObject>> getColors(@Query("seller_id") int seller_id, @Query("product_name") String product_name);

    @GET("getimages.php")
    Call<List<JsonObject>> getImages(@Query("seller_id") int seller_id, @Query("product_name") String product_name, @Query("color") String color);

    @GET("getsizes.php")
    Call<List<JsonObject>> getSizes(@Query("seller_id") int seller_id, @Query("product_name") String product_name, @Query("color") String color);

    @GET("getavailableaddresses.php")
    Call<List<Address>> getAvilableAddresses(@Query("address_ids") String address_ids);

    @GET("checksellerexists.php")
    Call<ResponseModel> checkSellerExists(@Query("phone") String phone);


    @GET("setsellerprofile.php")
    Call<ResponseModel> setSellerProfile(
            @Query("phone") String phone,
            @Query("alt_phone") String elt_phone,
            @Query("name") String user_name,
            @Query("email") String email,
            @Query("address") String address,
            @Query("gpay") String gpay,
            @Query("paytm") String paytm,
            @Query("profile_pic") String profile_pic,
            @Query("purpose") String purpose);

    @Multipart
    @POST("update_photos.php")
    Call<ResponseModel> update_images(@Part List<MultipartBody.Part> image,
                                      @Part("product_id") RequestBody product_id);

    @Multipart
    @POST("save_product.php")
    Call<ResponseModel> saveProduct(@Part List<MultipartBody.Part> image,
                                    @Part("seller_id") RequestBody seller_id,
                                    @Part("product_name") RequestBody product_name,
                                    @Part("price") RequestBody price,
                                    @Part("sale_price") RequestBody sale_price,
                                    @Part("color") RequestBody color,
                                    @Part("size") RequestBody size,
                                    @Part("qnty") RequestBody qnty,
                                    @Part("cod") RequestBody cod,
                                    @Part("desc") RequestBody desc,
                                    @Part("tags") RequestBody tags,
                                    @Part("shipping") RequestBody shipping
    );


    @GET("update_product.php")
    Call<ResponseModel> updateProductFromServer(
            @Query("product_id") int product_id,
            @Query("product_name") String product_name,
            @Query("price") String price,
            @Query("sale_price") String sale_price,
            @Query("color") String color,
            @Query("size") String size,
            @Query("qnty") String qnty,
            @Query("cod") String cod,
            @Query("desc") String desc,
            @Query("tags") String tags,
            @Query("images") String images,
            @Query("shipping") String shipping);


    @GET("delete_item.php")
    Call<ResponseModel> deleteItem(
            @Query("id") int id,
            @Query("table") String table,
            @Query("column") String column);

    @GET("delete_product.php")
    Call<ResponseModel> deleteProduct(
            @Query("product_id") int id);

    @GET("delete_image.php")
    Call<ResponseModel> deleteImage(
            @Query("product_id") int id,
            @Query("image_name") String img_name,
            @Query("image_array") String image_array);*/


/*
    @GET("deleteitem.php")
    Call<ResponseModel> deleteItem(
            @Query("id") String id,
            @Query("table") String table,
            @Query("column") String column);

    @GET("updateitem.php")
    Call<ResponseModel> updateItem(
            @Query("id") String id,
            @Query("table") String table,
            @Query("set_column") String set_column,
            @Query("column") String column,
            @Query("value") String value);

    @GET("checkproceed.php")
    Call<ResponseModel> checkProceed(
            @Query("user_id") String user_id,
            @Query("product_ids") String product_ids,
            @Query("cart_ids") String cart_ids);


    @GET("addcart.php")
    Call<ResponseModel> addToCart(
            @Query("user_id") String user_id,
            @Query("seller_id") int seller_id,
            @Query("product_name") String product_name,
            @Query("color") String color,
            @Query("size") String size);

    @GET("addwishlists.php")
    Call<ResponseModel> addToWishlists(
            @Query("user_id") String user_id,
            @Query("seller_id") int seller_id,
            @Query("product_name") String product_name,
            @Query("color") String color,
            @Query("size") String size);

    @GET("addorder.php")
    Call<ResponseModel> addOrder(
            @Query("user_id") String user_id,
            @Query("status") int status,
            @Query("alt_phone") String alt_phone,
            @Query("address_id") String address_id,
            @Query("pay_mode") String pay_mode,
            @Query("approvalRefNo") String refNo);

    @GET("checkuserexists.php")
    Call<ResponseModel> checkUserExists(@Query("user_id") String user_id);

    @GET("setuserprofile.php")
    Call<ResponseModel> setProfile(
            @Query("user_id") String user_id,
            @Query("user_name") String user_name,
            @Query("email") String email,
            @Query("address") String address,
            @Query("purpose") String purpose);*/
}
