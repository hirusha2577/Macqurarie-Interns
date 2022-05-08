package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.macqurarieinterns.Fragments.JobsFragment;
import com.example.macqurarieinterns.Model.Company;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class JobCreateActivity extends AppCompatActivity {

    private EditText title,description;
    private ImageButton field_dropdown;
    private AutoCompleteTextView field;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseReference1;
    private String companyName, companyId, companyAddress, companyImage;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pd = new ProgressDialog(this);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        field_dropdown = findViewById(R.id.field_dropdown);
        field = findViewById(R.id.field);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        companyId = firebaseUser.getUid();

//        databaseReference1 = FirebaseDatabase.getInstance().getReference("Company").child(companyId);
//        databaseReference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                companyName = (String)dataSnapshot.child("name").getValue();
//                companyAddress = (String)dataSnapshot.child("address").getValue();
//                companyImage = (String)dataSnapshot.child("P_imageURL").getValue();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        String[] types ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
        ArrayAdapter<String> companyTypes = new ArrayAdapter<String>(JobCreateActivity.this , android.R.layout.simple_dropdown_item_1line, types);
        field.setAdapter(companyTypes);

        field_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field.showDropDown();
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
                String txt_title = title.getText().toString();
                String txt_field = field.getText().toString();
                String txt_description = description.getText().toString();

                if(TextUtils.isEmpty(txt_title) || TextUtils.isEmpty(txt_field) || TextUtils.isEmpty(txt_description)){
                    Toast.makeText(JobCreateActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                }else{
                    createVacancy(txt_title, txt_field, txt_description);
                }
                return true;
        }
        return false;
    }

    private void createVacancy(final String title, final String field, final String description){

        pd.setMessage("Publishing Vacancy....");
        pd.show();

        final String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", companyId);
//        hashMap.put("company_name", companyName);
//        hashMap.put("company_address", companyAddress);
//        hashMap.put("company_image", companyImage);
        hashMap.put("title",title);
        hashMap.put("field",field);
        hashMap.put("description", description);
        hashMap.put("pTime", timestamp);
        hashMap.put("appliers_count", "0");

        databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy");
        databaseReference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(JobCreateActivity.this, "Vacancy published", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(JobCreateActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}