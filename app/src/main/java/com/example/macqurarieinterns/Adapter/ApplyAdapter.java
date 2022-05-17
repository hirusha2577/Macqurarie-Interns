package com.example.macqurarieinterns.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.macqurarieinterns.Model.Apply;
import com.example.macqurarieinterns.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.MyHolder>  {
    Context context;
    List<Apply> applyList;

    private DatabaseReference databaseReference;

    public ApplyAdapter(Context context, List<Apply> applyList) {
        this.context = context;
        this.applyList = applyList;
    }


    @NonNull
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_apply, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final String vacancy_id = applyList.get(position).getId();
        String txt_title = applyList.get(position).getTitle();
        String txt_address = applyList.get(position).getAddress();
        String id = applyList.get(position).getId();

        holder.title.setText(txt_title);
        holder.address.setText(txt_address);


        try {
            Picasso.get().load(applyList.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.image);
        } catch (Exception e) {

        }

        holder.deletebtn.setOnClickListener(v -> {
            ShowDialogBox(id);
        });
    }

    @Override
    public int getItemCount() {
        return applyList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView title,  address;
        ImageButton deletebtn;
        //        LinearLayout item;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            deletebtn = itemView.findViewById(R.id.delete);
//            item = itemView.findViewById(R.id.item);
        }
    }

    private void ShowDialogBox(final String id) {
        String[] options = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure to delete");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    deleteVacancy(id);
                    Toast.makeText(context, "deleted", Toast.LENGTH_LONG).show();
                }
                if (which == 1) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void deleteVacancy(final String id) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query applyQuery = databaseReference.child("Apply").orderByChild("id").equalTo(id);
        applyQuery.addListenerForSingleValueEvent(new ValueEventListener() {
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

