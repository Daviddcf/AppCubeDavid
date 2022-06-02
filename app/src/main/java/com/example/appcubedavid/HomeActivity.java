package com.example.appcubedavid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;

    //bottomNavigationView.getMenu().findItem(R.id.pantallaHome).setChecked(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cambiarFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            //listener para detectar cuando se pulsa uno de los botones del menú inferior
            switch (item.getItemId()){//switch que utiliza la id del boton que es pulsado

                case R.id.pantallaHome:
                    cambiarFragment(new HomeFragment());
                    break;
                case R.id.listaAmigos:
                    cambiarFragment(new AmigosFragment());
                    break;
                case R.id.infoUsuario:
                    cambiarFragment(new PerfilFragment());
                    break;
                case R.id.chat:
                    cambiarFragment(new ChatFragment());
                    break;
                case R.id.ajustes:
                    cambiarFragment(new AjustesFragment());
                    break;
            }
            return true;
        });

    }

    public void cambiarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutFragments,fragment);
        fragmentTransaction.commit();
    }


}