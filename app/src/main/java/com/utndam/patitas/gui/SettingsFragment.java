package com.utndam.patitas.gui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.utndam.patitas.R;
import com.utndam.patitas.model.UsuarioActual;
import com.utndam.patitas.model.UsuarioModel;
import com.utndam.patitas.service.CloudFirestoreService;

public class SettingsFragment extends PreferenceFragmentCompat {
    EditTextPreference nombre;
    EditTextPreference telefono;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Fragment frag = this;
        nombre = findPreference("nombre_seleccionado");
        nombre.setText(UsuarioActual.getInstance().getNombreCompleto());
        nombre.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                UsuarioActual.getInstance().setNombreCompleto((String) newValue);
                UserProfileChangeRequest.Builder b = new UserProfileChangeRequest.Builder();
                b.setDisplayName((String) newValue);
                FirebaseAuth.getInstance().getCurrentUser().updateProfile(b.build());
                new CloudFirestoreService().actualizarUsuario((UsuarioModel) UsuarioActual.getInstance(),frag);
                ((MainActivity)getActivity()).getDrawerNombreCompletoUsuario().setText((String) newValue);
                return true;
            }
        });

        telefono = findPreference("telefono_seleccionado");
        telefono.setText(UsuarioActual.getInstance().getTelefono());

        telefono.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                UsuarioActual.getInstance().setTelefono((String) newValue);
                new CloudFirestoreService().actualizarUsuario(UsuarioActual.getInstance(),frag);
                return true;
            }
        });



    }
}