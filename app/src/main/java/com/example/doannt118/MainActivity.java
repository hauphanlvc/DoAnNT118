package com.example.doannt118;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.doannt118.Class.TenDuAn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout; // layout chính của board
    NavigationView navigationView;
    ArrayList<TenDuAn> tenDuAns = new ArrayList<TenDuAn>();
    FloatingActionButton fabAddTask,fabAdd,fabAddProject;
    TextView tvAddCard,tvAddProject;
    RecyleViewDanhSachDuAn adapter;
    DatabaseReference mFirebaseDatabase;
    FirebaseDatabase mFirebaseInstance;
    // kiểm tra nút add có đang nhấn hay không
    String userId;
    Boolean isAllFabsVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user");


        drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = findViewById(R.id.design_navigation_view);
        navigationView.setItemIconTintList(null);
        // Sau khi nhấn vào biểu tượng thông báo
        findViewById(R.id.notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NotificationsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        AddTaskButton();
        HienThiTenDuAn();

        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            private static final String TAG = "read data";
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//                tenDuAns.add(new TenDuAn(value));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
    }


    public void HienThiTenDuAn()
    {


        tenDuAns.add(new TenDuAn("du an 2"));
        tenDuAns.add(new TenDuAn("du an 123213"));
        RecyclerView ListProject = (RecyclerView) findViewById(R.id.ListProject);
        adapter = new RecyleViewDanhSachDuAn(tenDuAns,this);
        ListProject.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ListProject.setAdapter(adapter);
        ListProject.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(new RecyleViewDanhSachDuAn.onClickListner() {
            private static final String TAG = "adapter";

            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(MainActivity.this, "chọn dự án ", Toast.LENGTH_SHORT).show();


            }


        });
    }

    public void AddTaskButton()
    {
        isAllFabsVisible = false;

        fabAdd = (FloatingActionButton) findViewById(R.id.fab);
        fabAddTask = (FloatingActionButton) findViewById(R.id.fabAddTask);
        tvAddCard = (TextView) findViewById(R.id.textViewAddTask);
        tvAddProject= (TextView) findViewById(R.id.tv_add_project);
        fabAddProject = (FloatingActionButton) findViewById(R.id.fabAddProject);
        fabAddProject.setVisibility(View.GONE);
        fabAddTask.setVisibility(View.GONE);
        tvAddCard.setVisibility(View.GONE);
        tvAddProject.setVisibility(View.GONE);
        fabAdd.show();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAllFabsVisible)
                {
                    fabAddTask.show();
                    tvAddCard.setVisibility(View.VISIBLE);
                    tvAddProject.setVisibility(View.VISIBLE);
                    fabAdd.show();
                    fabAddProject.show();

                    isAllFabsVisible = true;
                }
                else
                {
                    fabAddTask.hide();
                    fabAddProject.hide();
                    tvAddCard.setVisibility(View.GONE);
                    tvAddProject.setVisibility(View.GONE);
                    fabAdd.show();
                    isAllFabsVisible= false;

                }

                fabAddTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "thêm thẻ nào ", Toast.LENGTH_SHORT).show();

                    }
                });
                fabAddProject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, "thêm dự án  nào ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), Activity_them_du_an.class);
                        v.getContext().startActivity(intent);
                    }
                });

            }
        });



    }
}