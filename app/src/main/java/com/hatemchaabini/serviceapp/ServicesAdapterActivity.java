package com.hatemchaabini.serviceapp;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatemchaabini.serviceapp.Retrofit.INodeJS;
import com.hatemchaabini.serviceapp.Retrofit.Retrofits;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesAdapterActivity extends RecyclerView.Adapter<ServicesAdapterActivity.MyViewHolder> {
        List<User> personList;
private Context mContext;
    INodeJS myAPI;
    private INodeJS getAPI() {
        return Retrofits.getInstance().create(INodeJS.class);
    }
public ServicesAdapterActivity(List<User> personList,Context context) {

        this.personList = personList;
        this.mContext = context;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_services_adapter,parent,false);
        return new MyViewHolder(itemView);
        }

@Override
public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(personList.get(position).getMetier());
        holder.email.setText(personList.get(position).getEmail());
        holder.phone.setText(personList.get(position).getNumtel());
    myAPI =getAPI();

 System.out.println(personList.get(position).getImage());

    Call<ResponseBody> call = myAPI.getImage(personList.get(position).getImage());
    call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    // display the image data in a ImageView or save it
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    holder.img.setImageBitmap(bmp);
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
}



@Override
public int getItemCount() {
        return personList.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    CardView root_view;
    TextView name,phone,email;
    ImageView img;
    public MyViewHolder(@NonNull final View itemView) {
        super(itemView);
        root_view = (CardView)itemView.findViewById(R.id.root_view);
root_view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        name= itemView.findViewById(R.id.txt_name);
        phone= itemView.findViewById(R.id.txt_phone);
        email=itemView.findViewById(R.id.txt_email);
        img=itemView.findViewById(R.id.image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(v.getContext(), DetailserviceActivity.class);
           intent.putExtra("email",email.getText().toString());
                System.out.println(email.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
    }
}
}
