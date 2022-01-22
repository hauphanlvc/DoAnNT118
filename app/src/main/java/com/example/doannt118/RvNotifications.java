package com.example.doannt118;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.Notification;
import com.example.doannt118.Class.TenDanhSachTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class RvNotifications extends RecyclerView.Adapter<RvNotifications.ViewHolder>{
    private ArrayList<Notification> notifications;
    private Context context;
    private String email;
//    private static RvNotifications.onClickListner onclicklistner;
    public RvNotifications(ArrayList<Notification> notifications,Context context,String email)
    {
        this.notifications = notifications;
        this.context = context;
        this.email = email;

    }

    @Override
    public RvNotifications.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_notifications, parent, false);

        // Return a new holder instance
        RvNotifications.ViewHolder viewHolder = new RvNotifications.ViewHolder(contactView);
        return viewHolder;

    }
    class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tv_noi_dung_thong_bao;
        public Button bt_khong_tham_gia,bt_tham_gia;
        public LinearLayout linear;
        RvNotifications adapter;
        public ViewHolder(View v) {
            super(v);
            tv_noi_dung_thong_bao = (TextView) v.findViewById(R.id.tv_noi_dung_thong_bao);
            bt_khong_tham_gia = (Button) v.findViewById(R.id.bt_khong_tham_gia);
            bt_tham_gia = (Button) v.findViewById(R.id.bt_tham_gia);

            linear = (LinearLayout) v.findViewById(R.id.ll_danh_sach_notifications);
//            v.setOnClickListener(this);
//            v.setOnLongClickListener(this);

        }
//        @Override
//        public void onClick(View v) {
//            onclicklistner.onItemClick(getAdapterPosition(), v);
        }

//        @Override
//        public boolean onLongClick(View v) {
//            onclicklistner.onItemLongClick(	getLayoutPosition(), v);
//            return true;
//        }

//    public void setOnItemClickListener(RvNotifications.onClickListner onclicklistner) {
//        RvNotifications.onclicklistner = onclicklistner;
//    }
//
//    public void setHeader(View v) {
//        this.headerView = v;
//    }

//    public interface onClickListner {
//        void onItemClick(int position, View v);
////        void onItemLongClick(int position, View v);
//    }



    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
    @Override
    public void onBindViewHolder(RvNotifications.ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.tv_noi_dung_thong_bao.setText("Người dùng " + notification.getEmail_moi() + " mời bạn tham gia vào dự án " + notification.getProject()+". Bạn có muốn tham gia không ");
        holder.bt_tham_gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bt_tham_gia.setVisibility(View.GONE);
                holder.bt_khong_tham_gia.setVisibility(View.GONE);
                holder.tv_noi_dung_thong_bao.setVisibility(View.GONE);
                notification.setAgree(true);
                notifications.set(position,notification);
                notifyDataSetChanged();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("users").child(email).child("notifications").child(notification.getLink_email_moi_project()).child("confirm").setValue(true);
                reference.child("users").child(email).child("notifications").child(notification.getLink_email_moi_project()).child("agree").setValue(true);
//                DatabaseReference new_reference = FirebaseDatabase.getInstance().getReference("project");
                reference.child("project").child(notification.getProject()).child("member_of_project").child(email).child("email_member").setValue(email);
          }
        });
        holder.bt_khong_tham_gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bt_tham_gia.setVisibility(View.GONE);
                holder.bt_khong_tham_gia.setVisibility(View.GONE);
                holder.tv_noi_dung_thong_bao.setVisibility(View.GONE);
                notification.setAgree(false);
                notifications.set(position,notification);
                notifyDataSetChanged();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(email).child("notifications").child(notification.getLink_email_moi_project()).child("confirm").setValue(true);
                reference.child(email).child("notifications").child(notification.getLink_email_moi_project()).child("agree").setValue(false);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

}
