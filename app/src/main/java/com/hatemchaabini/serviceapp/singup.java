package com.hatemchaabini.serviceapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.RetrofitClient;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class singup extends AppCompatActivity {
    private ImageButton btnvalid, btn_an;
    EditText Qte;
    Button btnAttachment;
    Uri URI = null;
    int columnIndex;
    String attachmentFile;
    private Spinner spinner1,spinnert;
    List<LesMetier> lm = new ArrayList<>();
    Uri picUri;
    private Uri imageUri;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    INodeJS myAPI;
   // TextView textView;
    Bitmap mBitmap;
    FirebaseAuth auth;
    DatabaseReference reference;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    private StorageTask uploadTask;
private Controlesaisie controlesaisie= new Controlesaisie();
    CompositeDisposable compositeDisposable =new CompositeDisposable();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            ImageView imageView = findViewById(R.id.imageView);

            if (requestCode == IMAGE_RESULT) {


                String filePath = getImageFilePath(data);
                if (filePath != null) {
                    mBitmap = BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(mBitmap);
                }
            }

        }
    }



    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    private void askPermissions() {
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }
    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else {
            imageUri = data.getData();

            return getPathFromURI(data.getData());
        }
    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }
/*
    private void multipartImageUpload() {
        try {
            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            OutputStream os;
            try {
                os = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();


            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            Call<ResponseBody> req = myAPI.postImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 200) {
                        textView.setText("Uploaded Successfully!");
                        textView.setTextColor(Color.BLUE);
                    }

                    Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    textView.setText("Uploaded Failed!");
                    textView.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  */


    private INodeJS getAPIC() {
        return RetrofitClient.getInstance().create(INodeJS.class);
    }
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }
    private void getAllMetier(){
        compositeDisposable.add(myAPI.getMetierList()
                .subscribe(new Consumer<List<LesMetier>>() {
                    @Override
                    public void accept(List<LesMetier> metier) throws Exception {
                        lm=metier;
                    }
                }));
    }
   /* private void loginUser(String email,String password){
        compositeDisposable.add(myAPI.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        if(s.contains("encrypted_password")){

                            System.out.println("ssssssssssssssssssssss");
                            System.out.println(s);
                            System.out.println("ssssssssssssssssssssss");
                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();}
                        else
                            Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                    }}));
    }*/
   private String getFileExtension(Uri uri){
       ContentResolver contentResolver = singup.this.getContentResolver();
       MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
       return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
   }
    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(singup.this);
        pd.setMessage("Uploading image please wait!!!");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", ""+mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(singup.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(singup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(singup.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
   private void regist( final String email, final String name, final String password, final String username, final String numtel , final String adresse, final String metier, final String ville, final String codepostale, final MultipartBody.Part body, final RequestBody namee)
       {if(metier.equals("Aucune Spécialité"))
   {
       final ProgressDialog pd = new ProgressDialog(singup.this);
       pd.setMessage("Creation du compte veuillez attendre!!!");
       pd.setCanceledOnTouchOutside(false);
       pd.show();

       auth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           firebaseUser = auth.getCurrentUser();
                           assert firebaseUser != null;
                           final String userid = firebaseUser.getUid();

                           reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                           HashMap<String, String> hashMap = new HashMap<>();
                           hashMap.put("id", userid);
                           hashMap.put("username", username);
                           hashMap.put("imageURL", "default");
                           hashMap.put("status", "offline");
                           hashMap.put("search", username.toLowerCase());

                           reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       uploadImage();
                                       Call<ResponseBody> req = myAPI.newpostImage(body, namee,userid, FirebaseInstanceId.getInstance().getToken(),email,name,password,username,numtel,adresse,metier,ville,codepostale,0);
                                       req.enqueue(new Callback<ResponseBody>() {
                                           @Override
                                           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                               if (response.code() == 200) {
                                                   pd.dismiss();

                                                   Toast.makeText(getApplicationContext(), response.code() + "OKI ", Toast.LENGTH_SHORT).show();
                                               }

                                               Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                                           }

                                           @Override
                                           public void onFailure(Call<ResponseBody> call, Throwable t) {
                                     //          textView.setText("Uploaded Failed!");
                                       //        textView.setTextColor(Color.RED);
                                               pd.dismiss();

                                               Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                                               t.printStackTrace();
                                           }
                                       });
                                   }
                               }
                           });
                       } else {
                           Toast.makeText(singup.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();
                       }
                   }
               });





       /*compositeDisposable.add(myAPI.registerUser(userid,email,name,password,username,numtel,adresse,metier,ville,codepostale,0)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Consumer<String>() {
               @Override
               public void accept(String s) throws Exception {
                   Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();

               }
           }));*/

   }else {

           final ProgressDialog pd = new ProgressDialog(singup.this);
           pd.setMessage("Creation du compte veuillez attendre!!!");
           pd.setCanceledOnTouchOutside(false);
           pd.show();
       final View enter_name_view = LayoutInflater.from(this).inflate(R.layout.enter_prix_layout, null);
       new MaterialStyledDialog.Builder(this)
               .setTitle("Register")
               .setDescription("One More Step!")
               .setCustomView(enter_name_view)
               .setIcon(R.drawable.ic_user)
               .setNegativeText("Cancel")
               .onNegative(new MaterialDialog.SingleButtonCallback() {
                   @Override
                   public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                       /*compositeDisposable.add(myAPI.effac()
                               .subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(new Consumer<String>() {
                                   @Override
                                   public void accept(String s) throws Exception {
                                       Toast.makeText(singup.this, "annuler" , Toast.LENGTH_SHORT).show();

                                   }
                               }));
*/
                       pd.dismiss();
                       dialog.dismiss();
                     //  Toast.makeText(singup.this, "sakeeeeerrrr" , Toast.LENGTH_SHORT).show();

                   }
               })
               .setPositiveText("Register")
               .onPositive(new MaterialDialog.SingleButtonCallback() {
                   @Override
                   public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       final MaterialEditText edt_name = enter_name_view.findViewById(R.id.edt_prix);
                       auth.createUserWithEmailAndPassword(email, password)
                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if (task.isSuccessful()){

                                           firebaseUser = auth.getCurrentUser();
                                           assert firebaseUser != null;
                                           final String userid = firebaseUser.getUid();

                                           reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                           HashMap<String, String> hashMap = new HashMap<>();
                                           hashMap.put("id", userid);
                                           hashMap.put("username", username);
                                           hashMap.put("imageURL", "default");
                                           hashMap.put("status", "offline");
                                           hashMap.put("search", username.toLowerCase());

                                           reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if (task.isSuccessful()){
                                                       uploadImage();
                                                       Call<ResponseBody> req = myAPI.newpostImage(body, namee,userid,FirebaseInstanceId.getInstance().getToken(),email,name,password,username,numtel,adresse,metier,ville,codepostale,Float.parseFloat(edt_name.getText().toString()));
                                                       req.enqueue(new Callback<ResponseBody>() {
                                                           @Override
                                                           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                               if (response.code() == 200) {
                                                                   pd.dismiss();
                                                                   Toast.makeText(getApplicationContext(), response.code() + "OKI ", Toast.LENGTH_SHORT).show();
                                                               }

                                                               Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                                                           }

                                                           @Override
                                                           public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                               //textView.setText("Uploaded Failed!");
                                                               //textView.setTextColor(Color.RED);
                                                               Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                                                               t.printStackTrace();
                                                           }
                                                       });


                                                   }
                                               }
                                           });
                                       } else {
                                           Toast.makeText(singup.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });



                    /*   compositeDisposable.add(myAPI.registerUser(userid,email, name, password, username, numtel, adresse, metier, ville, codepostale,Float.parseFloat(edt_name.getText().toString()))
                               .subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(new Consumer<String>() {
                                   @Override
                                   public void accept(String s) throws Exception {
                                       Toast.makeText(singup.this, "" + s, Toast.LENGTH_SHORT).show();

                                   }


                               }));*/
                   }
               }).show();


   }}

    private void registerUser(final String email, final String name,  final String password,final String username, final String numtel ,final String adresse, final String metier, final String ville, final String codepostale){
        compositeDisposable.add(myAPI.verifuser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                        String ss = ""+s;

if(!s.contains("User already exists!!!")){









    System.out.println("User already exists!!!");
    System.out.println(""+s);

        try {
            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            OutputStream os;
            try {
                os = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();


            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody namee = RequestBody.create(MediaType.parse("text/plain"), "upload");


                  regist(email, name, password, username, numtel, adresse, metier, ville, codepostale,body,namee);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } }
else {
    System.out.println("exists");}
                    }
                }));

    /*  if(metier.equals("Aucune Spécialité"))
{    compositeDisposable.add(myAPI.registerUser(userid,email,name,password,username,numtel,adresse,metier,ville,codepostale,0)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();

            }
        }));

}else {
    final View enter_name_view = LayoutInflater.from(this).inflate(R.layout.enter_prix_layout, null);
    new MaterialStyledDialog.Builder(this)
            .setTitle("Register")
            .setDescription("One More Step!")
            .setCustomView(enter_name_view)
            .setIcon(R.drawable.ic_user)
            .setNegativeText("Cancel")
            .onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            })
            .setPositiveText("Register")
            .onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    MaterialEditText edt_name = enter_name_view.findViewById(R.id.edt_prix);
                    compositeDisposable.add(myAPI.registerUser(userid,email, name, password, username, numtel, adresse, metier, ville, codepostale,Float.parseFloat(edt_name.getText().toString()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    Toast.makeText(singup.this, "" + s, Toast.LENGTH_SHORT).show();

                                }
                            }));
                }
            }).show();


}*/



                    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        auth = FirebaseAuth.getInstance();
        myAPI =getAPI();
        getAllMetier();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

     //   textView = findViewById(R.id.textView);
        askPermissions();

        final Spinner spm = findViewById(R.id.metier);
        List<String> listmetier = new ArrayList<String>();
        listmetier.add("Aucune Spécialité");
        for(LesMetier nn:lm){
            listmetier.add(nn.getMetier());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, listmetier);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spm.setAdapter(adapter);
        ImageView img = findViewById(R.id.Image);
        final int PICK_IMAGE = 1;
