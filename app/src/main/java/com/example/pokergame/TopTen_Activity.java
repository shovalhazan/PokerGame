package com.example.pokergame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TopTen_Activity extends AppCompatActivity implements MyCallBack {
    private TopTenListFragment topTenF;
    private MapFragment mapF;
    private FrameLayout mapLay , listLay;
    private static final String TAG = "topTenAc";
    private static final float DEFAULT_ZOOM = 10f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideTheStatusBar();
        findView();

        topTenF=new TopTenListFragment(this);
        ft.add(R.id.placeHolder_LAY_list, topTenF).commit();

        mapF=new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.placeHolder_LAY_map,mapF).commit();
        Log.d(TAG, "onCreate: addMap");
    }

    public void findView(){

        listLay=findViewById(R.id.placeHolder_LAY_list);
        mapLay=findViewById(R.id.placeHolder_LAY_map);
    }
    private void hideTheStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);

    }
    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: Moving the camera to: lat: " + latLng.latitude + " lon: " +
                latLng.longitude);
        mapF.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void addRecordLocationMarker(LatLng latLng, String name) {
        Log.d(TAG, "addRecordLocationMarker: ");
        MarkerOptions markerOptions = new MarkerOptions();
        mapF.getMap().clear();
        markerOptions.position(latLng);
        markerOptions.title(name);
       markerOptions.getPosition();
        mapF.getMap().addMarker(markerOptions);
    }

    @Override
    public void getLocation(LatLng loc , String name) {
        Log.d(TAG, "getLocation: Map is ready latlng="+loc+"  name:="+ name);
        addRecordLocationMarker(loc,name);
        moveCamera(loc, DEFAULT_ZOOM);

    }


}
