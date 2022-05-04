package com.example.appcubedavid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRV_ChatRecientes extends RecyclerView.Adapter<AdapterRV_ChatRecientes.MyViewHolder> {

    Context context;
    ArrayList<ModelChatRecientes> modelChatRecientes;


    public AdapterRV_ChatRecientes(Context context, ArrayList<ModelChatRecientes> modelChatRecientes){
        this.context = context;
        this.modelChatRecientes = modelChatRecientes;
    }

    @NonNull
    @Override
    public AdapterRV_ChatRecientes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_rv_chat_recientes, parent , false);
        return new AdapterRV_ChatRecientes.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRV_ChatRecientes.MyViewHolder holder, int position) {

        holder.nombre.setText(modelChatRecientes.get(position).getNombre());
        holder.fotoperfil.setImageResource(modelChatRecientes.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return modelChatRecientes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        ImageView fotoperfil;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoperfil = itemView.findViewById(R.id.iv_foto_perfil_chat_reciente);
            nombre = itemView.findViewById(R.id.tv_nombre_chat_reciente);

        }
    }

}
