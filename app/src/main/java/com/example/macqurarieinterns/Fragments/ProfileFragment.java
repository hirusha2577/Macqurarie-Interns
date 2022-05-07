package com.example.macqurarieinterns.Fragments;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.AboutActivity;
import com.example.macqurarieinterns.Adapter.PostAdapter;
import com.example.macqurarieinterns.MainActivity;
import com.example.macqurarieinterns.Model.Company;
import com.example.macqurarieinterns.Model.Post;
import com.example.macqurarieinterns.Model.Student;
import com.example.macqurarieinterns.PostCreateActivity;
import com.example.macqurarieinterns.R;
import com.example.macqurarieinterns.StudentEditProfileActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment  {

    private LinearLayout tab_post, tab_about, post_add2;
    private RelativeLayout about_btn, edit_btn;
    private ImageButton moreBtn;
    private CardView post_add1, p_image_change, c_image_change;
    private TextView name, type;
    private CircleImageView profile_image;
    private ImageView cover_image;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference11, storageReference12, storageReference21, storageReference22 ;
    private static final int P_IMAGE_REQUEST = 1;
    private static final int C_IMAGE_REQUEST = 2;
    private Uri uri;
    private StorageTask uploadTask;

    private static String userType;

    private DatabaseReference databaseReference, databaseReference2;

    List<Post> postList;
    RecyclerView recyclerview_posts;
    PostAdapter postAdapter;
    String uid;


    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        tab_post = view.findViewById(R.id.tab_about);
        tab_about = view.findViewById(R.id.tab_about);
        about_btn = view.findViewById(R.id.about_btn);
        edit_btn = view.findViewById(R.id.edit_btn);
        moreBtn = view.findViewById(R.id.moreBtn);
        post_add2 = view.findViewById(R.id.post_add2);
        post_add1 = view.findViewById(R.id.post_add1);
        p_image_change = view.findViewById(R.id.p_image_change);
        c_image_change = view.findViewById(R.id.c_image_change);

        name = view.findViewById(R.id.name);
        type = view.findViewById(R.id.type);
        profile_image = view.findViewById(R.id.profile_image);
        cover_image = view.findViewById(R.id.cover_image);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        storageReference11 = FirebaseStorage.getInstance().getReference("company_profile");
        storageReference12 = FirebaseStorage.getInstance().getReference("company_cover");
        storageReference21 = FirebaseStorage.getInstance().getReference("student_profile");
        storageReference22 = FirebaseStorage.getInstance().getReference("student_cover");

        databaseReference = FirebaseDatabase.getInstance().getReference("Company").child(firebaseUser.getUid());
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    userType = "company";
                    Company company = dataSnapshot.getValue(Company.class);
                    name.setText(company.getName());
                    type.setText(company.getType());
                    if (company.getP_imageURL().equals("default")){
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    }else {
                        Picasso.get().load(company.getP_imageURL()).into(profile_image);
                    }
                    if(company.getC_imageURL().equals("default")){
                        cover_image.setImageResource(R.mipmap.ic_launcher);
                    }else {
                        Picasso.get().load(company.getC_imageURL()).into(cover_image);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    userType = "student";
                    Student student = dataSnapshot.getValue(Student.class);
                    name.setText(student.getName());
                    if (student.getP_imageURL().equals("default")){
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    }else {
                        Picasso.get().load(student.getP_imageURL()).into(profile_image);
                    }
                    if(student.getC_imageURL().equals("default")){
                        cover_image.setImageResource(R.mipmap.ic_launcher);
                    }else {
                        Picasso.get().load(student.getC_imageURL()).into(cover_image);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentEditProfileActivity.class);
                startActivity(intent);
            }
        });

        post_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostCreateActivity.class);
                startActivity(intent);
            }
        });

        post_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostCreateActivity.class);
                startActivity(intent);
            }
        });


//        moreBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(getContext(),view);
//                popupMenu.inflate(R.menu.post_popup);
//                popupMenu.show();
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        switch (menuItem.getItemId()) {
//                            case R.id.edit_post:
//                                Intent intent = new Intent(getContext(), PostCreateActivity.class);
////                intent.putExtra("company_id", u_id);
//                                startActivity(intent);
//                                return true;
//
//                            case R.id.delete_post:
//                                Intent intent1 = new Intent(getContext(), MainActivity.class);
////                intent.putExtra("company_id", u_id);
//                                startActivity(intent1);
//                                return true;
//
//                        }
//                        return false;
//                    }
//                });
//            }
//        });


        p_image_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_P_image();
            }
        });

        c_image_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_C_image();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerview_posts = view.findViewById(R.id.recyclerview_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerview_posts.setLayoutManager(layoutManager);

        postList = new ArrayList<>();

        checkUserStatus();
        loadMyPosts();

        return view;
    }

    public static String getUserType() {
        String myString = userType;
        return myString;
    }

    private void open_P_image() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, P_IMAGE_REQUEST);
    }
    private void open_C_image() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, C_IMAGE_REQUEST );
    }

    private String getFilExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private  void upload_P_Image(){
        final ProgressDialog pd =new ProgressDialog(getContext());
        pd.setMessage("uploading");
        pd.show();
        if (uri != null){
            if(userType.equals("company")) {
                final StorageReference fileReference = storageReference11.child(System.currentTimeMillis()
                        + "." + getFilExtension(uri));
                uploadTask = fileReference.putFile(uri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String mUri = downloadUri.toString();
                            databaseReference = FirebaseDatabase.getInstance().getReference("Company").child(firebaseUser.getUid());
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("P_imageURL", mUri);
                            databaseReference.updateChildren(map);
                            pd.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }else{
                final StorageReference fileReference = storageReference21.child(System.currentTimeMillis()
                        + "." + getFilExtension(uri));
                uploadTask = fileReference.putFile(uri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String mUri = downloadUri.toString();
                            databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("P_imageURL", mUri);
                            databaseReference.updateChildren(map);
                            pd.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }
        }else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    private  void upload_C_Image(){
        final ProgressDialog pd =new ProgressDialog(getContext());
        pd.setMessage("uploading");
        pd.show();

        if (uri != null){
            if(userType.equals("company")) {
                final StorageReference fileReference = storageReference12.child(System.currentTimeMillis()
                        + "." + getFilExtension(uri));

                uploadTask = fileReference.putFile(uri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String mUri = downloadUri.toString();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Company").child(firebaseUser.getUid());
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("C_imageURL", mUri);
                            databaseReference.updateChildren(map);
                            pd.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }else{
                final StorageReference fileReference = storageReference22.child(System.currentTimeMillis()
                        + "." + getFilExtension(uri));

                uploadTask = fileReference.putFile(uri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String mUri = downloadUri.toString();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(firebaseUser.getUid());
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("C_imageURL", mUri);
                            databaseReference.updateChildren(map);
                            pd.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }
        }else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == P_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            uri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "Upload is progress", Toast.LENGTH_SHORT).show();
            }else {
                upload_P_Image();

            }
        }
        if (requestCode == C_IMAGE_REQUEST && resultCode == RESULT_OK
                &&data !=null && data.getData() != null){
            uri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "Upload is progress", Toast.LENGTH_SHORT).show();
            }else {
                upload_C_Image();
            }
        }
    }

    private void loadMyPosts() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = reference.orderByChild("id").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post myPost = snapshot.getValue(Post.class);

                    //add to list
                    postList.add(myPost);

                    //adapter
                    postAdapter = new PostAdapter(getActivity(), postList);
                    //set this adapter torecycleview
                    recyclerview_posts.setAdapter(postAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        } else {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }

    }


}
