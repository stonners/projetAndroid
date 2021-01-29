package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.os.Handler;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ConnexionDAO;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.MapDAO;

public class NousTrouverFragment extends SupportMapFragment implements OnMapReadyCallback , Response.Listener<JSONArray>, Response.ErrorListener{

    private GoogleMap googleMap;


    public NousTrouverFragment()  {
        getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;
        new Handler().postDelayed(
                ()-> MapDAO.findAll(this),
                1000);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject map = response.getJSONObject(i);
                LatLng shop = new LatLng(map.getDouble("latitude"), map.getDouble("longitude"));
                this.googleMap.addMarker(new MarkerOptions().position(shop).title( map.getString("nom")));
                if(i==0){
                    this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(shop));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(shop, 12));
                }

            }
        }
        catch (Exception e) {
            Log.e("Error", "" + e);
        }
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2));
            }
        });
    }
}





