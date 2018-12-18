package pawel.wiklo.whereismycar;


import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;

    private Button myButton;

    private View.OnClickListener awesomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getLocation();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        myButton = (Button)findViewById(R.id.button);
        myButton.setOnClickListener(awesomeOnClickListener);
    }

    private void getLocation() {
        GPStracker g = new GPStracker(getApplicationContext());
        Location l = g.getLocation();
        if(l!=null)
        {
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            tv1.setText(tv1.getText()+"\n"+lat);
            tv2.setText(tv2.getText()+"\n"+lon);
        }
    }
}