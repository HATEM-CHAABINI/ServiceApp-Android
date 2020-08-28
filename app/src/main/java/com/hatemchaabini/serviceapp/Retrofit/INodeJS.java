package com.hatemchaabini.serviceapp.Retrofit;



import com.hatemchaabini.serviceapp.Commentaire;
import com.hatemchaabini.serviceapp.LesMetier;
import com.hatemchaabini.serviceapp.Service;
import com.hatemchaabini.serviceapp.ServiceStatut;
import com.hatemchaabini.serviceapp.User;
import com.hatemchaabini.serviceapp.Video;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface INodeJS {
    @Multipart
    @POST("/upload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

    @POST("register/")
    @FormUrlEncoded
    Observable<String> registerUser(
                                    @Field("fireid") String fireid,
                                    @Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password,
                                    @Field("username") String username,
                                    @Field("numtel") String numtel,
                                    @Field("adresse") String adresse,
                                    @Field("metier") String metier,
                                    @Field("ville") String ville,
                                    @Field("codepostale") String codepostale,
                                    @Field("prix") float prix

                                    );

    @Multipart
    @POST("/newupload")
    Call<ResponseBody> newpostImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name,
                                    @Part("firebaseid") String fireid,
                                    @Part("token") String token,
                                    @Part("email") String email,
                                    @Part("name") String nom,
                                    @Part("password") String password,
                                    @Part("username") String username,
                                    @Part("numtel") String numtel,
                                    @Part("adresse") String adresse,
                                    @Part("metier") String metier,
                                    @Part("ville") String ville,
                                    @Part("codepostale") String codepostale,
                                    @Part("prix") float prix);


    @POST("nahi/")
    Observable<String> effac();
    @POST("verif/")
    @FormUrlEncoded
    Observable<String> verifuser(@Field("email") String email
    );

    @GET("personnoc/{n}")
    Call<List<User>> getListTout(@Path("n") String n);



    @POST("login/")
    @FormUrlEncoded
    Observable<String>loginUser(@Field("email") String email,
                                @Field("password") String password);



    @POST("addServ/")
    @FormUrlEncoded
    Observable<String>addService(@Field("idD") String idD,
                                 @Field("idO") String idO,
                                 @Field("date") String date);


    @GET("/Retournoteoff/{n}")
    Call<String> Retournoteoff(@Path("n") int n);


    @GET("metier/")
    Observable<List<LesMetier>> getMetierList();


    @GET("/uploads/{upload}")
    Call<ResponseBody> getImage(@Path("upload") String n);



    @GET("getlaperson/{n}")
    Call<List<User>> getlaper(@Path("n") String n);

    @GET("affServiceOffertConfirmer/{n}")
    Call<List<Service>> getoffconf(@Path("n") int id);

    @GET("affServiceOffertNonConfirmer/{n}")
    Call<List<Service>> getoffnonconf(@Path("n") int id);

    @GET("affServiceDemanderConfirmer/{n}")
    Call<List<Service>> getdemaconf(@Path("n") int id);

    @GET("affServiceDemanderNonConfirmer/{n}")
    Call<List<Service>> getdemanonconf(@Path("n") int id);


    @GET("affService/{n}")
    Call<List<Service>> getServiceidS(@Path("n") int id);

    @GET("affDemandeService/{n}")
    Call<List<Service>> getDemandeServiceidS(@Path("n") int id);

    @GET("/Confirmer/{id}/{com}")
    Call<String> confirmerService(@Path("id") int id,@Path("com") String com);

    @GET("/Refuser/{id}/{com}")
    Call<String> refuserService(@Path("id") int id,@Path("com") String com);

    @GET("/NoterService/{id}/{com}/{note}")
    Call<String> NoterService(@Path("id") int id,@Path("com") String com,@Path("note") int note);


    @POST("gettokenfromemail/")
    @FormUrlEncoded
    Observable<String> gettokenfromemail(@Field("n") String n);


    @GET("/getcomm/{idoff}")
    Call<List<Commentaire>> getcomm(@Path("idoff") int idoff);

    @POST("searchema/")
    @FormUrlEncoded
    Observable<String>SearchUser(@Field("email") String email);

    @GET("RechercheVideo/{genre}")
    Call<List<Video>> RechercheVideo(@Path("genre") String genre);



    @GET("/verifpassword/{id}/{password}")
    Call<String> verifpassword(@Path("id") int id,@Path("password") String password);

    @POST("updateproflieavecmdp/")
    @FormUrlEncoded
    Observable<String> updateproflieavecmdp(@Field("password") String password,
                                            @Field("adresse") String adresse,
                                            @Field("codepostale") String codepostale,
                                            @Field("tel") String tel,
                                            @Field("ville") String ville,
                                            @Field("id") int id);

    @POST("updateprofliesansmdp/")
    @FormUrlEncoded
    Observable<String> updateprofliesansmdp(@Field("adresse") String adresse,
                                            @Field("codepostale") String codepostale,
                                            @Field("tel") String tel,
                                            @Field("ville") String ville,
                                            @Field("id") int id);


    @POST("addvideo/")
    @FormUrlEncoded
    Observable<String> addvideo(@Field("idD") int idD,
                                            @Field("path") String path,
                                            @Field("genre") String genre);

    @GET("affServiceOffertValideretrefuser/{n}")
    Call<List<ServiceStatut>> affServiceOffertValideretrefuser(@Path("n") int id);

    @GET("affServiceDemandeValideretrefuser/{n}")
    Call<List<ServiceStatut>> affServiceDemandeValideretrefuser(@Path("n") int id);


    @POST("sendnotif/")
    @FormUrlEncoded
    Observable<String> sendnotif(@Field("title") String title,
                                @Field("body") String body,
                                @Field("totoken") String totoken);

}
