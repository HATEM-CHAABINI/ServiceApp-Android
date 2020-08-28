package com.hatemchaabini.serviceapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoTuto extends Fragment implements SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    List<Video> youtubeVideos = new ArrayList<>();
    View rootView;
    INodeJS myAPI;
    List<LesMetier> lm = new ArrayList<>();

    FloatingActionButton addnewVideo;
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }
    CompositeDisposable compositeDisposable =new CompositeDisposable();

    public void PopulateListeView(List<Video> PostsFeedList){
        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new VideoAdapter(PostsFeedList));

    }
    private void getAllMetier(){
        compositeDisposable.add(myAPI.getMetierList()
                .subscribe(new Consumer<List<LesMetier>>() {
                    @Override
                    public void accept(List<LesMetier> metier) throws Exception {
                        lm=metier;
                    }
                }));}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        final SharedPreferences sharedPref ;
        sharedPref = this.getContext().getSharedPreferences("shL",0);
        rootView = inflater.inflate(R.layout.activity_video_tuto, container, false);
        myAPI =getAPI();
        getAllMetier();

        addnewVideo = rootView.findViewById(R.id.addnewvideo);
        addnewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View enter_name_view = LayoutInflater.from(getContext()).inflate(R.layout.entrer_lien_vider, null);
                final Spinner spm = enter_name_view.findViewById(R.id.metierv);
                List<String> listmetier = new ArrayList<String>();
                for(LesMetier nn:lm){
                    listmetier.add(nn.getMetier());
                }
                listmetier.remove(0);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, listmetier);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                spm.setAdapter(adapter);

                new MaterialStyledDialog.Builder(getContext())
                        .setTitle("Ajouter une Video")
                        .setCustomView(enter_name_view)
                        .setIcon(R.drawable.ic_videoadd)
                        .setNegativeText("Annuler")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("Ajouter")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                MaterialEditText edt_name = enter_name_view.findViewById(R.id.edt_lien);

if(!edt_name.getText().toString().equals("")) {

    String lien = edt_name.getText().toString();
    lien = lien.substring(lien.indexOf("=")+1,lien.length());
    System.out.println("liiiiiiiiiieeenennnn");
    System.out.println(lien);
    System.out.println("liiiiiiiiiieeenennnn");
    String path = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+lien+"\" frameborder=\"0\" allowfullscreen></iframe>";
    compositeDisposable.add(myAPI.addvideo(sharedPref.getInt("id", 0), path, spm.getSelectedItem().toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();

                }


            }));
}
else
{                    Toast.makeText(getContext(), "Veuillez inser un lien youtube avant d'ajouter", Toast.LENGTH_SHORT).show();
}




                                System.out.println(edt_name.getText().toString());
                                /*compositeDisposable.add(myAPI.registerUser(email, name, password, username, numtel, adresse, metier, ville, codepostale,Float.parseFloat(edt_name.getText().toString()))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<String>() {
                                            @Override
                                            public void accept(String s) throws Exception {
                                                Toast.makeText(youtubeVideos.this, "" + s, Toast.LENGTH_SHORT).show();

                                            }
                                        }));*/
                            }
                        }).show();

            }
        });
      /*
      *   addnewVideo = rootView.findViewById(R.id.addnewvideo);
        addnewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("neeeeeeeeeeeeeeeeeeew");
            }
        });
      * */
        final Spinner spmv = rootView.findViewById(R.id.spinnerChoix);
        List<String> listmetier = new ArrayList<String>();
        for(LesMetier nn:lm){
            listmetier.add(nn.getMetier());
        }
        listmetier.remove(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, listmetier);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spmv.setAdapter(adapter);

spmv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Call<List<Video>> call = myAPI.RechercheVideo(spmv.getSelectedItem().toString());
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                System.out.println(response.body());
                System.out.println("huhuhhuhuhuhuhuhuhuhuhuuh");
                PopulateListeView(new ArrayList<Video>());

                PopulateListeView(response.body());

            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                PopulateListeView(new ArrayList<Video>());


            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});





      /*  youtubeVideos.add( new Video(1,1,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/04854XqcfCY\" frameborder=\"0\" allowfullscreen></iframe>","a") );
        youtubeVideos.add( new Video(1,1,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KyJ71G2UxTQ\" frameborder=\"0\" allowfullscreen></iframe>","a") );
        youtubeVideos.add( new Video(1,1,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/y8Rr39jKFKU\" frameborder=\"0\" allowfullscreen></iframe>","a") );
        youtubeVideos.add( new Video(1,1,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/8Hg1tqIwIfI\" frameborder=\"0\" allowfullscreen></iframe>","a") );
        youtubeVideos.add( new Video(1,1,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uhQ7mh_o_cM\" frameborder=\"0\" allowfullscreen></iframe>","a") );
*/

        return rootView;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
