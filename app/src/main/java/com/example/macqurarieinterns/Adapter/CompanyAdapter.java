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

import com.example.macqurarieinterns.AdminCompanyRegisterRequestActivity;
import com.example.macqurarieinterns.Model.Company;
import com.example.macqurarieinterns.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.companyViewholder> {
    Context context;
    ArrayList<Company> list;
    public CompanyAdapter(Context context, ArrayList<Company> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public companyViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(context)
                .inflate(R.layout.item_company, parent, false);
        return new companyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull companyViewholder holder, int position) {
        Company company=list.get(position);
        String id=list.get(position).getId();
        String uname = list.get(position).getName();
        String companyImage=list.get(position).getC_imageURL();

        holder.company_name.setText(uname);
        if (companyImage.equals("default")){
            holder.image.setImageResource(R.mipmap.ic_launcher);
        }else {
            try {
                Picasso.get().load(companyImage).into(holder.image);
            } catch (Exception e) {

            }
        }
        try {
            Picasso.get().load(companyImage).placeholder(R.mipmap.ic_launcher).into(holder.image);
        } catch (Exception e) {

        }
        holder.deactivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("Company").child(id);
                mPostReference.child("status").setValue("0");
                Toast.makeText(context, "Company Name :"+uname+" is Deactivated", Toast.LENGTH_SHORT).show();
                intentPass();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //Used To Switch Between activities within the adapter class
    public void intentPass(){
        Intent intent = new Intent(context, AdminCompanyRegisterRequestActivity.class);
        context.startActivity(intent);
    }

    class companyViewholder
            extends RecyclerView.ViewHolder {
        TextView company_name,deactivateBtn;
        CircleImageView image;
        public companyViewholder(@NonNull View itemView)
        {
            super(itemView);

            company_name = itemView.findViewById(R.id.company_name);
            image = itemView.findViewById(R.id.image);
            deactivateBtn=itemView.findViewById(R.id.deactivateBtn);
        }
    }
}