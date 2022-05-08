package com.example.macqurarieinterns.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.Adapter.PostAdapter;
import com.example.macqurarieinterns.Adapter.VacancyAdapter;
import com.example.macqurarieinterns.JobAppliersActivity;
import com.example.macqurarieinterns.JobCreateActivity;
import com.example.macqurarieinterns.JobInterviewActivity;
import com.example.macqurarieinterns.JobMoreDetailsActivity;
import com.example.macqurarieinterns.MainActivity;
import com.example.macqurarieinterns.Model.Company;
import com.example.macqurarieinterns.Model.Post;
import com.example.macqurarieinterns.Model.Vacancy;
import com.example.macqurarieinterns.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobsFragment extends Fragment {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,databaseReference1;


    private FloatingActionButton create_job_btn;
    private String userType;
    String uid;

    List<Vacancy> vacancyList;
    List<Company> companyList;
    RecyclerView recyclerview_vacancy;
    VacancyAdapter vacancyAdapter;

    private String companyName, companyId, companyAddress, companyImage;


    public JobsFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        MainActivity activity = (MainActivity) getActivity();
        userType = activity.getUserType();

        create_job_btn = view.findViewById(R.id.create_job_btn);

        if(userType.equals("company")){
            create_job_btn.setVisibility(View.VISIBLE);
        }else{
            create_job_btn.setVisibility(View.GONE);
        }


        create_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobCreateActivity.class);
                startActivity(intent);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        companyId = firebaseUser.getUid();

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Company").child(companyId);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                companyName = (String)dataSnapshot.child("name").getValue();
                companyAddress = (String)dataSnapshot.child("address").getValue();
                companyImage = (String)dataSnapshot.child("P_imageURL").getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerview_vacancy = view.findViewById(R.id.job_vacancy_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerview_vacancy.setLayoutManager(layoutManager);

        vacancyList = new ArrayList<>();
        companyList = new ArrayList<>();


        checkUserStatus();
        loadCompanyVacancy();


        return view;
    }


    private void loadCompanyVacancy() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy");
        Query query = databaseReference.orderByChild("id").equalTo(uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vacancyList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Vacancy vacancy = snapshot.getValue(Vacancy.class);
                    vacancyList.add(vacancy);
                    vacancyAdapter = new VacancyAdapter(getActivity(), vacancyList, companyName, companyAddress, companyImage);
                    recyclerview_vacancy.setAdapter(vacancyAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        } else {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

}
