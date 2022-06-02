package com.example.appcubedavid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class PerfilFragment extends Fragment {

    LoginActivity loginActivity= new LoginActivity();

    //GoogleSignInClient googleSignInClient = loginActivity.getGoogleSignInClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);


        ImageButton volver = root.findViewById(R.id.b_volver_perfil);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutFragments, homeFragment);
                transaction.commit();

                // Establecer como activo el item de home del BottomNavigationMenu
                HomeActivity.bottomNavigationView.getMenu().findItem(R.id.pantallaHome).setChecked(true);
            }

        });


        return root;
    }





}