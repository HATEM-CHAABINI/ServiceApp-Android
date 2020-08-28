package com.hatemchaabini.serviceapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmenProfile extends Fragment {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_fragmen_profile, container, false);

        final SharedPreferences sharedPref ;
        sharedPref = this.getContext().getSharedPreferences("shL",0);
        LogiSa = sharedPref.getString("log","a");
        myAPI =getAPI();
        adr = rootView.findViewById(R.id.adresseeditp);
        ancmdp = rootView.findViewById(R.id.ancienmdpeditp);
        mdp1 = rootView.findViewById(R.id.mdpeditp);
        mdp2 = rootView.findViewById(R.id.mdp2editp);
        numtel = rootView.findViewById(R.id.numteleditp);
        codep = rootView.findViewById(R.id.codepostaleeditp);
        img = rootView.findViewById(R.id.imageProfileeditp);
        valid = rootView.findViewById(R.id.btnConfirmeredit);
        final Spinner sp =rootView.findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Ariana");
        list.add("Béja");
        list.add("Ben Arous");
        list.add("Bizerte");
        list.add("Gabès");
        list.add("Gafsa");
        list.add("Jendouba");
        list.add("Kairouan");
        list.add("Kasserine");
        list.add("Kébili");
        list.add("Le Kef");
        list.add("Mahdia");
        list.add("La Manouba");
        list.add("Médenine");
        list.add("Monastir");
        list.add("Nabeul");
        list.add("Sfax");
        list.add("Sidi Bouzid");
        list.add("Siliana");
        list.add("Sousse");
        list.add("Tataouine");
        list.add("Tozeur");
        list.add("Tunis");
        list.add("Zaghouan");
        ArrayAdapter<String> adapterv = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, list);
        adapterv.setDropDownViewResource(R.layout.spinner_item);
        sp.setAdapter(adapterv);
        sp.setSelection(list.indexOf(sharedPref.getString("ville","a")));
        adr.setText(sharedPref.getString("adr","a"));
        numtel.setText(sharedPref.getString("tel","a"));
        codep.setText(sharedPref.getString("codep","a"));

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ancmdp.getText().toString().equals("") && !mdp1.getText().toString().equals("") &&  !mdp2.getText().toString().equals("")){
                    Toast.makeText(getContext(),"remplire ancien mot de passe",Toast.LENGTH_SHORT).show();
                }else if (!ancmdp.getText().toString().equals("") && !mdp1.getText().toString().equals("") &&  !mdp2.getText().toString().equals("") && mdp1.getText().toString().equals(mdp2.getText().toString())) {

                    if (controlesaisie.verifmdp(ancmdp.getText().toString()) == false) {
                        Toast.makeText(getContext(), "l ancien mot de passe doit etre composer de minimum 8 caracteres", Toast.LENGTH_SHORT).show();

                    } else if (controlesaisie.verifmdp(mdp1.getText().toString()) == false ) {
                        Toast.makeText(getContext(), "le nouveau mot de passe doit etre composer de minimum 8 caracteres", Toast.LENGTH_SHORT).show();

                    }  else if (controlesaisie.verifcodepostale(codep.getText().toString()) == false) {
                        Toast.makeText(getContext(), "le code postale doit etre compose que de 4 chiffres", Toast.LENGTH_SHORT).show();

                    } else if (controlesaisie.verifisTel(numtel.getText().toString()) == false) {
                        Toast.makeText(getContext(), "le numero de telephone ne doit pas dépasser 8 chiifres", Toast.LENGTH_SHORT).show();
                    }
                    else {

                    Call<String> calll = myAPI.verifpassword(sharedPref.getInt("id", 0), ancmdp.getText().toString());
                    calll.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();


                            System.out.println(response.body());

                            ////update bel password

                            compositeDisposable.add(myAPI.updateproflieavecmdp(mdp1.getText().toString(), adr.getText().toString(), codep.getText().toString(), numtel.getText().toString(), sp.getSelectedItem().toString(), sharedPref.getInt("id", 0))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String s) throws Exception {
                                            Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();

                                        }


                                    }));
                            //finish();
                            final FirebaseUser user;
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            final String email = user.getEmail();
                            AuthCredential credential = EmailAuthProvider.getCredential(email, ancmdp.getText().toString());

                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(mdp1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()) {

                                                }
                                            }
                                        });
                                    } else {

                                    }
                                }
                            });
                        }


                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                } }
            else if (!mdp1.getText().toString().equals(mdp2.getText().toString())){

                    Toast.makeText(getContext(),"verfifer votre nouveau mot de passe",Toast.LENGTH_SHORT).show();


                }
            else
                {
                    compositeDisposable.add(myAPI.updateprofliesansmdp(adr.getText().toString(), codep.getText().toString(), numtel.getText().toString(), sp.getSelectedItem().toString(), sharedPref.getInt("id",0))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();

                                }


                            }));
                    Toast.makeText(getContext(),"mise a jour menghir mdp ",Toast.LENGTH_SHORT).show();

                }
            }
        });

   //     editor.putString("tel",uu.getNumtel());
     //   editor.putString("adr",uu.getAdresse());
   //     editor.putString("mdp",uu.getEncrypted_password());
      //  editor.putString("image",uu.getImage());


        return rootView;
    }
}
