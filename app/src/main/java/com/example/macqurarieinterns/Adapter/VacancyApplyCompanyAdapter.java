package com.example.macqurarieinterns.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.JobAppliersActivity;
import com.example.macqurarieinterns.JobCreateActivity;
import com.example.macqurarieinterns.JobInterviewCreateActivity;
import com.example.macqurarieinterns.Model.VacancyApply;
import com.example.macqurarieinterns.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VacancyApplyCompanyAdapter extends RecyclerView.Adapter<VacancyApplyCompanyAdapter.MyHolder> {

    Context context;
    List<VacancyApply> vacancyApplyList;
    private DatabaseReference databaseReference;

    public VacancyApplyCompanyAdapter(Context context, List<VacancyApply> vacancyApplyList) {
        this.context = context;
        this.vacancyApplyList = vacancyApplyList;
    }

    @NonNull
    @Override
    public VacancyApplyCompanyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_job_appliers, parent, false);
        return new VacancyApplyCompanyAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String id = vacancyApplyList.get(position).getId();
        String vacancy_id = vacancyApplyList.get(position).getVacancy_id();
        String company_id = vacancyApplyList.get(position).getCompany_id();
        String student_id = vacancyApplyList.get(position).getStudent_id();
        String student_name = vacancyApplyList.get(position).getStudent_name();
        String student_dp = vacancyApplyList.get(position).getStudent_dp();
        String student_year = vacancyApplyList.get(position).getStudent_year();


        holder.studentName.setText(student_name);
        holder.studentYear.setText(student_year);

        if (student_dp.equals("noImage")){
            holder.profile_img.setVisibility(View.GONE);
        }else {
            try{
                Picasso.get().load(student_dp).into(holder.profile_img);
            }catch (Exception e){

            }
        }
        try{
            Picasso.get().load(student_dp).placeholder(R.mipmap.ic_launcher).into(holder.profile_img);
        }catch (Exception e){

        }

        holder.correct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JobInterviewCreateActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("vacancy_id", vacancy_id);
                intent.putExtra("student_id", student_id);
                intent.putExtra("company_id", company_id);
                context.startActivity(intent);
            }
        });

        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAppliers(id);
            }
        });


    }


    @Override
    public int getItemCount() {
        return vacancyApplyList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView profile_img;
        TextView studentName, studentYear;
        ImageButton correct_btn, reject_btn;

        LinearLayout applierLinearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profile_img = itemView.findViewById(R.id.profile_img);
            studentName = itemView.findViewById(R.id.stdName);
            studentYear = itemView.findViewById(R.id.stdYear);
            correct_btn = itemView.findViewById(R.id.correct_btn);
            reject_btn = itemView.findViewById(R.id.reject_btn);
            applierLinearLayout = itemView.findViewById(R.id.applierLinearLayout);
        }
    }


    private void deleteAppliers(final String id) {

        databaseReference = FirebaseDatabase.getInstance().getReference("VacancyApply").child(id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    ds.getRef().removeValue();
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
