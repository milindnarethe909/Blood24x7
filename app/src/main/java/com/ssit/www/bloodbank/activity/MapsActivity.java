package com.ssit.www.bloodbank.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ssit.www.bloodbank.Map.AddressData;
import com.ssit.www.bloodbank.Map.DataParser;
import com.ssit.www.bloodbank.Map.DownloadURL;
import com.ssit.www.bloodbank.Map.ListCustomAdapter;
import com.ssit.www.bloodbank.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener {

    //Our Map
    private GoogleMap mMap;

    private static final String TAG = MapsActivity.class.getSimpleName();

    //To store longitude and latitude from map
    public double longitude;
    public double latitude; //Google ApiClient
    private GoogleApiClient googleApiClient;

    ListView listView;
    ListAdapter listAdapter;
    ImageView imageView;


    int PROXIMITY_RADIUS = 1000;
    String url;
    String locationAddressString;
    String intent_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        listView = (ListView) findViewById(R.id.list);
        imageView = (ImageView) findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, DashBoard.class));
                finish();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        //Initializing googleapi client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        Intent intent = getIntent();
        intent_value = intent.getStringExtra("key");
//        headerText.setText(intent_value);


    }

    public String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=" + "AIzaSyCS72LdXEBgOVFO_Vg15F5CIc18i60kB_A");

        Log.d("MapsActivity", "url = " + googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            //moving the map to location
            moveMap();
            Object dataTransfer[] = new Object[2];
            String url = getUrl(latitude, longitude, "hospital");
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            try {
                new GetPlacesTask().execute(dataTransfer);
            } catch (NullPointerException e) {
                Log.d(TAG, ">>>>>>>>>>>>>>   " + e);
            }
        } else {


        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", " + longitude;

        Log.d(TAG, msg);

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);


        //  Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);


        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title(locationAddressString)); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        // Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }


    public class GetPlacesTask extends AsyncTask<Object, String, ArrayList<AddressData>> {

        List<HashMap<String, String>> nearbyPlaceList;

        @Override
        public ArrayList<AddressData> doInBackground(Object... objects) {
            ArrayList<AddressData> placesArrayList = new ArrayList<AddressData>();

            mMap = (GoogleMap) objects[0];
            url = (String) objects[1];
            DownloadURL downloadURL = new DownloadURL();
            try {

                String googlePlacesData = null;
                try {
                    googlePlacesData = downloadURL.readUrl(url);
                } catch (NullPointerException e) {
                    Log.d(TAG, ">>>>>>>>>>>> " + e);
                }
                DataParser parser = new DataParser();
                nearbyPlaceList = parser.parse(googlePlacesData);
                Log.d("nearbyplacesdata", "called parse method");
                placesArrayList = showNearbyPlaces(nearbyPlaceList);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return placesArrayList;
        }

        public ArrayList<AddressData> showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)

        {
            ArrayList<AddressData> arrayList = new ArrayList<>();

            for (int i = 0; i < nearbyPlaceList.size(); i++) {
                AddressData addressData = new AddressData();

                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                //String photolink=googlePlace.get("photo_reference");
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));
                // String opening_hours=googlePlace.get("open_now");
                String photo = googlePlace.get("icon");

                addressData.setPlaceName(placeName);
                addressData.setVicinity(vicinity);
                addressData.setLng(lng);
                addressData.setLat(lat);
                // addressData.setVicinity(vicinity);
                // addressData.setVicinity(photolink);
                addressData.setPhotolink(photo);
                //  addressData.setOp
                arrayList.add(addressData);


            }

            return arrayList;

        }

        @Override
        public void onPostExecute(ArrayList<AddressData> addressData) {
            if (addressData != null) {

                ListCustomAdapter listCustomAdapter = new ListCustomAdapter(MapsActivity.this, addressData, latitude, longitude);
                listView.setAdapter(listCustomAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MapsActivity.this,DashBoard.class));
    }
}