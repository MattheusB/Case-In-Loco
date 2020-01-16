package com.example.desafioinloco;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ViewListener {

    private GoogleMap mMap;
    private SearchView searchView;

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickUpdateTextView();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        searchView = (SearchView) findViewById(R.id.location);






       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               String location = searchView.getQuery().toString();
               List<Address> addressList = new LinkedList<>();

               if (location != null || !location.equals("")){
                   Geocoder geocoder = new Geocoder(MapsActivity.this);
                   try {
                       addressList = geocoder.getFromLocationName(location, 1);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                   Address address = addressList.get(0);
                   LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                   mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));


               }

               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
               return false;
           }
       });

       mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the rtMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void clickUpdateTextView(){
        final HttpRequestBuilder builder = new HttpRequestBuilder();
        builder.getJsonFromService(this);
    }


    @Override
    public void updateTextFild(String text){
        resultTextView = findViewById(R.id.text_view_result);

        resultTextView.setText(text);
    }
}
