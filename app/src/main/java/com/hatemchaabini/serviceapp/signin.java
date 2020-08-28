package com.hatemchaabini.serviceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class signin extends AppCompatActivity {
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    INodeJS myAPI;
    FirebaseAuth auth;

    SharedPreferences sharedPref ;
    static User uu = new User();
private User changeStringUser(String s){
    User uu = new User();
    uu.setId(Integer.parseInt(s.substring(s.indexOf(":")+1,s.indexOf(","))));
    s=s.substring(s.indexOf(",")+1);

    uu.setFirebaseid(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setToken(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setEmail(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setName(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setUsername(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setEncrypted_password(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setSalt(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setNumtel(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setAdresse(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setMetier(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setVille(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setCodepostale(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    uu.setPrix(Float.parseFloat(s.substring(s.indexOf(":")+1,s.indexOf(","))));
    s=s.substring(s.indexOf(",")+1);
    uu.setImage(s.substring(s.indexOf(":")+2,s.indexOf(",")-1));
    s=s.substring(s.indexOf(",")+1);
    return uu;
}
    private INodeJS getAPIC() {
        return RetrofitClient.getInstance().create(INodeJS.class);
    }
    private void loginUser(final String email, final String password){
        compositeDisposable.add(myAPI.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        if(s.contains("encrypted_password")){

                            auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                           //     Intent intent = new Intent(signin.this, MaineActivity.class);
                                             //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                             //   startActivity(intent);
                                               // finish();
                                            } else {
                                                Toast.makeText(signin.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
uu =changeStringUser(s);
                            System.out.println("ssssssssssssssssssssss");
                            System.out.println(s);

                            System.out.println(uu.getImage());
                            System.out.println("ssssssssssssssssssssss");
                            sharedPref = getApplicationContext().getSharedPreferences("shL",0);

                            SharedPreferences.Editor editor = sharedPref.edit();

                            editor.putInt("id",uu.getId());

                            editor.putString("log",uu.getEmail());
                            editor.putString("nom",uu.getName());
                            editor.putString("tel",uu.getNumtel());
                            editor.putString("adr",uu.getAdresse());
                            editor.putString("mdp",uu.getEncrypted_password());
                            editor.putString("image",uu.getImage());
                            editor.putString("codep",uu.getCodepostale());
                            editor.putString("ville",uu.getVille());

                            editor.putString("firebaseid",uu.getFirebaseid());

                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                            startActivity(intent);

                           Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                            }
                        else
                            Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                    }}));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        auth = FirebaseAuth.getInstance();
        System.out.println("ttoooooooooookkkkeeeennnnnnnnnnnnnn");
        System.out.println(FirebaseInstanceId.getInstance().getToken());
        System.out.println("ttoooooooooookkkkeeeennnnnnnnnnnnnn");
        sharedPref = getSharedPreferences("shL",0);
        String LogiSa = sharedPref.getString("log","");
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (!LogiSa.equals("")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
        }
        myAPI =getAPIC();
        final EditText email = findViewById(R.id.usrusr);
        final EditText pwd = findViewById(R.id.pswrd);
email.setText(LogiSa);
        Button singup = findViewById(R.id.signUp);
        Button signin =findViewById(R.id.SignIn);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), singup.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(email.getText().toString(), pwd.getText().toString());
            }
        });
    }
}




