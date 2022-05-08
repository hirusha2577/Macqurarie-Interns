package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class AdminCompanyActivity extends AppCompatActivity {
    private ImageButton deactivateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_company);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Company");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             moveActivity(AdminCompanyActivity.this,AdminMainActivity.class);
            }
        });
        deactivateBtn = findViewById(R.id.deactivateBtn);

        deactivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCompanyActivity.this, AdminCompanyActivity.class);
                startActivity(intent);
            }
        });
    }
}