Button va = findViewById(R.id.valider);
        final Spinner sp =findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Gouvernorat");
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
        ArrayAdapter<String> adapterv = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        sp.setAdapter(adapterv);
        myAPI =getAPIC();
        final EditText nom = findViewById(R.id.nom);
        final EditText username = findViewById(R.id.username);
        final EditText email = findViewById(R.id.email);
        final  EditText numtel = findViewById(R.id.numtel);
        final EditText mdp1 = findViewById(R.id.mdp1);
        final  EditText mdp2 = findViewById(R.id.mdp2);
        final  EditText adresse = findViewById(R.id.adresse);
        final EditText codepostale = findViewById(R.id.codepostale);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);

            }
        });
        va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ville = sp.getSelectedItem().toString();
                final  String metier = spm.getSelectedItem().toString();
                if (!mdp1.getText().toString().equals(mdp2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Verifier votre mot de passe", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().equals("")|| nom.getText().toString().equals("")||username.getText().toString().equals("")|| numtel.getText().toString().equals("")||adresse.getText().toString().equals("")||ville.equals("Gouvernorat")||codepostale.getText().toString().equals("")||mdp1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Veuillez remplire tous les champs", Toast.LENGTH_SHORT).show();
                }
                  else if (controlesaisie.verifusername(username.getText().toString())== false)  {
                    Toast.makeText(getApplicationContext(), "le nom d'utilisateur doit etre compose que de lettre", Toast.LENGTH_SHORT).show();

                }
                else if (controlesaisie.verifcodepostale(codepostale.getText().toString())== false)  {
                    Toast.makeText(getApplicationContext(), "le code postale doit etre compose que de 4 chiffres", Toast.LENGTH_SHORT).show();

                }
                else if (controlesaisie.verifisTel(numtel.getText().toString())== false)  {
                    Toast.makeText(getApplicationContext(), "le numero de telephone ne doit pas dépasser 8 chiifres", Toast.LENGTH_SHORT).show();

                }
                else if (controlesaisie.verifmdp(mdp1.getText().toString())== false)  {
                    Toast.makeText(getApplicationContext(), "le mot de passe doit etre composer de minimum 8 caracteres", Toast.LENGTH_SHORT).show();

                }
                else if (controlesaisie.verifnom(nom.getText().toString())== false)  {
                    Toast.makeText(getApplicationContext(), "le nom doit etre compose que de lettre", Toast.LENGTH_SHORT).show();

                }
                else if (controlesaisie.verifemail(email.getText().toString())== false)  {
                    Toast.makeText(getApplicationContext(), "votre email n'est pas valide", Toast.LENGTH_SHORT).show();

                }
                else
                if (mBitmap != null) {
              //      multipartImageUpload();
                    registerUser(email.getText().toString(), nom.getText().toString(), mdp1.getText().toString(), username.getText().toString(), numtel.getText().toString(), adresse.getText().toString(), metier, ville, codepostale.getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Bitmap is null. Try again", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
