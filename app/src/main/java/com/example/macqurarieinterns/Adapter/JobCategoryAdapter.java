package com.example.macqurarieinterns.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.AdminUpdateJobCategory;
import com.example.macqurarieinterns.Model.JobCategory;
import com.example.macqurarieinterns.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class JobCategoryAdapter extends RecyclerView.Adapter<JobCategoryAdapter.JobCategoryViewholder> {
    Context context;
    ArrayList<JobCategory> list;
    public JobCategoryAdapter(Context context, ArrayList<JobCategory> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public JobCategoryViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(context)
                .inflate(R.layout.item_job_category, parent, false);
        return new JobCategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobCategoryViewholder holder, int position) {


        String id=list.get(position).getId();
        String name = list.get(position).getName();

        holder.name.setText(name);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
intentPass(id,name);

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("JobCategory").child(id);
                mPostReference.removeValue();
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });


    }
public void intentPass(String id,String Name){
    Intent intent = new Intent(context, AdminUpdateJobCategory.class);
    intent.putExtra("id", id);
    intent.putExtra("name", Name);
    context.startActivity(intent);
}
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class JobCategoryViewholder
            extends RecyclerView.ViewHolder {
        ImageButton editBtn,deleteBtn;
        TextView name;
        public JobCategoryViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.job_category);
            editBtn = itemView.findViewById(R.id.edit_cat);
            deleteBtn=itemView.findViewById(R.id.delete_cat);
        }
    }
}