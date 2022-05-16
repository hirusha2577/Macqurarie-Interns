package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class JobMoreDetailsActivity extends AppCompatActivity {

    private Button apply;
    private TextView title, date, description;
    private RelativeLayout apply_card;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private String student_id;

    private String user_type;
    Uri uri;
    byte[] bytes;

    private String vacancy_id, company_id, vacancy_title, vacancy_date, vacancy_description, uriString, pdf_file;
    private int PICK_PDF_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_more_details);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Job Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pDialog = new ProgressDialog(this);

        user_type = MainActivity.getUserType();

        Intent intent = getIntent();
        vacancy_id = intent.getStringExtra("vacancy_id");
        company_id = intent.getStringExtra("company_id");
        vacancy_title = intent.getStringExtra("vacancy_title");
        vacancy_date = intent.getStringExtra("vacancy_date");
        vacancy_description = intent.getStringExtra("vacancy_description");


        apply = findViewById(R.id.apply);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        description = findViewById(R.id.description);
        apply_card = findViewById(R.id.apply_card);

        title.setText(vacancy_title);
        date.setText(vacancy_date);
        description.setText(vacancy_description);

        if (user_type.equals("student")) {
            apply_card.setVisibility(View.VISIBLE);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        student_id = firebaseUser.getUid();

        storageReference = FirebaseStorage.getInstance().getReference("cv");

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogBox();
            }
        });


    }

    private void ShowDialogBox() {
        String[] options = {"File", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your CV");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    requestStoragePermission();
                    showFileChooser();
                }
                if (which == 1) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            ConvertToString(uri);
            applyVacancy();
        }
    }

    public void ConvertToString(Uri uri) {
        uriString = uri.toString();
        try {
            InputStream in = getContentResolver().openInputStream(uri);
            bytes = getBytes(in);
            Log.d("data", "onActivityResult: bytes size=" + bytes.length);
            Log.d("data", "onActivityResult: Base64string=" + Base64.encodeToString(bytes, Base64.DEFAULT));
            // String ansValue = Base64.encodeToString(bytes,Base64.DEFAULT);
            pdf_file = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.d("error", "onActivityResult: " + e.toString());
        }

    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }



    private void applyVacancy() {

        pDialog.setMessage("Apply Vacancy....");
        pDialog.show();

        final String timestamp = String.valueOf(System.currentTimeMillis());
        String filPathAndName = "CV/" + "cv_" + timestamp;

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(filPathAndName);
        ref.putFile(Uri.parse(String.valueOf(uri)))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());

                        String downloadUri = uriTask.getResult().toString();

                        if (uriTask.isSuccessful()){
                            HashMap<Object,String> hashMap = new HashMap<>();
                            hashMap.put("id", timestamp);
                            hashMap.put("company_id", company_id);
                            hashMap.put("vacancy_id", vacancy_id);
                            hashMap.put("student_id", student_id);
                            hashMap.put("cv", downloadUri);

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("VacancyApply");
                            ref.child(timestamp).setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pDialog.dismiss();
                                            Toast.makeText(JobMoreDetailsActivity.this, "Apply Vacancy", Toast.LENGTH_SHORT).show();
                                            moveActivity(JobMoreDetailsActivity.this,MainActivity.class);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pDialog.dismiss();
                                            Toast.makeText(JobMoreDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pDialog.dismiss();
                Toast.makeText(JobMoreDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }




}