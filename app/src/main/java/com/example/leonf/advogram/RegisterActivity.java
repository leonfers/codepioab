package com.example.leonf.advogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;

    private FirebaseAuth mAuth;

    //Firebase

    private ProgressDialog mRegProgess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mRegProgess = new ProgressDialog(this);

        mDisplayName = (TextInputLayout) findViewById(R.id.Name_input);
        mEmail = (TextInputLayout) findViewById(R.id.Email_input);
        mPassword = (TextInputLayout) findViewById(R.id.Password_input);
        Button mRegisterButton = (Button) findViewById(R.id.botao_para_registrar);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                String display_name = mDisplayName.getEditText().getText().toString();
                Log.d("Nome",display_name);
                String email = mEmail.getEditText().getText().toString();
                Log.d("email",email);
                String password = mPassword.getEditText().getText().toString();
                Log.d("senha",password);

                if(!TextUtils.isEmpty(display_name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    mRegProgess.setTitle("Registering user");
                    mRegProgess.show();

                    register_user(email, password);
                }else{
                    Toast.makeText(RegisterActivity.this,"Campos vazios",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void register_user( String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            mRegProgess.dismiss();
                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();

                        }else{
                            mRegProgess.hide();
                            Toast.makeText(RegisterActivity.this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
                        }



                    }

                });
    }
}


