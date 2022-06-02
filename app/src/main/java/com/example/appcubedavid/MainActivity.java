package com.example.appcubedavid;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    int imagenes[]={R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};


    static boolean DarkMode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_main);



        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        //comprobar si la app es abierta por primera vez
        boolean FirstTime = preferences.getBoolean("firststart", true);
        DarkMode = preferences.getBoolean("darkmode", false);

        if(DarkMode == true){
            ActivarModoscuro(this);
        }else{
            DesactivarModoscuro(this);
        }

        if(FirstTime==false){


            //si ya hay una sesión iniciada se va directamente a home
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user!=null){


                irHome();//si no es la primera vez que se abre la app Y hay una sesión iniciada se va a la pantalla de home
                Log.d(TAG, "MainActivity-onCreate: hay un usuario => se va a home"+user.getEmail());


            }else{
                irLogin();//si no es la primera vez que se abre la app Y no hay una sesión iniciada se va a la pantalla de login directamente
                Log.d(TAG, "MainActivity-onCreate: no hay un usuario => se va a login");
            }


        }else {//si es la primera vez que se abre la app se muestra la pantalla de introducción y se anota en preferences que la app ya se ha abierto por primera vez
            SharedPreferences.Editor editor = preferences.edit();
            //editor.putString("FirstTimeInstall", "Yes");
            editor.putBoolean("firststart", false);
            editor.apply();
        }





        ViewPager viewPager = findViewById(R.id.viewPagerInicio);
        ImageView ivdot1 = findViewById(R.id.dot1);
        ImageView ivdot2 = findViewById(R.id.dot2);
        ImageView ivdot3 = findViewById(R.id.dot3);
        ivdot1.setImageResource(R.color.orange);

        Button b_empezar = findViewById(R.id.b_empezar);
        b_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irLogin();
            }
        });

        AdapterViewPager adapterViewPager = new AdapterViewPager(this,imagenes);
        viewPager.setAdapter(adapterViewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {//cambia el color de los circulos que marcan en que foto esta
                if(position==0){
                    ivdot1.setImageResource(R.color.orange);
                    ivdot2.setImageResource(R.color.grey);
                    ivdot3.setImageResource(R.color.grey);
                }else if(position==1){
                    ivdot1.setImageResource(R.color.grey);
                    ivdot2.setImageResource(R.color.orange);
                    ivdot3.setImageResource(R.color.grey);
                }else if(position==2){
                    ivdot1.setImageResource(R.color.grey);
                    ivdot2.setImageResource(R.color.grey);
                    ivdot3.setImageResource(R.color.orange);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





    }

    public static void ActivarModoscuro(Context context){
        // Activar el modo oscuro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        SharedPreferences preferences = context.getSharedPreferences("PREFERENCE", context.MODE_PRIVATE);

        // Almacenar en preferencias que el modo oscuro esta activado
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("darkmode", true);
        editor.apply();
    }

    public static void DesactivarModoscuro(Context context){
        // Desactivar el modo oscuro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SharedPreferences preferences = context.getSharedPreferences("PREFERENCE", context.MODE_PRIVATE);

        // Almacenar en preferencias que el modo oscuro esta desactivado
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("darkmode", false);
        editor.apply();
    }

    public void irLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void irHome(){
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}