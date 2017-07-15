package com.example.leonf.advogram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.SupportActionModeWrapper;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private EditText email;
    private EditText password;
    private Button loginbutton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mtoolbar = (Toolbar) findViewById(R.id.loginappbar);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        email = (EditText) findViewById(R.id.EmailParaLogin);
        password = (EditText) findViewById(R.id.PasswordParaLogin);
        loginbutton = (Button) findViewById(R.id.Botaoparalogin);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailm = email.getText().toString();
                String pass = password.getText().toString();
                if(!TextUtils.isEmpty(emailm) && !TextUtils.isEmpty(pass)){
                    loginUser(emailm,pass);
                }
            }
        });
    }

    private void loginUser(String emailm, String pass){

        mAuth.signInWithEmailAndPassword(emailm, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Executado", "createUserWithEmail:onComplete:" + task.isSuccessful());


                        if (task.isSuccessful()) {

                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
}
