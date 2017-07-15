package com.example.leonf.advogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    //Cria a instancia autenticador do firebase para testar se o usuario esta logado.
    private FirebaseAuth autenticador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa a instancia
        autenticador = FirebaseAuth.getInstance();




    }
    @Override
    public void onStart() {
        super.onStart();

        //Armazena o usuario atual na variavel
        FirebaseUser currentUser = autenticador.getCurrentUser();

        if(currentUser == null){
            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();
        }
    }

}
