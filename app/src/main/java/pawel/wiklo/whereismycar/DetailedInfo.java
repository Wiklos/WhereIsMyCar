package pawel.wiklo.whereismycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailedInfo extends FragmentActivity implements OnMapReadyCallback {

    TextView tv;
    TextView distance;

    AlertDialog alertDialog;
    EditText editText;

    private Marker mPreviousMarker ;

    boolean prepareMap = true;

    private GoogleMap mMap;

    private String name;
    private String date;
    private Double lat;
    private Double lon;
    private Double currLat;
    private Double currLon;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
         //name = intent.getStringExtra("name");
         //date = intent.getStringExtra("date");
         //lat = intent.getDoubleExtra("lat",0.0);
         //lon = intent.getDoubleExtra("lon",0.0);

         id = intent.getLongExtra("Id",0);

        DataBaseValues value = DataBaseValues.findById(DataBaseValues.class, id);

        distance = (TextView)findViewById(R.id.distance);


        tv = (TextView)findViewById(R.id.name);
        tv.setText(value.getNazwa());

        TextView tv2 = (TextView)findViewById(R.id.date);
        tv2.setText(value.getData()+"\n"+value.getLat()+"\n"+value.getLat());

        name = value.getNazwa();
        date = value.getData();
        lat = value.getLat();
        lon = value.getLon();



        alertDialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        alertDialog.setTitle("Set name");
        alertDialog.setView(editText);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save name", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv.setText(editText.getText());

                DataBaseValues value = DataBaseValues.findById(DataBaseValues.class, id);
                value.nazwa = editText.getText().toString();
                value.save();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(tv.getText());
                alertDialog.show();
            }
        });




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
        //LatLng sydney = new LatLng(51.125693, 20.8730044);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Current position"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(lat, lon));
        mp.title(name);
        mp.snippet(date);
        mMap.addMarker(mp);

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

        if(prepareMap)
        {
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            prepareMap = false;
        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                MarkerOptions mp = new MarkerOptions();

                mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

                mp.title("Current Poition");

                if (mPreviousMarker != null) {
                    mPreviousMarker.remove();

                }

                mPreviousMarker = mMap.addMarker(mp);


                Log.v("Map", mp.getPosition().toString());

                currLat = mp.getPosition().latitude;
                currLon = mp.getPosition().longitude;


                float[] results = new float[1];
                Location.distanceBetween(lat, lon,
                        currLat, currLon, results);

                distance.setText(""+results[0]);
                Log.v("MapReult", ""+results[0]);

            }
        });


    }
}
