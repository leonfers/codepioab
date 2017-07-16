package com.example.leonf.advogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LeonF on 7/15/2017.
 */

public class Chat_Room extends AppCompatActivity{

    private Button botaodeenvio;
    private EditText inputdamsg;
    private TextView message;

    private String user_name,room_name;
    private DatabaseReference root;
    private String temp_key;
    private Toolbar mtoolbar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_chat , menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.cahtCalendario){
            Intent calendarIntent = new Intent(Chat_Room.this,CalendarActivity.class);
            startActivity(calendarIntent);

        }if(item.getItemId() == R.id.chatBusca) {
            Intent buscaIntent = new Intent(Chat_Room.this, LocationActivity.class);
            startActivity(buscaIntent);
        }
        return true;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        mtoolbar = (Toolbar) findViewById(R.id.chatappbar);




        botaodeenvio = (Button) findViewById(R.id.botaodeenvio);
        inputdamsg = (EditText) findViewById(R.id.inputdemsg);
        message = (TextView) findViewById(R.id.mensagens);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(room_name);


        setTitle("Room - "+room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        botaodeenvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",inputdamsg.getText().toString());

                message_root.updateChildren(map2);
                inputdamsg.setText("");

            }
        });



        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversa(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversa(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String chat_msg,chat_user_name;

    private void append_chat_conversa(DataSnapshot dataSnapshot){

        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){

            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            chat_user_name = (String)((DataSnapshot)i.next()).getValue();

            message.append(chat_user_name +" : "+chat_msg +"\n");

        }
    }
}
