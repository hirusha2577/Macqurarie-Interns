package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macqurarieinterns.Function.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class StudentRegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;

    private EditText name,student_id, nic,email, phone,password1, password2;
    private TextView dob;
    private RadioButton male, female;
    private ImageButton date_dropdown,year_dropdown;
    private AutoCompleteTextView center,degree,intake, r_stud_year;
    private ImageButton center_dropdown,course_dropdown,batch_dropdown;
    private ImageButton back_btn, register_btn;
    private RelativeLayout login_btn;

    private DatePickerDialog.OnDateSetListener DateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        auth = FirebaseAuth.getInstance();

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
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        date_dropdown = findViewById(R.id.date_dropdown);
        year_dropdown = findViewById(R.id.year_dropdown);
        center_dropdown = findViewById(R.id.center_dropdown);
        course_dropdown = findViewById(R.id.course_dropdown);
        batch_dropdown = findViewById(R.id.batch_dropdown);
        back_btn = findViewById(R.id.back_btn);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        String[] centers ={"Colombo","Kandy","Galle","Jaffna","Trincomalee","Anuradhapura"};
        ArrayAdapter<String> centerTypes = new ArrayAdapter<String>(StudentRegisterActivity.this , android.R.layout.simple_dropdown_item_1line, centers);
        center.setAdapter(centerTypes);

        center_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                center .showDropDown();
            }
        });



        ///////////////////////////
        date_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        StudentRegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = DateFormat.format("yyyy-MM-dd", calendar).toString();
                dob.setText(date);
            }
        };

        /////////////////////////////

        String[] degrees ={"Bsc.(hons) Information Technology","Bsc.(hons) Business Administration","Bsc.(hons) Naval and Maritime Science","Bsc.(hons) Electronic Engineering","Bsc.(hons) Tourism Management","Bsc.(hons) Applied Science"};
        ArrayAdapter<String> degreeTypes = new ArrayAdapter<String>(StudentRegisterActivity.this , android.R.layout.simple_dropdown_item_1line, degrees);
        degree.setAdapter(degreeTypes);

        course_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                degree .showDropDown();
            }
        });

        String[] years ={"1st year","2nd year","3rd year","4th year"};
        ArrayAdapter<String> yearTypes = new ArrayAdapter<String>(StudentRegisterActivity.this , android.R.layout.simple_dropdown_item_1line, years);
        r_stud_year.setAdapter(yearTypes);

        year_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r_stud_year .showDropDown();
            }
        });

        String[] intakes ={"2019","2020","2021","2022"};
        ArrayAdapter<String> intakeTypes = new ArrayAdapter<String>(StudentRegisterActivity.this , android.R.layout.simple_dropdown_item_1line, intakes);
        intake.setAdapter(intakeTypes);

        batch_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intake .showDropDown();
            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(StudentRegisterActivity.this,StartActivity.class);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = name.getText().toString();
                String txt_student_id = student_id.getText().toString();
                String txt_nic = nic.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password1 = password1.getText().toString();
                String txt_password2 = password2.getText().toString();
                String txt_center = center.getText().toString();
                String txt_degree = degree.getText().toString();
                String txt_r_stud_year = r_stud_year.getText().toString();
                String txt_intake = intake.getText().toString();
                String txt_dob = dob.getText().toString();

                String txt_gender;
                if (male.isChecked()) {
                    txt_gender = male.getText().toString();
                } else {
                    txt_gender = female.getText().toString();
                }

                if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_student_id) || TextUtils.isEmpty(txt_nic) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_phone) || TextUtils.isEmpty(txt_password1) || TextUtils.isEmpty(txt_password2) || TextUtils.isEmpty(txt_center) || TextUtils.isEmpty(txt_degree) || TextUtils.isEmpty(txt_r_stud_year) || TextUtils.isEmpty(txt_intake) || TextUtils.isEmpty(txt_dob) || TextUtils.isEmpty(txt_gender)) {
                    Toast.makeText(StudentRegisterActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidEmailAddress(txt_email)) {
                    Toast.makeText(StudentRegisterActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidMobileNumber(txt_phone)) {
                    Toast.makeText(StudentRegisterActivity.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                } else if (!txt_password1.equals(txt_password2)) {
                    Toast.makeText(StudentRegisterActivity.this, "Password not mach", Toast.LENGTH_SHORT).show();
                } else if (txt_password1.length() < 6) {
                    Toast.makeText(StudentRegisterActivity.this, "Password must be least 6 characters", Toast.LENGTH_SHORT).show();
                }
                register(txt_name, txt_student_id, txt_nic, txt_email, txt_phone, txt_password1, txt_center, txt_degree, txt_r_stud_year, txt_intake, txt_dob, txt_gender);


            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveActivity(StudentRegisterActivity.this,StudentLoginActivity.class);
            }
        });
    }

    private void register(final String name, final String student_id, final String nic, final String email, final String phone, final String password1, final String center, final String degree, final String r_stud_year, final String intake, final String dob,final String gender ) {
        auth.createUserWithEmailAndPassword(email,password1)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            String studentId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Student").child(studentId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",studentId);
                            hashMap.put("student_id",student_id);
                            hashMap.put("name",name);
                            hashMap.put("nic", nic);
                            hashMap.put("email", email);
                            hashMap.put("phone", phone);
                            hashMap.put("center", center);
                            hashMap.put("degree", degree);
                            hashMap.put("r_stud_year", r_stud_year);
                            hashMap.put("intake", intake);
                            hashMap.put("dob", dob);
                            hashMap.put("gender", gender);
                            hashMap.put("status", "0");
                            hashMap.put("P_imageURL", "default");
                            hashMap.put("C_imageURL", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(StudentRegisterActivity.this, "Register success...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(StudentRegisterActivity.this,StudentLoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(StudentRegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


