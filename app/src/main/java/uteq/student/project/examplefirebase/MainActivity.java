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

public class MainActivity extends AppCompatActivity {

    private int MY_FINE_LOCATION;
    private FusedLocationProviderClient fusedLocationClient;
    private Button btnMapa;
    private EditText editTextLatitud;
    private EditText editTextLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLatitud = findViewById(R.id.edithLatitud);
        editTextLongitud = findViewById(R.id.edithLongitud);
        btnMapa = findViewById(R.id.btnMostrar);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Abrieto", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLatLonUser();
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
                        editTextLongitud.setText( "" + location.getLongitude());
                        editTextLatitud.setText("" + location.getLatitude());
                    }
                });
    }

}