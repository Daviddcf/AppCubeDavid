package com.example.appcubedavid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerPwdActivity extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_restablecer_pwd);

        EditText nombre = findViewById(R.id.et_nombre_resetpwd);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.et_nombre_inscribirse, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);

        Button recuperar = findViewById(R.id.b_registrarse_resetpwd);

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String mail = nombre.getText().toString();

                    auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RestablecerPwdActivity.this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(RestablecerPwdActivity.this, "Correo inválido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(RestablecerPwdActivity.this, "Introduce un formato de correo válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView registrarse_resetpwd = findViewById(R.id.tv_registrarse_resetpwd);
        registrarse_resetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irRegistrarse_resetpwd();
            }
        });


    }

    public void irRegistrarse_resetpwd(){
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}