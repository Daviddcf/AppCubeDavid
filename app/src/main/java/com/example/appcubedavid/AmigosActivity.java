package com.example.appcubedavid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AmigosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        ImageButton ibVolver = findViewById(R.id.b_volver);
        ibVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irHome();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            //listener para detectar cuando se pulsa uno de los botones del menú inferior
            switch (item.getItemId()){//switch que utiliza la id del boton que es pulsado

                case R.id.listaAmigos:

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

    }


    public void irHome(){
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



}