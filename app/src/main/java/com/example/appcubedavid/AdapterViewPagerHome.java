package com.example.appcubedavid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class AdapterViewPagerHome extends PagerAdapter {

    Context context;
    int[] imagenes;
    LayoutInflater layoutInflater;
    String[] nombres;
    int[] fotosPerfil;

    public AdapterViewPagerHome(Context context, int[] imagenes, String[]nombres,int[] fotosPerfil){
        this.context=context;
        this.imagenes=imagenes;
        this.nombres=nombres;
        this.fotosPerfil=fotosPerfil;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagenes.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = layoutInflater.inflate(R.layout.itemviewpagerhome, container, false);

        ImageView ivFoto = (ImageView) v.findViewById(R.id.iv_itemviewpagerhome);
        ivFoto.setImageResource(imagenes[position]);
        ivFoto.setScaleType(ImageView.ScaleType.FIT_XY);//adaptar el tamaño de las imagenes dentro del imagepager

        TextView tvNombre = (TextView) v.findViewById(R.id.tv_nombreITEM);
        tvNombre.setText(nombres[position]);

        ImageView ivFotoPerfil = (ImageView) v.findViewById(R.id.iv_fotoperfilCuento);
        ivFotoPerfil.setImageResource(fotosPerfil[position]);
        ivFotoPerfil.setScaleType(ImageView.ScaleType.FIT_XY);//adaptar el tamaño de las imagenes dentro del imagepager



        container.addView(v);
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image "+(position + 1),Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
