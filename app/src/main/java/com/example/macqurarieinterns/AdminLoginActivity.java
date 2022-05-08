package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.macqurarieinterns.Function.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class AdminLoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    private ImageButton back_btn, login_btn;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        auth = FirebaseAuth.getInstance();

        back_btn = findViewById(R.id.back_btn);
        login_btn = findViewById(R.id.login_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity(AdminLoginActivity.this, StartActivity.class);
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(AdminLoginActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidEmailAddress(txt_email)) {
                    Toast.makeText(AdminLoginActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                } else {
                    adminLogin(txt_email, txt_password);
                }

            }
        });
    }
        private void adminLogin(final String txt_email,final String txt_password) {

            auth.signInWithEmailAndPassword(txt_email,txt_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin").child(auth.getCurrentUser().getUid()).child("status");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.getValue().equals("0")){
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(AdminLoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(AdminLoginActivity.this, "Have not Accepted The Registration", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    });

        }
}
