package com.example.appcubedavid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class AdapterViewPager extends PagerAdapter {
    Context context;
    int[] imagenes;
    LayoutInflater layoutInflater;

    public AdapterViewPager(Context context, int[] imagenes){
        this.context=context;
        this.imagenes=imagenes;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagenes.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==((LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = layoutInflater.inflate(R.layout.itemviewpager, container, false);
        ImageView imageview = (ImageView) v.findViewById(R.id.iv_itemviewpager);
        imageview.setImageResource(imagenes[position]);
        imageview.setScaleType(ImageView.ScaleType.FIT_XY);//adaptar el tamaño de las imagenes dentro del imagepager
        container.addView(v);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image "+(position + 1),Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
