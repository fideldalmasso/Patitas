package com.utndam.patitas.gui.ingreso;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.MainActivity;

public class IngresoActivity extends FragmentActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public static ViewPager2 viewPager;
//    public static FirebaseAuth mAuth;
    public static GoogleSignInClient mGoogleSignInClient;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);



//        mAuth = FirebaseAuth.getInstance();
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.paginador);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);


        viewPager.setUserInputEnabled(true);

        viewPager.setUserInputEnabled(false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Log.d(null,"xd");
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if(usuario != null){
            Toast.makeText(this, "Bienvenido "+usuario.getDisplayName(), Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MainActivity.class);
            finish();
            startActivity(i);
        }
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {

            switch(position){
                case 0:
                    return new BienvenidaFragment();
                case 1:
                    return new SignInFragment();
                case 2:
                    return new SignUpFragment();
                default:
                    return new BienvenidaFragment();
            }

        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
