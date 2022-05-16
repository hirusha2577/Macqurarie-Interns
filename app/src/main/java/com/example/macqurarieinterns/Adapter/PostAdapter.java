package com.example.macqurarieinterns.Adapter;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.MainActivity;
import com.example.macqurarieinterns.Model.Post;
import com.example.macqurarieinterns.PostCreateActivity;
import com.example.macqurarieinterns.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder> {

    Context context;
    List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final String user_id = postList.get(position).getUser_id();
        String email = postList.get(position).getEmail();
        String uname = postList.get(position).getName();
        String udp = postList.get(position).getUdp();
        String pId = postList.get(position).getpId();
        String pDescr = postList.get(position).getpDescr();
        String pImage = postList.get(position).getpImage();
        String pTimestamp = postList.get(position).getpTime();
        String pLike = postList.get(position).getLike();

        //time convert
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimestamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        holder.username.setText(uname);
        holder.posttime.setText(pTime);
        holder.postDis.setText(pDescr);
        holder.postLikes.setText(pLike+" Likes");

        //if post image eka nathnam eka hangana
        if (pImage.equals("noImage")){
            holder.postImage.setVisibility(View.GONE);
        }else {
            try{
                Picasso.get().load(pImage).into(holder.postImage);
            }catch (Exception e){

            }
        }
        try{
            Picasso.get().load(udp).placeholder(R.mipmap.ic_launcher).into(holder.profile_image);
        }catch (Exception e){

        }



        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.inflate(R.menu.post_popup);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.edit_post:
                                Intent intent = new Intent(context, PostCreateActivity.class);
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("post_id", pId);
                                intent.putExtra("post_description", pDescr);
                                intent.putExtra("post_image", pImage);
                                intent.putExtra("post_edit", "true");
                                context.startActivity(intent);
                                return true;

                            case R.id.delete_post:
//                                Intent intent1 = new Intent(context, MainActivity.class);
                                Toast.makeText(context, "delete post", Toast.LENGTH_LONG).show();
                                return true;

                        }
                        return false;
                    }
                });
            }
        });

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
            }
        });
        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "comments", Toast.LENGTH_SHORT).show();
            }
        });
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "share", Toast.LENGTH_SHORT).show();
            }
        });

        // post ekak linear layot eka click karahama
//        holder.postLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ThereProfileActivity.class);
//                intent.putExtra("user_id", user_id);
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        CircleImageView profile_image;
        TextView username,posttime,postDis,postLikes;
        ImageButton moreBtn;
        ImageView postImage;
        Button likeBtn,commentBtn,shareBtn;
        LinearLayout postLinearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            posttime = itemView.findViewById(R.id.posttime);
            postDis = itemView.findViewById(R.id.postDis);
            postLikes = itemView.findViewById(R.id.postLikes);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            postImage = itemView.findViewById(R.id.postImage);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            commentBtn = itemView.findViewById(R.id.commentBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            postLinearLayout = itemView.findViewById(R.id.postLinearLayout);
        }
    }

}
