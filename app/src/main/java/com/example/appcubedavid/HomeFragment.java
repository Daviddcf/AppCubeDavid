package com.example.appcubedavid;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    //arrays que guardan los datos que luego se muestran en el slider de "Cuentos"
    ArrayList<ModelCuentos> modelCuentos = new ArrayList<>();
    String nombres[]={"Hurin Seary","Victor Exrix","Eduardo Kelly","Pepe","Manuel","Pablo"};
    int fotos[]={R.drawable.gente6, R.drawable.gente5, R.drawable.gente4, R.drawable.gente3, R.drawable.gente2, R.drawable.gente1 };
    int fotosPerfil[]={R.drawable.gente1, R.drawable.gente2, R.drawable.gente3, R.drawable.gente4, R.drawable.gente5, R.drawable.gente6 };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rv_cuentos);

        rellenarRvChatRecientes();

        AdapterRV_cuentos adapterRV = new AdapterRV_cuentos(getActivity(), modelCuentos);

        recyclerView.setAdapter(adapterRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));


        return root;
    }

    public void rellenarRvChatRecientes(){

        for(int i = 0; i< nombres.length;i++){
            modelCuentos.add(new ModelCuentos(nombres[i], fotos[i], fotosPerfil[i]));
        }

    }

}