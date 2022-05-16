package com.example.macqurarieinterns.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.JobAppliersActivity;
import com.example.macqurarieinterns.JobCreateActivity;
import com.example.macqurarieinterns.JobInterviewActivity;
import com.example.macqurarieinterns.JobMoreDetailsActivity;
import com.example.macqurarieinterns.MainActivity;
import com.example.macqurarieinterns.Model.Company;
import com.example.macqurarieinterns.Model.Vacancy;
import com.example.macqurarieinterns.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.MyHolder> {

    Context context;
    List<Vacancy> vacancyList;
    String companyName;
    String companyAddress;
    String companyImage;

    private DatabaseReference databaseReference;

    public VacancyAdapter(Context context, List<Vacancy> vacancyList, String companyName, String companyAddress, String companyImage) {
        this.context = context;
        this.vacancyList = vacancyList;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyImage = companyImage;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_job, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println(companyName);
//        System.out.println(companyAddress);
//        System.out.println(companyImage);
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        final String vacancy_id = vacancyList.get(position).getId();
        final String company_id = vacancyList.get(position).getCompany_id();
        String txt_title = vacancyList.get(position).getTitle();
        String txt_description = vacancyList.get(position).getDescription();
        String txt_time = vacancyList.get(position).getpTime();
        String txt_field = vacancyList.get(position).getField();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(txt_time));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();


        holder.title.setText(txt_title);
        holder.name.setText(companyName);
        holder.address.setText(companyAddress);
        holder.date.setText(pTime);

//        if (companyImage.equals("default")){
//            holder.image.setImageResource(R.mipmap.ic_launcher);
//        }else {
//            try {
//                Picasso.get().load(companyImage).into(holder.image);
//            } catch (Exception e) {
//
//            }
//        }
        try {
            Picasso.get().load(companyImage).placeholder(R.mipmap.ic_launcher).into(holder.image);
        } catch (Exception e) {

        }

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.inflate(R.menu.job_popup);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.appliers_job:
                                Intent intent = new Intent(context, JobAppliersActivity.class);
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("vacancy_id", vacancy_id);
                                context.startActivity(intent);
                                return true;
                            case R.id.interview_job:
                                Intent intent1 = new Intent(context, JobInterviewActivity.class);
                                intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                intent1.putExtra("vacancy_id", vacancy_id);
                                context.startActivity(intent1);
                                return true;
                            case R.id.edit_job:
                                Intent intent2 = new Intent(context, JobCreateActivity.class);
                                intent2.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                intent2.putExtra("vacancy_id", vacancy_id);
                                intent2.putExtra("vacancy_edit", "true");
                                intent2.putExtra("vacancy_title", txt_title);
                                intent2.putExtra("vacancy_field", txt_field);
                                intent2.putExtra("vacancy_description", txt_description);
                                context.startActivity(intent2);
                                return true;

                            case R.id.delete_job:
                                ShowDialogBox(vacancy_id);
                                return true;
                        }
                        return false;
                    }
                });
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, JobMoreDetailsActivity.class);
                intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("vacancy_id", vacancy_id);
                intent1.putExtra("company_id", company_id);
                intent1.putExtra("vacancy_title", txt_title);
                intent1.putExtra("vacancy_date", pTime);
                intent1.putExtra("vacancy_description", txt_description);
                context.startActivity(intent1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return vacancyList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView title, name, address, date;
        ImageButton moreBtn;
        LinearLayout item;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            item = itemView.findViewById(R.id.item);
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

    private void deleteVacancy(final String vacancy_id) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy").child(vacancy_id);
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
