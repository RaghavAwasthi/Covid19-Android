package com.rvai.covid_19;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.rvai.covid_19.advisory.AdvisoryFragment;
import com.rvai.covid_19.home.HomeFragment;
import com.rvai.covid_19.profile.ProfileFragment;
import com.rvai.covid_19.quarantine.AssesmentActivity;
import com.rvai.covid_19.quarantine.QuarantineFragment;
import com.rvai.covid_19.status.StatusFragment;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private MainActivityViewModel mViewModel;
    int RC_SIGN_IN = 1254;
    SharedPreferences preferences;
    DataStore ds;
    Toolbar bar;
    String TAG = "MainActivity";
    Menu mOptionsMenu;

    private BottomNavigationView navigationView;
    private FirebaseAuth mAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        mOptionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signout:
                signOut();
                break;
            case R.id.menu_refresh:
                ds.refresh();
                Toast.makeText(this, "Refreshing Data", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ds = new DataStore(getApplicationContext());
        bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        navigationView = findViewById(R.id.nav_view);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        mViewModel.getSelectedNavItem().observe(this, this::displayNavigationItem);
        navigationView.setOnNavigationItemSelectedListener(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        selectInitialNavigationItem();
        preferences = getSharedPreferences(Constants.APP_PREFERENCE_NAME, MODE_PRIVATE);
        PermissionManager manager = new PermissionManager(this, this);
        manager.checkAndAskPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AssesmentActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        MainActivityViewModel.NavigationItem NavItem;
        switch (item.getItemId()) {
            case R.id.nav_home:
                NavItem = MainActivityViewModel.NavigationItem.HOME;
                break;
            case R.id.nav_profile:
                NavItem = MainActivityViewModel.NavigationItem.PROFILE;
                break;
            case R.id.nav_status:
                NavItem = MainActivityViewModel.NavigationItem.STATUS;
                break;
            case R.id.nav_advisory:
                NavItem = MainActivityViewModel.NavigationItem.ADVISORIES;
                break;
            case R.id.nav_quarantine:
                NavItem = MainActivityViewModel.NavigationItem.QUARANTINE;
                break;
            default:
                Log.e(TAG, "onNavigationItemSelected: Navigation item not resolved", new IllegalArgumentException());
                throw new IllegalArgumentException();
        }
        mViewModel.setSelectedNavItem(NavItem);
        return true;
    }

    public void selectInitialNavigationItem() {
        int initialItem = R.id.nav_home;
        onNavigationItemSelected(navigationView.getMenu().findItem(initialItem));
        navigationView.setSelectedItemId(initialItem);
    }

    private void displayNavigationItem(MainActivityViewModel.NavigationItem employeeNavigationItem) {
        MainFragment newFragment;
        switch (employeeNavigationItem) {
            case HOME:
                newFragment = new HomeFragment();
                break;
            case STATUS:
                newFragment = new StatusFragment();
                break;
            case PROFILE:
                newFragment = new ProfileFragment();
                break;
            case ADVISORIES:
                newFragment = new AdvisoryFragment();
                break;
            case QUARANTINE:
                newFragment = new QuarantineFragment();
                break;

            default:
                Log.e(TAG, "displayNavigationItem: name not resolved", new IllegalArgumentException());
                throw new IllegalArgumentException();
        }
        if (newFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, newFragment)
                    .commit();
            setTitle(newFragment.getTitle());

        }
    }

    public void signIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            // already signed in
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder().setLogo(R.mipmap.ic_launcher)
                            .setIsSmartLockEnabled(false, true)
                            .setAlwaysShowSignInMethodScreen(false)
                            .setAvailableProviders(Collections.singletonList(
                                    new AuthUI.IdpConfig.PhoneBuilder().build()))
                            .build(),
                    RC_SIGN_IN);
            // not signed in
        }
    }

    public void signOut() {
        mAuth.signOut();
        // preferences = getSharedPreferences(Constants.APP_PREFERENCE_NAME, MODE_PRIVATE);
        // preferences.edit().clear().commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUserMetadata metadata = mAuth.getCurrentUser().getMetadata();
                if (metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {
                    Intent i = new Intent(MainActivity.this, RegisterUserActivity.class);
                    startActivity(i);
                } else {
                    // This is an existing user, show them a welcome back screen.
                }


            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "Sign in was not successfull", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(Toast.LENGTH_LONG);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No Network Found", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(Toast.LENGTH_LONG);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
                Log.e("MainActivity", "Sign-in error: ", response.getError());
            }
        }
    }

    public void selectNavItem(int item) {
        MainActivityViewModel.NavigationItem NavItem;
        switch (item) {
            case R.id.nav_status:
                NavItem = MainActivityViewModel.NavigationItem.STATUS;
                break;
            case R.id.nav_advisory:
                NavItem = MainActivityViewModel.NavigationItem.ADVISORIES;
                break;
            case R.id.nav_quarantine:
                NavItem = MainActivityViewModel.NavigationItem.QUARANTINE;
                break;
        }
    }
}