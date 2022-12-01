package rmit.ad.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import rmit.ad.myapplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final long UPDATE_INTERVAL = 10*1000 ;
    private static final long FASTEST_INTERVAL = 5000 ;
    protected FusedLocationProviderClient client;
    public LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng rmit = new LatLng(10.729819990183303, 106.6931399730168);        mMap.addMarker(new MarkerOptions().position(rmit).title("RMIT Vietnam"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rmit));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rmit, 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapsActivity.this, marker.toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        startLocationUpdate();
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }
    @SuppressLint("MissingPermission")
    public void getPosition(View view){
        client.getLastLocation().addOnSuccessListener(location -> Toast.makeText(MapsActivity.this, location.getLatitude() + "", Toast.LENGTH_SHORT).show());
    }
    public void onLocationChanged(Location location){
        String message = "Updated location " +
                Double.toString(location.getLatitude()) + ", " +
                Double.toString(location.getLongitude());
        LatLng newLoc = new LatLng(location.getLatitude(),
                location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(newLoc).title("New Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint({"MissingPermission", "RestrictedApi"})
    private void startLocationUpdate(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        client.requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult){
                onLocationChanged(locationResult.getLastLocation());
            }
        }, null);
    }
}