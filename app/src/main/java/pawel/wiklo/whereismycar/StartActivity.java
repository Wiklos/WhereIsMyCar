package pawel.wiklo.whereismycar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orm.SugarContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StartActivity extends FragmentActivity implements OnMapReadyCallback {

    boolean prepareMap = true;

    private MarkerOptions markers = new MarkerOptions();
    private Marker mPreviousMarker ;

    double lat;
    double lon;

    private GoogleMap mMap;

    private TextView tv1;
    private TextView tv2;

    private Button myButton;

    private Button goToListActivityButton;

    private View.OnClickListener addNewPosition = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getLocation();
        }
    };

    private View.OnClickListener gotoList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToListActivity();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);

        ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        myButton = (Button)findViewById(R.id.button);
        myButton.setOnClickListener(addNewPosition);

        goToListActivityButton = (Button)findViewById(R.id.goToListActivityButton);
        goToListActivityButton.setOnClickListener(gotoList);
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

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
                //mMap.clear();

                MarkerOptions mp = new MarkerOptions();

                mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

                mp.title("Current Poition");

                if (mPreviousMarker != null) {
                    mPreviousMarker.remove();
                }

                mPreviousMarker = mMap.addMarker(mp);

                if(prepareMap)
                {
                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);
                    prepareMap = false;
                }


                Log.v("Map", mp.getPosition().toString());
                //tv1.setText(tv1.getText()+"\n"+mp.getPosition().latitude);
                //tv2.setText(tv2.getText()+"\n"+mp.getPosition().longitude);

                lat = mp.getPosition().latitude;
                lon = mp.getPosition().longitude;

            }
        });

//        MarkerOptions mp2 = new MarkerOptions();
//        mp2.position(new LatLng(51.1256, 20.863033));
//        mp2.title("New Marker2");
//        mMap.addMarker(mp2);


//        MarkerOptions mp3 = new MarkerOptions();
//        mp3.position(new LatLng(51.1256500, 20.8759));
//        mp3.title("New Marker3");
//        mMap.addMarker(mp3);


        SugarContext.init(this);
        List<DataBaseValues> myList = DataBaseValues.listAll(DataBaseValues.class);

        for (DataBaseValues value : myList)
        {
            //TODO if nazwa == empty or sth then title is date
            markers.position(new LatLng(value.getLat(), value.getLon()));
            markers.title(value.getNazwa());
            markers.snippet(value.getData());
            googleMap.addMarker(markers).setTag(value.getId());
        }


        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub

//                Toast toast = Toast.makeText(getApplicationContext(), arg0.getPosition().toString(), Toast.LENGTH_LONG);
//                toast.show();

                Log.v("MapID", " "+arg0.getTag());

                Intent intent = new Intent(getApplicationContext(), DetailedInfo.class);
                intent.putExtra("Id", (Long) arg0.getTag());
                //intent.putExtra("name", arg0.getTitle());
                //intent.putExtra("date", arg0.getSnippet());
                //intent.putExtra("lat", arg0.getPosition().latitude);
                //intent.putExtra("lon", arg0.getPosition().longitude);
                startActivity(intent);

            }
        });


    }

    private void getLocation() {
//        GPStracker g = new GPStracker(getApplicationContext());
//        Location l = g.getLocation();
//        if(l!=null)
//        {
//            lat = l.getLatitude();
//            lon = l.getLongitude();
//            tv1.setText(tv1.getText()+"\n"+lat);
//            tv2.setText(tv2.getText()+"\n"+lon);
//
//
//            LatLng newPosition = new LatLng(lat, lon);
//            mMap.addMarker(new MarkerOptions().position(newPosition).title("Your Position"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(newPosition));
//        }
        //SugarContext.init(this);
        //DataBaseValues newEntry = new DataBaseValues(lat,lon,"data","name");
        //newEntry.save();
        //Intent intent = new Intent(this, ListOfAll.class);
        //startActivity(intent);

//        SugarContext.init(this);
//        DataBaseValues dbv = new DataBaseValues(0.01,0.02,"aaa","bbb");
//        dbv.save();

//        SugarContext.init(this);
//        for(int i = 1;i<10;i++)
//        {
//            DataBaseValues book = DataBaseValues.findById(DataBaseValues.class, i);
//            book.delete();
//        }



        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43

        Toast toast = Toast.makeText(this, dateFormat.format(date), Toast.LENGTH_LONG);
        toast.show();

        SugarContext.init(this);
        //DataBaseValues dbv = new DataBaseValues(51.1256500,20.8759,dateFormat.format(date),"Nazwa");
        DataBaseValues dbv = new DataBaseValues(lat,lon,dateFormat.format(date),"Nazwa");
        dbv.save();


        //Toast toast = Toast.makeText(this, dbv.getLat()+" - "+dbv.getLon()+" - "+dbv.getNazwa()+" - "+dbv.getData()+" - "+dbv.getId(), Toast.LENGTH_LONG);
        //toast.show();


    }
private void goToListActivity()
{
    Intent intent = new Intent(this, ListOfAll.class);
    startActivity(intent);
}

}


//TODO
/*
delete from db
widget
option to hide hour on marker
marker colors
show only date if name not set

Widget!
*/
