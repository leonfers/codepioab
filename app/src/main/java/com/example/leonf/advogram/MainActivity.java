package com.example.leonf.advogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    //Cria a instancia autenticador do firebase para testar se o usuario esta logado.
    private FirebaseAuth autenticador;

    private Toolbar mtoolbar;
    private Button adicionarsaladereuniao;
    private EditText nomenovasala;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> lista_de_salas = new ArrayList<>();

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa a instancia
        autenticador = FirebaseAuth.getInstance();


        mtoolbar = (Toolbar) findViewById(R.id.mainappbar);

        adicionarsaladereuniao = (Button) findViewById(R.id.BotaoCriarSaladeReuniao);
        nomenovasala = (EditText) findViewById(R.id.NomeProcessoInput);
        listView = (ListView) findViewById(R.id.listadeprocessos);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, lista_de_salas);

        listView.setAdapter(arrayAdapter);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Advogram");

        adicionarsaladereuniao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put(nomenovasala.getText().toString(),"");
                root.updateChildren(map);
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();

                Iterator i = dataSnapshot.getChildren().iterator();


                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                lista_de_salas.clear();
                lista_de_salas.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView<?>.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),Chat_Room.class);
                intent.putExtra("room_name",((TextView)View).getText().toString());

                FirebaseUser currentUser = autenticador.getCurrentUser();
                intent.putExtra("user_name",currentUser.getDisplayName());
                startActivity(intent);
            }

        });





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

