package com.example.macqurarieinterns.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.Model.Company;
import com.example.macqurarieinterns.Model.Vacancy;
import com.example.macqurarieinterns.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.MyHolder> {

    Context context;
    List<Vacancy> vacancyList;
    List<Company> companyList;

    public VacancyAdapter(Context context, List<Vacancy> vacancyList, List<Company> companyList) {
        this.context = context;
        this.vacancyList = vacancyList;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final String txt_company_id = vacancyList.get(position).getId();
        String txt_title = vacancyList.get(position).getTitle();
        String txt_description = vacancyList.get(position).getDescription();
        String txt_time = vacancyList.get(position).getpTime();

        String txt_name = companyList.get(position).getName();
        String txt_image = companyList.get(position).getP_imageURL();
        String txt_address = companyList.get(position).getAddress();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(txt_time));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        holder.title.setText(txt_title);
        holder.name.setText(txt_name);
        holder.address.setText(txt_address);
        holder.date.setText(pTime);

        if (txt_image.equals("default")){
            holder.image.setImageResource(R.mipmap.ic_launcher);
        }else {
            try {
                Picasso.get().load(txt_image).into(holder.image);
            } catch (Exception e) {

            }
        }
        try {
            Picasso.get().load(txt_image).placeholder(R.mipmap.ic_launcher).into(holder.image);
        } catch (Exception e) {

        }
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
}