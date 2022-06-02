package com.example.appcubedavid;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AjustesFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    private Switch modoscuro;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);

        modoscuro = root.findViewById(R.id.switch_modoscuro);

        ImageButton volver = root.findViewById(R.id.b_volver_ajustes);
        CardView desconectar = root.findViewById(R.id.cd_desconectar);
        CardView resetPWD = root.findViewById(R.id.cd_restablecerPwd);
        CardView eliminarCuenta = root.findViewById(R.id.cd_eliminarCuenta);
        CardView cambiarIdioma = root.findViewById(R.id.cd_cambiarIdioma);


        //conseguir el usuario que esta actualmente logueado
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el fragment a home
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutFragments, homeFragment);
                transaction.commit();

                // Establecer como activo el item de home del BottomNavigationMenu
                HomeActivity.bottomNavigationView.getMenu().findItem(R.id.pantallaHome).setChecked(true);
            }

        });

        desconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();//cerrar sesión de firebase

                googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("225955144223-tetp006hqbjsu59l91asn8ospnd57vmh.apps.googleusercontent.com")//(R.string.default_web_client_id)
                        /*dentro de .requestIdToken() se debería de poner (R.string.default_web_client_id) el cual, en teoría se debería de generar automáticamente
                         * en "strings.xml". En mi caso no lo hace (no he averiguado el motivo) y la solución provisional que he encontrado es copiar el "client_id"
                         * de "google-services.json" directamente dentro de .requestIdToken()*/
                        .requestEmail()
                        .build();

                GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(getActivity(),googleSignInOptions);
                googleSignInClient.signOut();
                Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

                irLogin();
            }
        });

        resetPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irResetPWD();
            }
        });

        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("¿Estas seguro?");
                dialog.setMessage("Esta acción borrará tu cuenta permanente mente. ¿Estas seguro de que deseas hacerlo?");


                dialog.setPositiveButton("Borrar cuenta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //borar el usuario de la base de datos de firebase
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Cuenta borrada correctamente", Toast.LENGTH_SHORT).show();
                                    irLogin();
                                    //si la cuenta se ha podido borrar correctamente se vuelve a la pantalla de login
                                }else{
                                    Toast.makeText(getActivity(), "ERROR: la cuenta no ha sido borrada", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Borrar cuenta firebase / onComplete: "+task.getException().getMessage());
                                    //si la cuenta no se ha podido borrar correctamente enseña el mensaje de error de la task
                                }
                            }
                        });
                    }
                });

                dialog.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //mostrar el alertdialog
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();


            }
        });

        
        modoscuro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    MainActivity.ActivarModoscuro(getActivity());
                    // Establecer como activo el item de home del BottomNavigationMenu
                    HomeActivity.bottomNavigationView.getMenu().findItem(R.id.pantallaHome).setChecked(true);
                }else{
                    MainActivity.DesactivarModoscuro(getActivity());
                    // Establecer como activo el item de home del BottomNavigationMenu
                    HomeActivity.bottomNavigationView.getMenu().findItem(R.id.pantallaHome).setChecked(true);
                }
            }
        });

        cambiarIdioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el fragment a idiomasFragment
                IdiomasFragment idiomasFragment = new IdiomasFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutFragments, idiomasFragment);
                transaction.commit();

                // Establecer como activo el item de ajustes del BottomNavigationMenu
                HomeActivity.bottomNavigationView.getMenu().findItem(R.id.ajustes).setChecked(true);
            }
        });

        return root;




    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);

        if(preferences.getBoolean("darkmode",false) == true){
            modoscuro.setChecked(true);

        }else {
            modoscuro.setChecked(false);

        }
    }


    public void irLogin(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void irResetPWD(){
        Intent i = new Intent(getContext(), RestablecerPwdActivity.class);
        startActivity(i);
    }


}