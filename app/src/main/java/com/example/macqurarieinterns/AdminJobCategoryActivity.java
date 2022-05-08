package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class AdminJobCategoryActivity extends AppCompatActivity {
    private ImageButton confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(AdminJobCategoryActivity.this,AdminMainActivity.class);
            }

        });

        confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminJobCategoryActivity.this, JobInterviewCreateActivity.class);
                startActivity(intent);
            }
        });
    }
}