package com.example.appcubedavid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class InscribirseActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_inscribirse);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.et_nombre_inscribirse, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.et_contraseña1_inscribirse, ".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.et_contraseña2_inscribirse, ".{6,}",R.string.invalid_password);

        EditText nombre = findViewById(R.id.et_nombre_inscribirse);
        EditText contraseña1 = findViewById(R.id.et_contraseña1_inscribirse);
        EditText contraseña2 = findViewById(R.id.et_contraseña2_inscribirse);
        Button registrarse = findViewById(R.id.b_registrarse_inscribirse);

        TextView registrarse_inscribirse = findViewById(R.id.tv_registrarse_inscribirse);
        registrarse_inscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irRegistrarse_inscribirse();
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = nombre.getText().toString();
                String pwd1 = contraseña1.getText().toString();
                String pwd2 = contraseña2.getText().toString();
                
                if(pwd1.equals(pwd2)){
                    if(awesomeValidation.validate()){
                        firebaseAuth.createUserWithEmailAndPassword(mail,pwd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(InscribirseActivity.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                                }else{
                                    String errorCode = ((FirebaseAuthException)task.getException()).getErrorCode();
                                    Toast.makeText(InscribirseActivity.this, errorCode, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(InscribirseActivity.this, "Completa todos los datos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(InscribirseActivity.this, "Asegurate de que ambas contraseñas coinciden", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

    }

    public void irRegistrarse_inscribirse(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}