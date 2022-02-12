package com.utndam.patitas.gui.ingreso;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.utndam.patitas.databinding.FragmentSignUpBinding;
import com.utndam.patitas.gui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "fidel";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private FirebaseAuth mAuth;


    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setEnterTransition(new MaterialFadeThrough());
//        setExitTransition(new MaterialFadeThrough());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        mAuth = FirebaseAuth.getInstance();

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Toast.makeText(getContext(), "usuario logueado:"+currentUser.getEmail(), Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentSignUpBinding binding = FragmentSignUpBinding.inflate(inflater,container,false);
//        return inflater.inflate(R.layout.fragment_sign_in, container, false);


        binding.nombreCompletoEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.nombreCompletoInput.setError(null);
        });

        binding.emailEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.emailInput.setError(null);
        });

        binding.contraseniaEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.contraseniaInput.setError(null);
        });

        binding.repetirContraseniaEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.repetirContraseniaInput.setError(null);
        });

        binding.celularEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.celularInput.setError(null);
        });

        binding.celularEdit.addTextChangedListener(new PhoneNumberFormattingTextWatcher("AR"){});



        binding.botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean todoOk=true;


                if(!nombreValido(binding.nombreCompletoEdit.getText())){
                    binding.nombreCompletoInput.setErrorEnabled(true);
                    binding.nombreCompletoInput.setError("Formato: Nombre/s Apellido/s");
                    todoOk=false;
                }
                if(!emailValido(binding.emailEdit.getText())){
                    binding.emailInput.setErrorEnabled(true);
                    binding.emailInput.setError("E-mail no válido");
                    todoOk=false;
                }
                if(!contraseniaValida(binding.contraseniaEdit.getText())){
                    binding.contraseniaInput.setErrorEnabled(true);
                    binding.contraseniaInput.setError("6 caracteres como mínimo");
                    todoOk=false;
                } else {
                    if(!repetirContraseniaValida(binding.contraseniaEdit.getText(),binding.repetirContraseniaEdit.getText())){
                        binding.repetirContraseniaInput.setErrorEnabled(true);
                        binding.repetirContraseniaInput.setError("Las contraseñas no son iguales");
                        todoOk=false;
                    }
                }
                if(!telefonoValido(binding.celularEdit.getText())){
                    binding.celularInput.setErrorEnabled(true);
                    binding.celularInput.setError("Número no válido");
                    todoOk=false;
                }

                if(todoOk) {
                    createAccount(binding.emailEdit.getText().toString(),
                            binding.contraseniaEdit.getText().toString(),
                            binding.nombreCompletoEdit.getText().toString());

                }
            }
        });


        return binding.getRoot();
    }

    private void asignarNombreACuenta(String nombreCompleto) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nombreCompleto)
                .build();

        if(user!=null){
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                            }
                        }
                    });
        }
        cambiarAMainActivity();
    }

    private boolean nombreValido(CharSequence nombre) {
        return (!TextUtils.isEmpty(nombre) && nombre.length()<=32 && nombre.toString().matches("[a-zA-Z ]+ [a-zA-Z ]+"));
    }

    public static boolean emailValido(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean contraseniaValida(CharSequence contrasenia) {
        return (!TextUtils.isEmpty(contrasenia) && contrasenia.length()>=6);
    }

    private boolean repetirContraseniaValida(CharSequence contrasenia1, CharSequence contrasenia2) {
        return (!TextUtils.isEmpty(contrasenia2) && contrasenia1.toString().equals(contrasenia2.toString()));
    }

    private boolean telefonoValido(CharSequence telefono){
        return TextUtils.isEmpty(telefono) || PhoneNumberUtils.isGlobalPhoneNumber(obtenerTelefono(telefono));
    }

    private String obtenerTelefono(CharSequence telefono){
        return PhoneNumberUtils.stripSeparators("+54"+telefono);
    }

    private void createAccount(String email, String password, String nombreCompleto) {
        // [START create_user_with_email]

        FirebaseAuth mAuth =FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Usuario creado correctamente!! xd.",
                                    Toast.LENGTH_LONG).show();
                            asignarNombreACuenta(nombreCompleto);

//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Log.w(TAG, "Ya existe un usuario con ese email", task.getException());
                                Toast.makeText(getContext(), "Ya existe un usuario con ese email",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }


                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
//                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void cambiarAMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        startActivity(i);
    }


}