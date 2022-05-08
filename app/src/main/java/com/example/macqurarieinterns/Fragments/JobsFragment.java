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

import com.example.macqurarieinterns.JobAppliersActivity;
import com.example.macqurarieinterns.JobCreateActivity;
import com.example.macqurarieinterns.JobInterviewActivity;
import com.example.macqurarieinterns.JobMoreDetailsActivity;
import com.example.macqurarieinterns.MainActivity;
import com.example.macqurarieinterns.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JobsFragment extends Fragment {

    private ImageButton more_btn;
    private FloatingActionButton create_job_btn;
    private CardView job_card;
    private String userType;


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

        more_btn = view.findViewById(R.id.more_btn);
        create_job_btn = view.findViewById(R.id.create_job_btn);
        job_card = view.findViewById(R.id.job_card);

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

//        job_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), JobMoreDetailsActivity.class);
//                startActivity(intent);
//            }
//        });


//        more_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(getContext(),view);
//                popupMenu.inflate(R.menu.job_popup);
//                popupMenu.show();
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        switch (menuItem.getItemId()) {
//                            case R.id.appliers_job:
//                                Intent intent = new Intent(getContext(), JobAppliersActivity.class);
//                                startActivity(intent);
//                                return true;
//
//                            case R.id.interview_job:
//                                Intent intent1 = new Intent(getContext(), JobInterviewActivity.class);
//                                startActivity(intent1);
//                                return true;
//
//                            case R.id.edit_job:
//                                Intent intent2 = new Intent(getContext(), JobCreateActivity.class);
//                                startActivity(intent2);
//                                return true;
//
//                            case R.id.delete_job:
//                                Toast.makeText(getContext(),"deleted",Toast.LENGTH_LONG).show();
//                                return true;
//                        }
//                        return false;
//                    }
//                });
//            }
//        });



        return view;
    }



}
