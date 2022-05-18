package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.macqurarieinterns.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class StudentEditProfileActivity extends AppCompatActivity {

    private MaterialEditText name, email, phone, nic, current_password, password1, password2, password3;
    private Button edit_btn, password_edit_btn;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        name = findViewById(R.id.name);
        nic = findViewById(R.id.nic);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        current_password = findViewById(R.id.current_password);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        password3 = findViewById(R.id.password3);
        edit_btn = findViewById(R.id.edit_btn);
        password_edit_btn = findViewById(R.id.password_edit_btn);


        databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                name.setText(student.getName());
                nic.setText(student.getNIC());
                email.setText(student.getEmail());
                phone.setText(student.getPhone());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = name.getText().toString();
                String txt_student_id = nic.getText().toString();
                String txt_email = email.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_current_password = current_password.getText().toString();

                if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_student_id)  || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_phone)) {
                    Toast.makeText(StudentEditProfileActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txt_current_password)) {
                    Toast.makeText(StudentEditProfileActivity.this, "Enter Current Password", Toast.LENGTH_SHORT).show();
                } else {
                    EditProfile(txt_name, txt_student_id, txt_email, txt_phone, txt_current_password);
                }
            }
        });

        password_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_password1 = password1.getText().toString();
                String txt_password2 = password2.getText().toString();
                String txt_password3 = password3.getText().toString();
                if (TextUtils.isEmpty(txt_password1) || TextUtils.isEmpty(txt_password2) || TextUtils.isEmpty(txt_password3)) {
                    Toast.makeText(StudentEditProfileActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                } else if (!txt_password1.equals(txt_password2)) {
                    Toast.makeText(StudentEditProfileActivity.this, "new password and confirm password not mach", Toast.LENGTH_SHORT).show();
                } else if (txt_password1.length() < 6) {
                    Toast.makeText(StudentEditProfileActivity.this, "Password must be least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    ChangePassword(txt_password1, txt_password3);
                }
            }
        });


    }


    private void ChangePassword(String password1, String password3) {

        AuthCredential credential = EmailAuthProvider
                .getCredential(student.getEmail(), password3);
        firebaseUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updatePassword(password1)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(StudentEditProfileActivity.this, "Password change successfully", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "User password updated.");
                                            moveActivity(StudentEditProfileActivity.this, StudentLoginActivity.class);
                                        } else {
                                            Toast.makeText(StudentEditProfileActivity.this, "wrong current Password", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "wrong current Password");
                                        }
                                    }
                                });
                    }
                });
    }


    private void EditProfile(String name, String student_id, String email, String phone, String password) {

        if (student.getEmail().equals(email)) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("nic", student_id);
            hashMap.put("email", email);
            hashMap.put("phone", phone);
            databaseReference.updateChildren(hashMap);
            Toast.makeText(StudentEditProfileActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
            moveActivity(StudentEditProfileActivity.this, MainActivity.class);
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("nic", student_id);
            hashMap.put("email", email);
            hashMap.put("phone", phone);
            databaseReference.updateChildren(hashMap);

            AuthCredential credential = EmailAuthProvider
                    .getCredential(student.getEmail(), password);
            firebaseUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "User re-authenticated.");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(StudentEditProfileActivity.this, "Email change successfully", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "User email address updated.");
                                                moveActivity(StudentEditProfileActivity.this, MainActivity.class);
                                            } else {
                                                Toast.makeText(StudentEditProfileActivity.this, "wrong current Password", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "wrong current Password");
                                            }
                                        }
                                    });
                        }
                    });
        }

    }


}