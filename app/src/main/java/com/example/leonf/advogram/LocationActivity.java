package com.example.leonf.advogram;

import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button busca = (Button) findViewById(R.id.buscaMapa);

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch();
            }
        });






    }

    public void onSearch(){
        EditText location = (EditText)findViewById(R.id.inputMapa);
        String locationstring= location.getText().toString();

        if(location != null || location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            List<android.location.Address> adressList = null;
            try {
                adressList = geocoder.getFromLocationName(locationstring, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            android.location.Address address = adressList.get(0);

            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());

            mMap.addMarker(new MarkerOptions().position(latLng).title("sede da OAB"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));



        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng OAB = new LatLng(-5.0799581, -42.8025177);
        mMap.addMarker(new MarkerOptions().position(OAB).title("sede da OAB"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(OAB));


    }

}
