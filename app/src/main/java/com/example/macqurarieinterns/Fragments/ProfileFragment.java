package com.example.macqurarieinterns.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.macqurarieinterns.CompanyAboutActivity;
import com.example.macqurarieinterns.CompanyEditProfileActivity;
import com.example.macqurarieinterns.MainActivity;
import com.example.macqurarieinterns.PostCreateActivity;
import com.example.macqurarieinterns.R;
import com.example.macqurarieinterns.StudentEditProfileActivity;


public class ProfileFragment extends Fragment  {

    private LinearLayout tab_post, tab_about, post_add2;
    private RelativeLayout about_btn, edit_btn;
    private ImageButton moreBtn;
    private CardView post_add1;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        tab_post = view.findViewById(R.id.tab_about);
        tab_about = view.findViewById(R.id.tab_about);
        about_btn = view.findViewById(R.id.about_btn);
        edit_btn = view.findViewById(R.id.edit_btn);
        moreBtn = view.findViewById(R.id.moreBtn);
        post_add2 = view.findViewById(R.id.post_add2);
        post_add1 = view.findViewById(R.id.post_add1);



        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CompanyAboutActivity.class);
//                intent.putExtra("company_id", u_id);
                startActivity(intent);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentEditProfileActivity.class);
//                intent.putExtra("company_id", u_id);
                startActivity(intent);
            }
        });

        post_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostCreateActivity.class);
//                intent.putExtra("company_id", u_id);
                startActivity(intent);
            }
        });

        post_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostCreateActivity.class);
//                intent.putExtra("company_id", u_id);
                startActivity(intent);
            }
        });


        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(),view);
                popupMenu.inflate(R.menu.post_popup);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.edit_post:
                                Intent intent = new Intent(getContext(), PostCreateActivity.class);
//                intent.putExtra("company_id", u_id);
                                startActivity(intent);
                                return true;

                            case R.id.delete_post:
                                Intent intent1 = new Intent(getContext(), MainActivity.class);
//                intent.putExtra("company_id", u_id);
                                startActivity(intent1);
                                return true;


                        }
                        return false;
                    }
                });
            }
        });



        return view;
    }




}
