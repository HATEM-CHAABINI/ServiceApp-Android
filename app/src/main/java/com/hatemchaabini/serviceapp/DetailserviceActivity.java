package com.hatemchaabini.serviceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hatemchaabini.serviceapp.Chat.MessageActivity;
import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.view.LayoutInflater;

public class DetailserviceActivity extends AppCompatActivity {
    INodeJS myAPI;
    TextView ema,numtel,prix,metier,nom;
    ImageView ima;
    User uu = new User();
    Button btn;
    RatingBar note;
    Context mcontext;
    FloatingActionButton chatbtn;
    SharedPreferences sharedPref ;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }


    public void PopulateListeViewcom(List<Commentaire> PostsFeedList){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lesComm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new AdapterCom(PostsFeedList,this));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailservice);

        myAPI =getAPI();
        mcontext = this;
        sharedPref = mcontext.getSharedPreferences("shL",0);

        chatbtn = findViewById(R.id.Chatbtn);
        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("firesbaseeeeeeid");
                System.out.println(uu.getFirebaseid());
                String idfirebase = sharedPref.getString("firebaseid","");
                System.out.println(idfirebase);
                System.out.println("firesbaseeeeeeid");
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("userid", uu.getFirebaseid());
                mcontext.startActivity(intent);
            }
        });
        String E2= getIntent().getStringExtra("email");
Button btn = findViewById(R.id.btnajoutserv);

        Call<List<User>> call = myAPI.getlaper(E2);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                uu =response.body().get(0);
                        ema= findViewById(R.id.emailserv);
                numtel= findViewById(R.id.telserv);
                prix= findViewById(R.id.prixserv);
                metier= findViewById(R.id.metierserv);
                nom= findViewById(R.id.nomserv);
                ima = findViewById(R.id.imageSer);
                ema.setText(uu.getEmail());
                numtel.setText(uu.getNumtel());
                prix.setText(String.valueOf(uu.getPrix()));
                metier.setText(uu.getMetier());
                nom.setText(uu.getName());
                note= findViewById(R.id.ratingBar);




                Call<List<Commentaire>> callcom = myAPI.getcomm(uu.getId());
                callcom.enqueue(
                        new Callback<List<Commentaire>>() {
                            @Override
                            public void onResponse(Call<List<Commentaire>> call, Response<List<Commentaire>> response) {
                                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                                System.out.println(response.body());
                                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                                 PopulateListeViewcom(response.body());
                            }

                            @Override
                            public void onFailure(Call<List<Commentaire>> call, Throwable t) {

                            }
                        });






                Call<String> callo = myAPI.Retournoteoff(uu.getId());
                callo.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> callo, Response<String> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                System.out.println("aaaaaaahhhhhhhhhhbbbbbbbbbbbbbb");
                                note.setRating(Float.parseFloat(response.body()));
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
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("wwwwwwwwwww");

            }


        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                final View enter_name_view = LayoutInflater.from(mcontext).inflate(R.layout.enter_date_layout, null);
                CalendarView cl = enter_name_view.findViewById(R.id.calendarView);
                cl.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                        currentDate = year+"-"+(month+1)+"-"+dayOfMonth;

                    }
                });
                new MaterialStyledDialog.Builder(mcontext)
                        .setTitle("Choisir la date")
                        .setDescription("One More Step!")
                        .setCustomView(enter_name_view)
                        .setNegativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                dialog.dismiss();
                                //  Toast.makeText(singup.this, "sakeeeeerrrr" , Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setPositiveText("Register")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                System.out.println("bsbsbsbsbsbsbsbsbsbsbsbbsbsbsbsbsbsbsbsbsbsbsbsbs");
                               System.out.println("Date "+currentDate);
                                System.out.println("idO "+uu.getId());
                                Integer idO = uu.getId();
                               // final SharedPreferences sharedPref ;
                              //  sharedPref = mcontext.getSharedPreferences("shL",0);
                                Integer idD = sharedPref.getInt("id",0);
                                System.out.println("idD "+idD);
                                System.out.println("bsbsbsbsbsbsbsbsbsbsbsbbsbsbsbsbsbsbsbsbsbsbsbsbs");
                                compositeDisposable.add(myAPI.addService(idD.toString(),idO.toString(),currentDate)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<String>() {
                                            @Override
                                            public void accept(String s) throws Exception {
                                                Toast.makeText(mcontext, "" + s, Toast.LENGTH_SHORT).show();

                                            }


                                        }));
                            }
                        }).show();


            }

        });
        }
    }

    /*
    *     *
    *
    * */



