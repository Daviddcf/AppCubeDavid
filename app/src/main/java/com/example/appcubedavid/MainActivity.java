package com.example.appcubedavid;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {


    int imagenes[]={R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_main);


        ViewPager viewPager = findViewById(R.id.viewPagerInicio);

        ImageView ivdot1 = findViewById(R.id.dot1);
        ImageView ivdot2 = findViewById(R.id.dot2);
        ImageView ivdot3 = findViewById(R.id.dot3);
        ivdot1.setImageResource(R.color.orange);

        Button b_empezar = findViewById(R.id.b_empezar);
        b_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empezar();
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


    public void empezar(){
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}