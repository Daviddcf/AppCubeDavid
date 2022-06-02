package com.example.appcubedavid;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class IdiomasFragment extends Fragment {

    private Spinner spinnerIdiomas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_idiomas, container, false);

        Button cambiarIdioma = root.findViewById(R.id.b_cambiarIdioma);
        ImageButton volver = root.findViewById(R.id.b_volver_idiomas);


        String[] arrayIdiomas ={"en", "es"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayIdiomas);
        spinnerIdiomas = root.findViewById(R.id.sp_idiomas);
        spinnerIdiomas.setAdapter(adaptador);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el fragment a idiomasFragment
                AjustesFragment ajustesFragment = new AjustesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutFragments, ajustesFragment);
                transaction.commit();

                // Establecer como activo el item de ajustes del BottomNavigationMenu
                HomeActivity.bottomNavigationView.getMenu().findItem(R.id.ajustes).setChecked(true);
            }
        });


        spinnerIdiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idioma = parent.getItemAtPosition(position).toString();

                if(idioma.equals("English")){
                    setLocal(getActivity(),"en");
                }else if (idioma.equals("Español")){
                    setLocal(getActivity(),"es");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return root;
    }

    public void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

}