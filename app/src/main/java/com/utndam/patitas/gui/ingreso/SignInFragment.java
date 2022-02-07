package com.utndam.patitas.gui.ingreso;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.utndam.patitas.databinding.FragmentSignInBinding;
import com.utndam.patitas.gui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "lol";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ActivityResultLauncher<Intent> someActivityResultLauncher;


//    private FirebaseAuth mAuth;


    public SignInFragment() {
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
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentSignInBinding binding = FragmentSignInBinding.inflate(inflater,container,false);
//        return inflater.inflate(R.layout.fragment_sign_in, container, false);


        binding.emailEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.emailInput.setError(null);
        });

        binding.contraseniaEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.contraseniaInput.setError(null);
        });


        binding.botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean todoOk=true;

                if(!emailValido(binding.emailEdit.getText())){
                    binding.emailInput.setErrorEnabled(true);
                    binding.emailInput.setError("E-mail no válido");
                    todoOk=false;
                }
                if(!contraseniaValida(binding.contraseniaEdit.getText())){
                    binding.contraseniaInput.setErrorEnabled(true);
                    binding.contraseniaInput.setError("Contraseña inválida");
                    todoOk=false;
                }

                if(todoOk) {
                    iniciarSesion(binding.emailEdit.getText().toString(),binding.contraseniaEdit.getText().toString());
                }
            }
        });

        return binding.getRoot();
    }





    public static boolean emailValido(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean contraseniaValida(CharSequence contrasenia) {
        return (!TextUtils.isEmpty(contrasenia) && contrasenia.length()>=6);
    }

    private void iniciarSesion(String email, String password) {
        // [START create_user_with_email]

        FirebaseAuth mAuth =FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                            if(usuario!=null)
                                Toast.makeText(getContext(), "Bienvenido "+usuario.getDisplayName(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            getActivity().finish();
                            startActivity(i);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
//                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }



}