package com.example.appcubedavid;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class HomeFragment extends Fragment {

    //arrays que guardan los datos que luego se muestran en el slider de "Cuentos"
    int fotoHistoria[]={R.drawable.gente1, R.drawable.gente2, R.drawable.gente3, R.drawable.gente4, R.drawable.gente5, R.drawable.gente6 };
    String nombres[]={"Hurin Seary","Victor Exrix","Eduardo Kelly","Pepe","Manuel","Pablo"};
    int fotosPerfil[]={R.drawable.gente1, R.drawable.gente2, R.drawable.gente3, R.drawable.gente4, R.drawable.gente5, R.drawable.gente6 };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager viewPager = root.findViewById(R.id.viewPagerHome);

        AdapterViewPagerHome adapterViewPagerHome = new AdapterViewPagerHome(getContext(), fotoHistoria,nombres,fotosPerfil);
        viewPager.setAdapter(adapterViewPagerHome);


        return root;
    }



}