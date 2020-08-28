package com.hatemchaabini.serviceapp;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffreServActivity extends Fragment {
    List<User> lus = new ArrayList<>();
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    INodeJS myAPI;
    String LogiSa;
    int id;
    View rootView;
    Button conf,nonconf;
    ServicesAdapterActivity adapter;
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }

    public void PopulateListeView(List<Service> PostsFeedList){

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.alloffreServices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new AdapterOffre(PostsFeedList,this.getContext()));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences sharedPref ;
        sharedPref = this.getContext().getSharedPreferences("shL",0);
        LogiSa = sharedPref.getString("log","a");
        id = sharedPref.getInt("id",0);
        myAPI =getAPI();


        rootView = inflater.inflate(R.layout.activity_offre_serv, container, false);
        conf = rootView.findViewById(R.id.serviceconf);
        nonconf = rootView.findViewById(R.id.ServiceNonconf);


        Call<List<Service>> call = myAPI.getoffconf(id);
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {

                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                System.out.println(response.body());
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                PopulateListeView(response.body());

            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");

            }
        });
conf.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Call<List<Service>> call = myAPI.getoffconf(id);
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {

                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                System.out.println(response.body());
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                PopulateListeView(response.body());

            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                PopulateListeView(new ArrayList<Service>());

            }
        });
    }
});

        nonconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Service>> call = myAPI.getoffnonconf(id);
                call.enqueue(new Callback<List<Service>>() {
                    @Override
                    public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {

                        System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                        System.out.println(response.body());
                        System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                        PopulateListeView(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<Service>> call, Throwable t) {
                        System.out.println("jjjjjjjjjjjjjjjjj");
                        PopulateListeView(new ArrayList<Service>());

                    }
                });
            }
        });


         /*       Call<List<User>> call = myAPI.getListTout(LogiSa);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                System.out.println(response.body());
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                PopulateListeView(response.body());

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });*/

        return rootView;
    }
}
