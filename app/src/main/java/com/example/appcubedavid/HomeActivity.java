package com.example.appcubedavid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    //arrays que guardan los datos que luego se muestran en el slider de "Cuentos"
    int fotoHistoria[]={R.drawable.gente1, R.drawable.gente2, R.drawable.gente3, R.drawable.gente4, R.drawable.gente5, R.drawable.gente6 };
    String nombres[]={"Hurin Seary","Victor Exrix","Eduardo Kelly","Pepe","Manuel","Pablo"};
    int fotosPerfil[]={R.drawable.gente1, R.drawable.gente2, R.drawable.gente3, R.drawable.gente4, R.drawable.gente5, R.drawable.gente6 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            //listener para detectar cuando se pulsa uno de los botones del menú inferior
            switch (item.getItemId()){//switch que utiliza la id del boton que es pulsado

                case R.id.listaAmigos:
                    irAmigos();
                    break;
                case R.id.infoUsuario:
                    //ir a información sobre el usuario
                    Toast.makeText(this, "Ir a informacion sobre el usuario", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.chat:
                    //ir al chat
                    Toast.makeText(this, "Ir al chat", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ajustes:
                    //ir a ajustes
                    Toast.makeText(this, "Ir a ajustes", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

        ViewPager viewPager = findViewById(R.id.viewPagerHome);

        AdapterViewPagerHome adapterViewPagerHome = new AdapterViewPagerHome(this, fotoHistoria,nombres,fotosPerfil);
        viewPager.setAdapter(adapterViewPagerHome);

        Button cerrarSesion = findViewById(R.id.b_cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                irLogin();
            }
        });

    }

    public void irLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void irAmigos(){
        Intent i = new Intent(this, AmigosActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


}