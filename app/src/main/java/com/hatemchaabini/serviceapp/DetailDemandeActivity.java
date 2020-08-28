package com.hatemchaabini.serviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDemandeActivity extends AppCompatActivity {
    INodeJS myAPI;
    Service uu = new Service();
    TextView nom,metier,date;
    EditText commentaire;
    RatingBar note;
    Button confi;
    ImageView image;
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_demande);

        myAPI =getAPI();
        confi = findViewById(R.id.btnConfirmerD);
        final int idS= getIntent().getIntExtra("idS",0);

        commentaire = findViewById(R.id.commentaireD);

        confi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("wwwwwwwwwww");
                System.out.println(commentaire.getText().toString());
                System.out.println(note.getRating());
                System.out.println(idS);
                Call<String> calll = myAPI.NoterService(idS,commentaire.getText().toString(), (int) note.getRating());
                calll.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();




                        //finish();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("id","demande");

                startActivity(intent); }
        });

        Call<List<Service>> call = myAPI.getDemandeServiceidS(idS);
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {

                uu =response.body().get(0);
                System.out.println();
                metier= findViewById(R.id.metierD);
                nom= findViewById(R.id.nomservD);
                image = findViewById(R.id.imageSerD);
                date = findViewById(R.id.dateD);
                note = findViewById(R.id.ratingBarD);
                note.setStepSize(1);
                metier.setText(uu.getMetier());
                nom.setText(uu.getName());
                date.setText(uu.getDateD());

                Call<ResponseBody> calll = myAPI.getImage(uu.getImage());
                calll.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> calll, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                // display the image data in a ImageView or save it
                                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                image.setImageBitmap(bmp);
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

    }
}
