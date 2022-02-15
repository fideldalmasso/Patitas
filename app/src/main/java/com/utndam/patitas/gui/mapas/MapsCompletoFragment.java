package com.utndam.patitas.gui.mapas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.google.android.material.transition.MaterialFadeThrough;
import com.utndam.patitas.R;
import com.utndam.patitas.model.UsuarioActual;

public class MapsCompletoFragment extends Fragment {

    private GoogleMap mapa;
    private FusedLocationProviderClient fusedLocationClient;
//    private boolean permitirMovimiento;


    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private LatLng ubicacionElegida;
    private LatLng ubicacionFija;

    private void cambiarPosicion(LatLng pos){
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,15));
    }

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
            //onStart();
            mapa = googleMap;
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setTiltGesturesEnabled(false);
            LatLngBounds limites_argentina = new LatLngBounds( new LatLng(-54.964913124446696, -74.26678541029585),new LatLng(-21.897337, -54.118911));

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                mapa.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapa_dark));
            }



            mapa.setOnCameraMoveListener(() -> ubicacionElegida = mapa.getCameraPosition().target);

            mapa.getUiSettings().setMyLocationButtonEnabled(true);


            try {
                mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(limites_argentina,0));
            }catch (Exception e){
                Log.d(null,e.toString());
            }

            ubicacionElegida = mapa.getCameraPosition().target;

            // si tengo que mostrar una posicion exacta, no permito movimiento
            if(ubicacionFija!=null){
                ubicacionElegida = ubicacionFija;
                mapa.getUiSettings().setScrollGesturesEnabled(false);
                cambiarPosicion(ubicacionFija);
                probarMoverMapaAUbicacionActual(false);
                return;
            }

            // si el usuario ya eligio una ubicacion al lanzar la aplicacion, muestro esa y no la real
            LatLng ubic = UsuarioActual.getInstance().getUbicacionActual();
            if(ubic!=null){
                ubicacionElegida=ubic;
                cambiarPosicion(ubic);
                probarMoverMapaAUbicacionActual(false);
                return;
            }

            // este es el caso en que el usuario recien lanza la aplicacion o recien inicia sesion / crea una cuenta
            probarMoverMapaAUbicacionActual(true);

        }


    };

    public MapsCompletoFragment() {
//        this.permitirMovimiento = true;
    }

    public MapsCompletoFragment(LatLng posicionInicial) {
        ubicacionFija = posicionInicial;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_maps_completo, container, false);

        Button botonConfirmar = view.findViewById(R.id.confirmar_ubicacion);

        if(ubicacionFija!=null){
            botonConfirmar.setText("Volver");
        }
        botonConfirmar.setOnClickListener(view1 -> {
            Intent intentResultado = new Intent();
            intentResultado.putExtra("lat",ubicacionElegida.latitude);
            intentResultado.putExtra("lng",ubicacionElegida.longitude);
            getActivity().setResult(Activity.RESULT_OK,intentResultado);
            getActivity().finish();
        });
        return view;
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

        } else {
            moverMapaAUbicacionActual(false);
        }
        });
    }

    @SuppressLint("MissingPermission")
    public void moverMapaAUbicacionActual(boolean mover){
        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setMyLocationButtonEnabled(mover);

        if(mover) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            ubicacionElegida = new LatLng(location.getLatitude(), location.getLongitude());
                            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionElegida, 15), 3, null);
                        }
                    });
        }

    }



    public void probarMoverMapaAUbicacionActual(boolean mover){

        if (tienePermisoAcceso()) {
            moverMapaAUbicacionActual(mover);
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
                .setTitle("¿Querés localización automática?")
                .setMessage("El siguiente permiso facilitará encontrarte en el mapa ")
                .setPositiveButton("Dale!", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    pedirPermisos();
                })
                .setNegativeButton("Por ahora no", (dialog, id) -> {
                    dialog.dismiss();
                })
                .create()
                .show();

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