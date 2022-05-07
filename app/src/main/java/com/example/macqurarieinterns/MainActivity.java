package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.macqurarieinterns.Fragments.HomeFragment;
import com.example.macqurarieinterns.Fragments.JobsFragment;
import com.example.macqurarieinterns.Fragments.MenuFragment;
import com.example.macqurarieinterns.Fragments.ProfileFragment;
import com.example.macqurarieinterns.Model.Company;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottom_navigation_view;

    private static String userType;

    private DatabaseReference databaseReference, databaseReference2;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation_view = findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.setSelectedItemId(R.id.navigation_profile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Company").child(firebaseUser.getUid());
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    userType = "company";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    userType = "student";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static String getUserType() {
        String myString = userType;
        return myString;
    }

    ProfileFragment profileFragment = new ProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    JobsFragment jobsFragment = new JobsFragment();
    MenuFragment menuFragment = new MenuFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;

            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.navigation_job:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, jobsFragment).commit();
                return true;

            case R.id.navigation_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, menuFragment).commit();
                return true;
        }

        return false;
    }


}