package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class StartActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    private Button student_btn, company_btn, admin_btn;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

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