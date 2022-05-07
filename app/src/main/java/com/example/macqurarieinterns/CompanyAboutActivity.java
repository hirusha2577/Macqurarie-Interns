package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.macqurarieinterns.Fragments.ProfileFragment;

import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class CompanyAboutActivity extends AppCompatActivity {

    private EditText about;
    private Button btn_edit_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        about = findViewById(R.id.about);
        btn_edit_about = findViewById(R.id.btn_edit_about);

        btn_edit_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_about = about.getText().toString();

            }
        });

    }


}