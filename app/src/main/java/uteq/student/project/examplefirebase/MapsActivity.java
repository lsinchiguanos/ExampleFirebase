package uteq.student.project.examplefirebase;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uteq.student.project.examplefirebase.entidades.Place;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Place place;
    private MarkerOptions markerOptions;
    private ArrayList<Marker> firebasemarkerArrayList = new ArrayList<>();
    private ArrayList<Marker> realmarkerArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        database = FirebaseDatabase.getInstance();
    }

    private void countDownTime() {
        new CountDownTimer(5000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                onMapReady(mMap);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myRef = database.getReference("place");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (Marker marker:realmarkerArrayList) {
                    marker.remove();
                }
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    place = dataSnapshot.getValue(Place.class);
                    markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(place.getLatitud(), place.getLongitud()));
                    markerOptions.snippet(place.getName_place());
                    firebasemarkerArrayList.add(mMap.addMarker(markerOptions));
                }
                realmarkerArrayList.clear();
                realmarkerArrayList.addAll(firebasemarkerArrayList);
                countDownTime();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}