package com.example.macqurarieinterns.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.Model.Post;
import com.example.macqurarieinterns.Model.Student;
import com.example.macqurarieinterns.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobAppliersAdapter extends RecyclerView.Adapter<JobAppliersAdapter.MyHolder> {

    Context context;
    List<Student> appliersList;
    private DatabaseReference databaseReference;

    public JobAppliersAdapter(Context context, List<Student> appliersList) {
        this.context = context;
        this.appliersList = appliersList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_job_appliers, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAppliersAdapter.MyHolder holder, int position) {

        final String student_id = appliersList.get(position).getUID();
        String email = appliersList.get(position).getEmail();
        String uname = appliersList.get(position).getName();
        String stdYear = appliersList.get(position).getYear();


        holder.studentName.setText(uname);
        holder.studentYear.setText(stdYear);

    }

    @Override
    public int getItemCount() { return appliersList.size(); }

    class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView profile_img;
        TextView studentName, studentYear, date_applied;
        ImageButton correct_btn, reject_btn;

        LinearLayout applierLinearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            profile_img = itemView.findViewById(R.id.profile_img);
            studentName = itemView.findViewById(R.id.stdName);
            studentYear = itemView.findViewById(R.id.stdYear);
            date_applied = itemView.findViewById(R.id.date_applied);
            correct_btn = itemView.findViewById(R.id.correct_btn);
            reject_btn = itemView.findViewById(R.id.reject_btn);

            applierLinearLayout = itemView.findViewById(R.id.applierLinearLayout);
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
                    deleteInterview(id);
                    Toast.makeText(context, "deleted", Toast.LENGTH_LONG).show();
                }
                if (which == 1) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }
    private void deleteInterview(final String id) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Interview").child(id);
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