package com.example.leonf.advogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button regbutton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginButton =(Button)findViewById(R.id.botao_possuiconta);

       /* loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(StartActivity.this ,LoginActivity.class);

                startActivity(login_intent);
            }
        });
        */
        regbutton =(Button)findViewById(R.id.botao_registro);
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(StartActivity.this ,RegisterActivity.class);

                startActivity(reg_intent);
            }
        });
    }

}
