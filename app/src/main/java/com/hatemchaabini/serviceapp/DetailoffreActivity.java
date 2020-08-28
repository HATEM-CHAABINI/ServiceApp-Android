package com.hatemchaabini.serviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.RetrofitClient;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailoffreActivity extends AppCompatActivity {
    INodeJS myAPI;
    Service uu = new Service();
    TextView ema,numtel,addr,metier,nom,date;
    EditText com;
    ImageView ima;
    Button conf,ref;
    String token;
    CompositeDisposable compositeDisposable =new CompositeDisposable();

    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }
    private INodeJS getAPIC() {
        return RetrofitClient.getInstance().create(INodeJS.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailoffre);
        myAPI =getAPI();
        final String[] totoken = new String[1];
        conf = findViewById(R.id.btnConfirmerv);
        ref = findViewById(R.id.btnRefuser);
        com = findViewById(R.id.commentaire);
        int idS= getIntent().getIntExtra("idS",0);
        System.out.println("ooooooooffffffrrrrrrreeeeeeee");
        System.out.println(idS);
        System.out.println("ooooooooffffffrrrrrrreeeeeeee");
        Call<List<Service>> call = myAPI.getServiceidS(idS);
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {

                uu =response.body().get(0);
                System.out.println();

                ema= findViewById(R.id.emailserv);
                numtel= findViewById(R.id.telserv);
                metier= findViewById(R.id.metierserv);
                nom= findViewById(R.id.nomserv);
                ima = findViewById(R.id.imageSer);
                date = findViewById(R.id.date);
                addr = findViewById(R.id.adresse);
                ema.setText(uu.getEmail());
                numtel.setText(uu.getNumtel());
                metier.setText(uu.getMetier());
                nom.setText(uu.getName());
                date.setText(uu.getDateD());
                addr.setText(uu.getAdresse());

                Call<ResponseBody> calll = myAPI.getImage(uu.getImage());
                calll.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> calll, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                // display the image data in a ImageView or save it
                                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                ima.setImageBitmap(bmp);
                            } else {
                                // TODO
                            }
                        } else {
                            // TODO
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> calll, Throwable t) {
                        // TODO
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                System.out.println("wwwwwwwwwww");

            }


        });


        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myAPI =getAPIC();

                compositeDisposable.add(myAPI.gettokenfromemail(uu.getEmail())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                System.out.println(s);
                                token = s.substring(s.indexOf(":")+2,s.indexOf("}")-1);
                                System.out.println(token);
                                myAPI =getAPI();

                                Call<String> callo = myAPI.confirmerService(uu.getIdS(),com.getText().toString());
                                callo.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> callo, Response<String> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body() != null) {
                                                System.out.println("aaaaaaahhhhhhhhhhbbbbbbbbbbbbbb");
                                                System.out.println(token);
                                                System.out.println(response.body());
                                                compositeDisposable.add(myAPI.sendnotif("Consulter vos demande","Votre demande a ete accepter",token)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Consumer<String>() {
                                                            @Override
                                                            public void accept(String s) throws Exception {
                                                                Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();

                                                            }


                                                        }));
                                                System.out.println("aaaaaaahhhhhhhhhhbbbbbbbbbbbbbb");
                                            } else {
                                                // TODO
                                            }
                                        } else {
                                            // TODO
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> callo, Throwable t) {
                                        // TODO
                                    }
                                });
                            }


                        }));
              /*  */
            }
        });
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> callo = myAPI.refuserService(uu.getIdS(),com.getText().toString());
                callo.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> callo, Response<String> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                System.out.println("aaaaaaahhhhhhhhhhbbbbbbbbbbbbbb");
                                System.out.println(response.body());
                                System.out.println("aaaaaaahhhhhhhhhhbbbbbbbbbbbbbb");
                            } else {
                                // TODO
                            }
                        } else {
                            // TODO
                        }
                    }

                    @Override
                    public void onFailure(Call<String> callo, Throwable t) {
                        // TODO
                    }
                });
            }
        });
    }
}
