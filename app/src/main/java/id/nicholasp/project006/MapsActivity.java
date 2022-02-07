package id.nicholasp.project006;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        searchView = findViewById(R.id.idSearchView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Mendapatkan inputan lokasi
                String location = searchView.getQuery().toString();

                // Buat simpan alamat
                List<Address> addressList = null;

                // Cek apakah inputan kosong/belum di isi atau udah
                if (location != null || location.equals("")) {
                    // Menggunakan geocoder
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        // Menggunakan inputan lokasi yang diketik untuk dicari menggunakan geocoder
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Mendapatkan alamat
                    Address address = addressList.get(0);

                    // Mendapatkan latitude and longitude.
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // Memberikan marker pada lokasi yang dicari.
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                    // Animasi kamera ke lokasi.
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}




