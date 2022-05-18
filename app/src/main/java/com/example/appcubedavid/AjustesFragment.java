package com.example.appcubedavid;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AjustesFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);

        ImageButton volver = root.findViewById(R.id.b_volver_ajustes);
        ImageButton desconectar = root.findViewById(R.id.ib_desconectar);
        ImageButton resetPWD = root.findViewById(R.id.ib_cambiarContraseña);
        ImageButton eliminarCuenta = root.findViewById(R.id.ib_eliminarCuentas);

        //conseguir el usuario que esta actualmente logueado
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutFragments, homeFragment);
                transaction.commit();
            }

        });

        desconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();//cerrar sesión de firebase
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

        return root;
    }

    public void irLogin(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void irResetPWD(){
        Intent i = new Intent(getContext(), RestablecerPwdActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


}