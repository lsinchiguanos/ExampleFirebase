package uteq.student.project.examplefirebase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import uteq.student.project.examplefirebase.entidades.Place;

public class MainActivity extends AppCompatActivity {

    private int MY_FINE_LOCATION;
    private FusedLocationProviderClient fusedLocationClient;
    private Button btnMapa;
    private Button btnSave;
    private EditText editTextLatitud;
    private EditText editTextLongitud;
    private EditText editTextNombre;
    private Place place;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        editTextNombre = findViewById(R.id.edithPlace);
        editTextLatitud = findViewById(R.id.edithLatitud);
        editTextLongitud = findViewById(R.id.edithLongitud);
        btnMapa = findViewById(R.id.btnMostrar);
        verMapa();
        btnSave = findViewById(R.id.btnSave);
        save();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLatLonUser();
    }

    private void verMapa() {
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Abrieto", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void save() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFirebase();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getLatLonUser() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_FINE_LOCATION);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        editTextLongitud.setText("" + location.getLongitude());
                        editTextLatitud.setText("" + location.getLatitude());
                    }
                });
    }

    private void saveFirebase(){
        place = new Place();
        place.setName_place(editTextNombre.getText().toString());
        place.setLatitud(Double.parseDouble(editTextLatitud.getText().toString()));
        place.setLongitud(Double.parseDouble(editTextLongitud.getText().toString()));
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("name_place", place.getName_place());
        stringObjectMap.put("latitud", place.getLatitud());
        stringObjectMap.put("longitud", place.getLongitud());
        myRef = database.getReference("place");
        myRef.push().setValue(stringObjectMap);
        cleanObjects();
        Toast.makeText(MainActivity.this, "Almacenado con éxito", Toast.LENGTH_LONG).show();
    }

    private void cleanObjects(){
        editTextNombre.setText("");
        editTextLatitud.setText("");
        editTextLongitud.setText("");
    }

}