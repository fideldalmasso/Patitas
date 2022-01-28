package com.utndam.patitas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapsFragment extends Fragment {

    GoogleMap mapa;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mapa = googleMap;
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//            mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mapa.getUiSettings().setMyLocationButtonEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            LatLngBounds limites_argentina = new LatLngBounds( new LatLng(-54.964913124446696, -74.26678541029585),new LatLng(-21.897337, -54.118911));
            mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(limites_argentina,0));

            mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    latLng = mapa.getCameraPosition().target;
                    Log.d(null,latLng.latitude+" "+ latLng.longitude);
                }
            });

            probarActualizarMapa();


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    @SuppressLint("MissingPermission")
    public void actualizarMapa(){
        mapa.setMyLocationEnabled(true);
    }

    public void probarActualizarMapa(){
        if(noTienePermisoAcceso()){
            Toast.makeText(getContext(),"111111",Toast.LENGTH_LONG).show();
            Log.d(null,"11111");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},9999);
        }else{
            Log.d(null,"22222");
            actualizarMapa();
        }
    }

    private Boolean noTienePermisoAcceso(){
        return (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9999){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                actualizarMapa();
            }else{
                Toast.makeText(getContext(),"No teni permisos",Toast.LENGTH_LONG).show();
            }
        }
    }

}