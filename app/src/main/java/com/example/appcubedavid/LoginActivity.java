package com.example.appcubedavid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;
    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN = 100;

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public GoogleSignInOptions googleSignInOptions;
    public GoogleSignInClient googleSignInClient;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    //Activity result launcher para Login con google
    ActivityResultLauncher<Intent> startForResult =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData()!=null ){
                    Log.d(TAG, "onActivityResult: Google Signin intent result");
                    Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try{
                        //sign in con google correcto >>> autenticar con firebase
                        GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                        firebaseAuthWithGoogleAccount(account);
                    }
                    catch (Exception e){
                        Log.d(TAG, "onActivityResult Ex: "+e.getMessage());
                    }
                }
            }
        }
    });

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
        TextView inscribirse = findViewById(R.id.tv_inscribirse);



        //clickar "¿Se te olvido tu contraseña?" >>> ir a RestablecerPwdActivity
        resetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irResetPwd();
            }
        });

        //clickar "Inscribirse." >>> ir a InscribirseActivity
        inscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irInscribirse();
            }
        });



        //con awesomeValidation compruebo que el correo tenga un patrón válido y que las contraseñas tengan al menos 6 carácteres
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.et_nombre_inscribirse, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.et_contraseña1_inscribirse, ".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.et_contraseña2_inscribirse, ".{6,}",R.string.invalid_password);

        //LOGIN CON EMAIL Y CONTRASEÑA
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = nombre.getText().toString();
                String pwd = contraseña.getText().toString();

                if(mail.isEmpty() || pwd.isEmpty()){//comprobar que el nombre y la contraseña no estan vacios
                    Toast.makeText(LoginActivity.this, "Introduce tu correo y contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    if(awesomeValidation.validate()){
                        //si no estan vacios Y cumplen con la validación se pasa a autenticar con firebase
                        firebaseAuth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    irahome();
                                } else {
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    Toast.makeText(LoginActivity.this, errorCode, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(LoginActivity.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //LOGIN CON GOOGLE
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("225955144223-tetp006hqbjsu59l91asn8ospnd57vmh.apps.googleusercontent.com")//(R.string.default_web_client_id)
                /*dentro de .requestIdToken() se debería de poner (R.string.default_web_client_id) el cual, en teoría se debería de generar automáticamente
                * en "strings.xml". En mi caso no lo hace (no he averiguado el motivo) y la solución provisional que he encontrado es copiar el "client_id"
                * de "google-services.json" directamente dentro de .requestIdToken()*/
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: begin Google SignIn");
                Intent intent = googleSignInClient.getSignInIntent();

                startForResult.launch(intent);

                }


        });



    }


    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: comenzando autenticacion de firebase con cuenta de google");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login correcto
                        Log.d(TAG, "onSuccess: Logged in");

                        //obtenet usuario logeado
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();

                        Log.d(TAG, "onSuccess: "+email);
                        Log.d(TAG,"onSucces: "+uid);

                        //comprobar si el usuario es nuevo o existente
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            //si el usuario es nuevo - Cuenta creada
                            Log.d(TAG, "onSucces: Cuenta creada...\n"+email);
                            Toast.makeText(LoginActivity.this, "Cuenta creada...\n"+email, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //usuario existente - Sesión iniciada
                            Log.d(TAG, "onSucces: Usuario existente...\n"+email);
                            Toast.makeText(LoginActivity.this, "Usuario existente...\n"+email, Toast.LENGTH_SHORT).show();
                        }
                        //ir a la activity home
                        irahome();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //login fallido
                        Log.d(TAG, "onFailure: Loggin failed"+e.getMessage());
                    }
                });
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