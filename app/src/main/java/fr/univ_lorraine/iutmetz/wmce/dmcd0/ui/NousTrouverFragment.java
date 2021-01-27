package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NousTrouverFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    public NousTrouverFragment()  {
        getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;

        LatLng shop = new LatLng(49.1296616, 6.154556599999999);
        this.googleMap.addMarker(new MarkerOptions().position(shop).title("boutique MonPullMoch"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(shop));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(shop, 12));

        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2));
            }
        });
    }
}
