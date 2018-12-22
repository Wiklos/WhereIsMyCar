package pawel.wiklo.whereismycar;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailedInfo extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String name;
    private String date;
    private Double lat;
    private Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
         name = intent.getStringExtra("name");
         date = intent.getStringExtra("date");
         lat = intent.getDoubleExtra("lat",0.0);
         lon = intent.getDoubleExtra("lon",0.0);


        TextView tv = (TextView)findViewById(R.id.name);
        tv.setText(name);

        TextView tv2 = (TextView)findViewById(R.id.date);
        tv2.setText(date+"\n"+lat+"\n"+lon);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(51.125693, 20.8730044);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Current position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(lat, lon));
        mp.title(name);
        mp.snippet(date);
        mMap.addMarker(mp);


        float[] results = new float[1];
        Location.distanceBetween(mp.getPosition().latitude, mp.getPosition().longitude,
                51.125693, 20.8730044, results);

        TextView tv = (TextView)findViewById(R.id.name);
        tv.setText(""+results[0]);


    }
}
