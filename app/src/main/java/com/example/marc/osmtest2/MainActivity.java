package com.example.marc.osmtest2;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.ResourceProxy;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.nio.charset.Charset;


public class MainActivity extends Activity implements LocationListener {

    MapView map;
    TextView longitude ;
    TextView latitude;
    MyLocationNewOverlay myLocation;
    LocationManager locMgr;
    MapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button localisation = (Button)findViewById(R.id.button);
        longitude = (TextView) findViewById(R.id.textView3);
        latitude = (TextView) findViewById(R.id.textView2);

        locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

        initializeMap();

        View.OnClickListener buttonListener = new View.OnClickListener() {

            public void onClick(View v) {
             //   longitude.setText(String.valueOf(myLocation.getMyLocation().getLongitude()) );
             //   latitude.setText(String.valueOf(myLocation.getMyLocation().getLatitude()) );
                initializeLocalization();
            }
        };
        localisation.setOnClickListener(buttonListener);

    }


    private void initializeLocalization() {

        Criteria criteria = new Criteria();
        String provider = locMgr.getBestProvider(criteria, true);
        Location location = locMgr.getLastKnownLocation(provider);
        Boolean pb = locMgr.isProviderEnabled(provider);

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
            locMgr.removeUpdates(this);
        } else {
            locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

//            latitude.setText("Location not available");
//            longitude.setText(pb.toString());
        }
    }

    public void initializeMap(){
        map = (MapView) findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        GeoPoint startPoint = new GeoPoint(541541, 9735936);
        mapController = (MapController) map.getController();
        mapController.setCenter(startPoint);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        mapController.setZoom(10);
        map.setUseDataConnection(true);

    }

    protected void onResume() {
        super.onResume();
    }


    public void onLocationChanged(Location location) {
 //       int lat = (int) (location.getLatitude());
 //       int lng = (int) (location.getLongitude());
 //       latitude.setText(String.valueOf(lat));
 //       longitude.setText(String.valueOf(lng));

//        this.location = location;
        String lat = String.valueOf(location.getLatitude());
        String lng = String.valueOf(location.getLongitude());
        System.out.println("provider");
        System.out.println("lat");
        System.out.println("lng");
        latitude.setText(lat);
        longitude.setText(lng+" ");
        locMgr.removeUpdates(this);

        GeoPoint startPoint2 = new GeoPoint((int)(location.getLatitude() * 1E6),(int)(location.getLongitude() * 1E6) );//Integer.parseInt(lng.toString()) * 1E6
        mapController.setCenter(startPoint2);
        mapController.setZoom(20);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        locMgr.removeUpdates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
