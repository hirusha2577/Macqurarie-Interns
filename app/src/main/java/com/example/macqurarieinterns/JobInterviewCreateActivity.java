package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class JobInterviewCreateActivity extends AppCompatActivity {

    private TextView closing_date,closing_time;
    private EditText Place,dis;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    int hour, minute;

    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    String id, company_id, vacancy_id, student_id;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_interview_create);

        pd = new ProgressDialog(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        company_id = intent.getStringExtra("company_id");
        vacancy_id = intent.getStringExtra("vacancy_id");
        student_id = intent.getStringExtra("student_id");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Interview");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        closing_date = findViewById(R.id.closing_date);
        closing_time = findViewById(R.id.closing_time);
        Place = findViewById(R.id.Place);
        dis = findViewById(R.id.dis);



        closing_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        JobInterviewCreateActivity.this,
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
                closing_date.setText(date);
            }
        };
        closing_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                        closing_time.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(JobInterviewCreateActivity.this,timeSetListener,hour, minute,true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_btn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_post:

                String txt_closing_date = closing_date.getText().toString();
                String txt_closing_time = closing_time.getText().toString();
                String txt_Place = Place.getText().toString();
                String txt_dis = dis.getText().toString();

                if(TextUtils.isEmpty(txt_closing_date) || TextUtils.isEmpty(txt_closing_time) || TextUtils.isEmpty(txt_Place) || TextUtils.isEmpty(txt_dis)){
                    Toast.makeText(JobInterviewCreateActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                }else{


                    createInterview(txt_closing_date, txt_closing_time, txt_Place, txt_dis);
                }

                return true;
        }
        return false;
    }

    private void createInterview(final String date, final String time, final String place, final String dis) {

        pd.setMessage("Publishing Interview....");
        pd.show();

        final String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("company_id", company_id);
        hashMap.put("student_id",student_id);
        hashMap.put("vacancy_id",vacancy_id);
        hashMap.put("pTime",timestamp);
        hashMap.put("date", date);
        hashMap.put("time", time);
        hashMap.put("place", place);
        hashMap.put("description", dis);

        databaseReference = FirebaseDatabase.getInstance().getReference("Interview");
        databaseReference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(JobInterviewCreateActivity.this, "Interview published", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(JobInterviewCreateActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }



}