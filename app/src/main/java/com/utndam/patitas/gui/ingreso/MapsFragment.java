package com.utndam.patitas.gui.ingreso;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.transition.MaterialFadeThrough;
import com.utndam.patitas.R;

public class MapsFragment extends Fragment {

    private GoogleMap mapa;
    private FusedLocationProviderClient fusedLocationClient;



    private ActivityResultLauncher<String[]> requestPermissionLauncher;


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
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setTiltGesturesEnabled(false);
            LatLngBounds limites_argentina = new LatLngBounds( new LatLng(-54.964913124446696, -74.26678541029585),new LatLng(-21.897337, -54.118911));

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                mapa.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapa_dark));

            mapa.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    LatLng latLng = mapa.getCameraPosition().target;
                    Log.d(null,latLng.latitude+" "+ latLng.longitude);
                }

            });
            mapa.getUiSettings().setMyLocationButtonEnabled(true);

//            mapa.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//                @Override
//                public void onMapLoaded() {
//
//                }
//            });
            try {
                mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(limites_argentina,0));
            }catch (Exception e){
                Log.d(null,e.toString());
            }
            probarMoverMapaAUbicacionActual();

        }
    };

    public MapsFragment() {
    }

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough());
        setExitTransition(new MaterialFadeThrough());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
        if (isGranted.containsValue(false)) {
            Toast.makeText(getContext(),"No teni permisos",Toast.LENGTH_LONG).show();
        } else {
            moverMapaAUbicacionActual();
        }
        });
    }

    @SuppressLint("MissingPermission")
    public void moverMapaAUbicacionActual(){
        mapa.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener( new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng ubicacionUsuario = new LatLng(location.getLatitude(),location.getLongitude());
                            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionUsuario,15),3,null);
                        }
                    }
                });


    }



    public void probarMoverMapaAUbicacionActual(){

        if (tienePermisoAcceso()) {
            moverMapaAUbicacionActual();
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                    || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                explicarPermisos();
            }
            else{
                pedirPermisos();
            }
        }

    }

    private void explicarPermisos() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder
                .setCancelable(false)
                .setTitle("NECESITO LOS PERMISOS !!!!")
                .setMessage("es por la ciencia")
//                .setPositiveButton("Dale!", (dialog, id) -> Toast.makeText( getContext(), "aca deberia pedir los permisos ", Toast.LENGTH_LONG).show())
                .setPositiveButton("Dale!", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        pedirPermisos();
                    }
                })
                .setNegativeButton("No!", (dialog, id) -> { dialog.dismiss();Toast.makeText( getContext(), " Vos te lo perdes ", Toast.LENGTH_LONG).show();});

        AlertDialog dialogo = builder.create();
        dialogo.show();

    }

    private void pedirPermisos() {
        requestPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION});

    }


    private Boolean tienePermisoAcceso(){
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED));
    }











}