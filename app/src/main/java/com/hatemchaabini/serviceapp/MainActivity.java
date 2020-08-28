package com.hatemchaabini.serviceapp;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hatemchaabini.serviceapp.Chat.MaineActivity;
import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigation;
    private TextView email,nom;
    private CircleImageView img;
    SharedPreferences sharedPref ;
    INodeJS myAPI;
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigation = findViewById(R.id.nav);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mNavigation != null) {
            mNavigation.setNavigationItemSelectedListener(this);
        }
        String idd= "";
        idd= getIntent().getStringExtra("id");
        sharedPref = getSharedPreferences("shL",0);
        String emaile = sharedPref.getString("log","");
        String nome = sharedPref.getString("nom","");
        String pathim =sharedPref.getString("image","");

        myAPI =getAPI();

        View headerView = mNavigation.getHeaderView(0);
        email =  headerView.findViewById(R.id.emailProfileheader);
        email.setText(emaile);
        nom =  headerView.findViewById(R.id.nomProfileheader);
        nom.setText(nome);
        img =  headerView.findViewById(R.id.imgProfileheader);
        System.out.println("pattthhhhhhhhhhimage");
        System.out.println(pathim);
        System.out.println("pattthhhhhhhhhhimage");
        Call<ResponseBody> call = myAPI.getImage(pathim);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        img.setImageBitmap(bmp);
                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // TODO
            }
        });





        if (idd != null){
if (idd.equals("demande")){
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    MesDemandeServicesActivity fragment = new MesDemandeServicesActivity();
    FrameLayout fl =findViewById(R.id.frame1);
    fl.removeAllViews();
    fragmentTransaction.add(R.id.frame1,fragment);
    fragmentTransaction.commit();
    System.out.println("Event");

    Toast.makeText(getApplicationContext(),"Event",Toast.LENGTH_SHORT).show();
}}else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ServicesFragmentActivity fragment = new ServicesFragmentActivity();
            FrameLayout fl = findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1, fragment);
            fragmentTransaction.commit();
            System.out.println("dashboard");
            Toast.makeText(getApplicationContext(), "dashboard", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.db) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ServicesFragmentActivity fragment = new ServicesFragmentActivity();
            FrameLayout fl =findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1,fragment);
            fragmentTransaction.commit();
            System.out.println("dashboard");
            Toast.makeText(getApplicationContext(),"dashboard",Toast.LENGTH_SHORT).show();
            return true;

        }else if(id == R.id.event){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            OffreServActivity fragment = new OffreServActivity();
            FrameLayout fl =findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1,fragment);
            fragmentTransaction.commit();
            System.out.println("Event");

            Toast.makeText(getApplicationContext(),"Event",Toast.LENGTH_SHORT).show();
            return true;

        }else if(id == R.id.demande){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MesDemandeServicesActivity fragment = new MesDemandeServicesActivity();
            FrameLayout fl =findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1,fragment);
            fragmentTransaction.commit();
            System.out.println("Event");

            Toast.makeText(getApplicationContext(),"Event",Toast.LENGTH_SHORT).show();
            return true;

        }else if(id == R.id.videos){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            VideoTuto fragment = new VideoTuto();
            FrameLayout fl =findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1,fragment);
            fragmentTransaction.commit();
            System.out.println("Event");

            Toast.makeText(getApplicationContext(),"Event",Toast.LENGTH_SHORT).show();
            return true;

        }else if(id == R.id.Messages){
                 Intent intent = new Intent(MainActivity.this, MaineActivity.class);
          //     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
            return true;

        }else if(id == R.id.updateProfile){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmenProfile fragment = new FragmenProfile();
            FrameLayout fl =findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1,fragment);
            fragmentTransaction.commit();
            System.out.println("Profile");

            Toast.makeText(getApplicationContext(),"Event",Toast.LENGTH_SHORT).show();
            return true;

        }
        else if(id == R.id.OffreValidouRef){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmenOffreetServiceValideretRefuser fragment = new FragmenOffreetServiceValideretRefuser();
            FrameLayout fl =findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1,fragment);
            fragmentTransaction.commit();
            System.out.println("OffreValidouRef");

            Toast.makeText(getApplicationContext(),"Event",Toast.LENGTH_SHORT).show();
            return true;

        }
        else if(id == R.id.DemandeValidouRef){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmenOffreetServiceValideretRefuserDemande fragment = new FragmenOffreetServiceValideretRefuserDemande();
            FrameLayout fl =findViewById(R.id.frame1);
            fl.removeAllViews();
            fragmentTransaction.add(R.id.frame1,fragment);
            fragmentTransaction.commit();
            System.out.println("OffreValidouRef");

            Toast.makeText(getApplicationContext(),"Event",Toast.LENGTH_SHORT).show();
            return true;

        }
        else if(id == R.id.logout){
            SharedPreferences sp = getSharedPreferences("shL",0);
            sp.edit().clear().commit();
            Intent logout = new Intent(MainActivity.this,signin.class);
            startActivity(logout);
            Toast.makeText(getApplicationContext(),"Deconnexion",Toast.LENGTH_SHORT).show();
            return true;

        }
        return true;

    }

}


