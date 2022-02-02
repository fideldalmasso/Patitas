package com.utndam.patitas.gui.ingreso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.utndam.patitas.R;
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



        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {



                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                            try {
                                // Google Sign In was successful, authenticate with Firebase
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                                firebaseAuthWithGoogle(account.getIdToken());
                            } catch (ApiException e) {
                                // Google Sign In failed, update UI appropriately
                                Log.w(TAG, "Google sign in failed", e);
                            }



                        }
                    }
                });


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

        FragmentSignInBinding binding = FragmentSignInBinding.inflate(inflater,container,false);
//        return inflater.inflate(R.layout.fragment_sign_in, container, false);


        binding.emailEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.emailInput.setError(null);
        });

        binding.contraseniaEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            binding.contraseniaInput.setError(null);
        });


        binding.signInGoogle.setOnClickListener(view -> {
            signInWithGoogle();
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

        private void firebaseAuthWithGoogle(String idToken) {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Toast.makeText(getContext(), "Usuario logueado con Google!! xd.",
                                        Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                getActivity().finish();
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
//                                updateUI(null);
                            }
                        }
                    });
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Usuario logueado correctamente!! xd.",
                                    Toast.LENGTH_LONG).show();
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



    private void signInWithGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//            }
//        }
//    }



}