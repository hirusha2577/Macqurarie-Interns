package com.example.macqurarieinterns.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.AdminStudentActivity;
import com.example.macqurarieinterns.Model.Student;
import com.example.macqurarieinterns.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentViewholder> {
    Context context;
    ArrayList<Student> list;
    public StudentAdapter(Context context, ArrayList<Student> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public studentViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(context)
                .inflate(R.layout.item_manage_student, parent, false);
        return new studentViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewholder holder, int position) {
        String id=list.get(position).getId()   ;
        String uname = list.get(position).getName();
        String studentImage=list.get(position).getP_imageURL();

        holder.student_name.setText(uname);
        if (studentImage.equals("default")){
            holder.image.setImageResource(R.mipmap.ic_launcher);
        }else {
            try {
                Picasso.get().load(studentImage).into(holder.image);
            } catch (Exception e) {

            }
        }
        try {
            Picasso.get().load(studentImage).placeholder(R.mipmap.ic_launcher).into(holder.image);
        } catch (Exception e) {

        }
        holder.deactivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("Student").child(id);
                mPostReference.child("status").setValue("0");
                Toast.makeText(context, "Student Name :"+uname+" is Deactivated", Toast.LENGTH_SHORT).show();
                intentPass();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void intentPass(){
        Intent intent = new Intent(context, AdminStudentActivity.class);
        context.startActivity(intent);
    }
    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class studentViewholder
            extends RecyclerView.ViewHolder {
        TextView student_name,deactivateBtn;
        CircleImageView image;
        public studentViewholder(@NonNull View itemView)
        {
            super(itemView);

            student_name = itemView.findViewById(R.id.student_name);
            image = itemView.findViewById(R.id.student_image);
            deactivateBtn=itemView.findViewById(R.id.deactivateBtn);
        }
    }
}