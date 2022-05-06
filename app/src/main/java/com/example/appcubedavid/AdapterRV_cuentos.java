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

public class AdapterRV_cuentos extends RecyclerView.Adapter<AdapterRV_cuentos.MyViewHolder> {

    Context context;
    ArrayList<ModelCuentos> modelCuentos;


    public AdapterRV_cuentos(Context context, ArrayList<ModelCuentos> modelCuentos){
        this.context = context;
        this.modelCuentos = modelCuentos;
    }

    @NonNull
    @Override
    public AdapterRV_cuentos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_rv_cuentos, parent , false);
        return new AdapterRV_cuentos.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRV_cuentos.MyViewHolder holder, int position) {

        holder.nombre.setText(modelCuentos.get(position).getNombre());
        holder.foto.setImageResource(modelCuentos.get(position).getFoto());
        holder.fotoperfil.setImageResource(modelCuentos.get(position).getFotoPerfil());

    }

    @Override
    public int getItemCount() {
        return modelCuentos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        ImageView foto;
        ImageView fotoperfil;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre_rv_cuentos);
            foto = itemView.findViewById(R.id.iv_foto_rv_cuentos);
            fotoperfil = itemView.findViewById(R.id.iv_foto_perfil_rv_cuentos);


        }
    }

}
