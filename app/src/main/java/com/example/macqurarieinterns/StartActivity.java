package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class StartActivity extends AppCompatActivity {

    private Button student_btn, company_btn, admin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        student_btn = findViewById(R.id.student_btn);
        company_btn = findViewById(R.id.company_btn);
        admin_btn = findViewById(R.id.admin_btn);

        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(StartActivity.this,StudentLoginActivity.class);
            }
        });

        company_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(StartActivity.this,CompanyLoginActivity.class);
            }
        });

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(StartActivity.this,AdminLoginActivity.class);
            }
        });
    }
}