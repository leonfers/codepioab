package com.example.leonf.advogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    //Cria a instancia autenticador do firebase para testar se o usuario esta logado.
    private FirebaseAuth autenticador;

    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa a instancia
        autenticador = FirebaseAuth.getInstance();

        mtoolbar = (Toolbar) findViewById(R.id.mainappbar);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Advogram");





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainmenu , menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.mainloggout) {
            FirebaseAuth.getInstance().signOut();
            updatePage();
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Armazena o usuario atual na variavel
        FirebaseUser currentUser = autenticador.getCurrentUser();

        if(currentUser == null){
            updatePage();
        }
    }

    public void updatePage(){
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

}

