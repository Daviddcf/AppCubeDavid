package com.example.appcubedavid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;


public class ChatFragment extends Fragment {


    ArrayList<ModelChatRecientes> modelChatRecientes = new ArrayList<>();
    String nombres[]={"Hurin Seary","Victor Exrix","Eduardo Kelly","Pepe","Manuel","Pablo"};
    int fotosPerfil[]={R.drawable.gente1, R.drawable.gente2, R.drawable.gente3, R.drawable.gente4, R.drawable.gente5, R.drawable.gente6 };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rv_chat_recientes);

        rellenarRvChatRecientes();

        AdapterRV_ChatRecientes adapterRV = new AdapterRV_ChatRecientes(getActivity(), modelChatRecientes);

        recyclerView.setAdapter(adapterRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        ImageButton volver = root.findViewById(R.id.b_volver_chat);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutFragments, homeFragment);
                transaction.commit();

                // Establecer como activo el item de home del BottomNavigationMenu
                HomeActivity.bottomNavigationView.getMenu().findItem(R.id.pantallaHome).setChecked(true);
            }

        });

        return root;
    }

    public void rellenarRvChatRecientes(){

        for(int i = 0; i< nombres.length;i++){
            modelChatRecientes.add(new ModelChatRecientes(nombres[i], fotosPerfil[i]));
        }

    }

}