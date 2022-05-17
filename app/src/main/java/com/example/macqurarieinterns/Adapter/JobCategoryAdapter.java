package com.example.macqurarieinterns.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.Model.JobCategory;
import com.example.macqurarieinterns.R;

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

        String name = list.get(position).getName();

        holder.name.setText(name);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });


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