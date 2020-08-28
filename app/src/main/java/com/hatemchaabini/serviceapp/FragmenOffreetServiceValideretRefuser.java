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
import android.widget.EditText;


import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmenOffreetServiceValideretRefuser extends Fragment {

    INodeJS myAPI;
    String LogiSa;
    View rootView;
    Button valid;
    EditText adr,ancmdp,mdp1,mdp2,numtel,codep;
    CircleImageView img;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    private Controlesaisie controlesaisie= new Controlesaisie();

    ServicesAdapterActivity adapter;
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }

    public void PopulateListeView(List<ServiceStatut> PostsFeedList){

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.offretServicesvaetref);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new OffreetServiceValideretRefuserAdapterActivity(PostsFeedList,this.getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_fragmen_offreet_service_valideret_refuser, container, false);

        final SharedPreferences sharedPref ;
        sharedPref = this.getContext().getSharedPreferences("shL",0);
        LogiSa = sharedPref.getString("log","a");
        myAPI =getAPI();
        Call<List<ServiceStatut>> call = myAPI.affServiceOffertValideretrefuser(sharedPref.getInt("id",0));
        call.enqueue(new Callback<List<ServiceStatut>>() {
            @Override
            public void onResponse(Call<List<ServiceStatut>> call, Response<List<ServiceStatut>> response) {
                PopulateListeView(response.body());

            }

            @Override
            public void onFailure(Call<List<ServiceStatut>> call, Throwable t) {

            }
        });
        return rootView;
    }
    }
