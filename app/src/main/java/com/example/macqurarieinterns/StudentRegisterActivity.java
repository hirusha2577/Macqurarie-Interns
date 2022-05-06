package com.example.macqurarieinterns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegisterActivity extends AppCompatActivity {
    private EditText name,student_id, nic,email, phone,password1, password2;
    private TextView dob;
    private ImageButton date_dropdown;
    private AutoCompleteTextView center;
    private ImageButton center_dropdown;
    private AutoCompleteTextView degree;
    private ImageButton course_dropdown;
    private TextView r_stud_year;
    private ImageButton year_dropdown;
    private AutoCompleteTextView intake;
    private ImageButton batch_dropdown;
    private ImageButton back_btn, register_btn;
    private RelativeLayout login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        name = findViewById(R.id.name);
        student_id = findViewById(R.id.student_id);
        nic = findViewById(R.id.nic);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        dob = findViewById(R.id.dob);
        center = findViewById(R.id.center);
        degree = findViewById(R.id.degree);
        r_stud_year = findViewById(R.id.r_stud_year);
        intake = findViewById(R.id.intake);

        date_dropdown = findViewById(R.id.date_dropdown);
        back_btn = findViewById(R.id.back_btn);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(StudentRegisterActivity.this,StartActivity.class);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveActivity(StudentRegisterActivity.this,MainActivity.class);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveActivity(StudentRegisterActivity.this,StudentLoginActivity.class);
            }
        });
    }


}