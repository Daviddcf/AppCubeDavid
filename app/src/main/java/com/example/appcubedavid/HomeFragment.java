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

        Button cerrarSesion = root.findViewById(R.id.b_cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
                irLogin();
            }
        });
        return root;
    }

    public void irLogin(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}