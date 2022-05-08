package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class AdminMainActivity extends AppCompatActivity {

    private RelativeLayout register_req, category, report1, report2, report3, report4,company, student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        register_req = findViewById(R.id.register_req);
        company = findViewById(R.id.company);
        student = findViewById(R.id.student);
        category = findViewById(R.id.category);
        report1 = findViewById(R.id.report1);
        report2 = findViewById(R.id.report2);
        report3 = findViewById(R.id.report3);
        report4 = findViewById(R.id.report4);

        register_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,AdminCompanyRegisterRequestActivity.class);
            }
        });

        report1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,ReportCompanyRegistrationActivity.class);
            }
        });
        report2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,ReportStudentInfluenceActivity.class);
            }
        });
        report3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,ReportVacancyInfluenceActivity.class);
            }
        });
        report4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,ReportStudentRegistrationActivity.class);
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,AdminJobCategoryActivity.class);
            }
        });
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,AdminCompanyActivity.class);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminMainActivity.this,AdminStudentActivity.class);
            }
        });



    }
}