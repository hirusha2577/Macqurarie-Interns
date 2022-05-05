package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class CompanyRegisterActivity extends AppCompatActivity {

    private EditText name, register_no, address, phone, email, password1, password2;
    private AutoCompleteTextView type;
    private ImageButton select_type;
    private  ImageButton back_btn, register_btn;
    private RelativeLayout login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        name = findViewById(R.id.name);
        register_no = findViewById(R.id.register_no);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        type = findViewById(R.id.type);

        select_type = findViewById(R.id.select_type);
        back_btn = (ImageButton) findViewById(R.id.back_btn);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(CompanyRegisterActivity.this,StartActivity.class);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(CompanyRegisterActivity.this,MainActivity.class);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(CompanyRegisterActivity.this,CompanyLoginActivity.class);
            }
        });
    }
}