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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_login);

        //Declaración de variables de las vistas
        Button btnIniciarSesion = findViewById(R.id.b_iniciarsesion);
        Button btnGoogle = findViewById(R.id.b_google);
        Button btnFacebook = findViewById(R.id.b_facebook);
        EditText nombre = findViewById(R.id.et_nombre_login);
        EditText contraseña = findViewById(R.id.et_contraseña_login);
        TextView resetpwd = findViewById(R.id.tv_resetpwd);

        //clickar "¿Se te olvido tu contraseña?" >>> ir a RestablecerPwdActivity
        resetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irResetPwd();
            }
        });

        //clickar "Inscribirse." >>> ir a InscribirseActivity
        TextView inscribirse = findViewById(R.id.tv_inscribirse);
        inscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irInscribirse();
            }
        });

        //si ya hay una sesión iniciada no se va a la pantalla de login
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            irahome();
        }

        //con awesomeValidation compruebo que el correo tenga un patrón válido y que las contraseñas tengan al menos 6 carácteres
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.et_nombre_inscribirse, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.et_contraseña1_inscribirse, ".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.et_contraseña2_inscribirse, ".{6,}",R.string.invalid_password);

        //LOGIN CON EMAIL Y CONTRASEÑA
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    String mail = nombre.getText().toString();
                    String pwd = contraseña.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                irahome();
                            }else{
                                String errorCode = ((FirebaseAuthException)task.getException()).getErrorCode();
                                Toast.makeText(LoginActivity.this, errorCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //LOGIN CON GOOGLE

        //LOGIN CON FACEBOOK


    }

    public void irahome(){
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void irResetPwd(){
        Intent i = new Intent(this, RestablecerPwdActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void irInscribirse(){
        Intent i = new Intent(this, InscribirseActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


}