package com.example.macqurarieinterns.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.macqurarieinterns.JobCreateActivity;
import com.example.macqurarieinterns.JobMoreDetailsActivity;
import com.example.macqurarieinterns.R;
import com.example.macqurarieinterns.StudentApplyActivity;
import com.example.macqurarieinterns.StudentInterviewActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private RelativeLayout apply_list, interview_list;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        apply_list = view.findViewById(R.id.apply_list);
        interview_list = view.findViewById(R.id.interview_list);

        apply_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentApplyActivity.class);
                startActivity(intent);
            }
        });

        interview_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentInterviewActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }


}
