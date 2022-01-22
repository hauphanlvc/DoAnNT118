package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.doannt118.Class.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {
    String email;
    ArrayList<Notification> notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        findViewById(R.id.exit_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        notifications = new ArrayList<Notification>();
        getAllNotifications();


    }
    public void getAllNotifications()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Log.d("TAG", "danh sach thong bao notifcation" + dataSnapshot.getRef().toString());
                    for (DataSnapshot i: dataSnapshot.child("notifications").getChildren())
                    {
                        if ( i.child("confirm").getValue(Boolean.class) == false) {
                            notifications.add(new Notification(i.child("email_moi").getValue(String.class), i.child("project").getValue(String.class), i.child("agree").getValue(Boolean.class), i.child("confirm").getValue(Boolean.class), i.child("link_email_moi_project").getValue(String.class)));
                        }
                    }
                PrintNotifications();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void PrintNotifications()
    {
        TextView tv_khong_co_thong_bao = (TextView) findViewById(R.id.tv_khong_co_thong_bao);

        if (!notifications.isEmpty())
        {
            tv_khong_co_thong_bao.setVisibility(View.GONE);
        }
        RecyclerView ListProject = (RecyclerView) findViewById(R.id.rv_notification);
//        adapter = new RecyleViewDanhSachDuAn(tenDuAns,this);
        RvNotifications adapter = new RvNotifications(notifications,this,email);

        ListProject.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ListProject.setAdapter(adapter);
        ListProject.setLayoutManager(linearLayoutManager);
    }
}