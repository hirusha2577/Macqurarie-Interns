package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.macqurarieinterns.Function.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class CompanyRegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;

    private EditText name, register_no, address, phone, email, password1, password2;
    private AutoCompleteTextView type;
    private ImageButton select_type;
    private  ImageButton back_btn, register_btn;
    private RelativeLayout login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        register_no = findViewById(R.id.register_no);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        type = findViewById(R.id.type);

        select_type = findViewById(R.id.select_type);
        back_btn = findViewById(R.id.back_btn);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        String[] types ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
        ArrayAdapter<String> companyTypes = new ArrayAdapter<String>(CompanyRegisterActivity.this , android.R.layout.simple_dropdown_item_1line, types);
        type.setAdapter(companyTypes);


        select_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type.showDropDown();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(CompanyRegisterActivity.this,StartActivity.class);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_name = name.getText().toString();
                String txt_register_no = register_no.getText().toString();
                String txt_address = address.getText().toString();
                String txt_email = email.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_password1 = password1.getText().toString();
                String txt_password2 = password2.getText().toString();
                String txt_type = type.getText().toString();

                if(TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_register_no) || TextUtils.isEmpty(txt_address) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_phone) || TextUtils.isEmpty(txt_password1) || TextUtils.isEmpty(txt_password2) || TextUtils.isEmpty(txt_type) ) {
                    Toast.makeText(CompanyRegisterActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                } else if(!Validation.isValidEmailAddress(txt_email)) {
                    Toast.makeText(CompanyRegisterActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                } else if(!txt_password1.equals(txt_password2)){
                    Toast.makeText(CompanyRegisterActivity.this, "Password not mach", Toast.LENGTH_SHORT).show();
                } else if (txt_password1.length() < 6){
                    Toast.makeText(CompanyRegisterActivity.this, "Password must be least 6 characters", Toast.LENGTH_SHORT).show();
                }else {
                    companyRegister(txt_name, txt_register_no, txt_address, txt_email, txt_phone, txt_password1, txt_type);
                }


            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(CompanyRegisterActivity.this,CompanyLoginActivity.class);
            }
        });
    }

    private void companyRegister(final String name, final String register_no, final String address, final String email, final String phone, final String password1, final String type) {
        auth.createUserWithEmailAndPassword(email,password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            String companyId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Company").child(companyId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", companyId);
                            hashMap.put("register_no",register_no);
                            hashMap.put("name",name);
                            hashMap.put("address", address);
                            hashMap.put("email", email);
                            hashMap.put("phone", phone);
                            hashMap.put("type", type);
                            hashMap.put("about", "Enter Company About");
                            hashMap.put("status", "0");
                            hashMap.put("P_imageURL", "default");
                            hashMap.put("C_imageURL", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CompanyRegisterActivity.this, "Register success...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CompanyRegisterActivity.this,CompanyLoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(CompanyRegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}