package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout; // layout chính của board
    NavigationView navigationView;
    ArrayList<TenDuAn> tenDuAns;
    FloatingActionButton fabAddTask,fabAdd,fabAddProject;
    TextView tvAddCard,tvAddProject;
    RecyleViewDanhSachDuAn adapter;

    AlertDialog.Builder diaglogDangXuat;
    // kiểm tra nút add có đang nhấn hay không
    String userId;
    Boolean isAllFabsVisible;
    String email;

    List<User> messages ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
        // Lấy email từ login
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        tenDuAns = new ArrayList<TenDuAn>();
        AddTaskButton();
        HienThiTenDuAn();
        DangXuat();

//
//        mFirebaseInstance = FirebaseDatabase.getInstance();
//
//        // get reference to 'users' node
//        mFirebaseDatabase = mFirebaseInstance.getReference("users");
    }
    public void DangXuat()
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                       int id = menuItem.getItemId();
                       if (id == R.id.menuLogOut)
                       {
                           diaglogDangXuat.setMessage("Bạn có muốn đăng xuất không? ")
                                   .setCancelable(false)
                                   .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           Intent intent = new Intent(MainActivity.this,StartActivity.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   })
                                   .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           //  Action for 'NO' Button
                                           dialog.cancel();
                                           Toast.makeText(getApplicationContext(),"Bạn chọn không xóa ",
                                                   Toast.LENGTH_SHORT).show();
                                       }
                                   });
                           AlertDialog alert = diaglogDangXuat.create();
                           alert.show();
                       }
                       else if (id == R.id.menuSettings)
                       {
                           Toast.makeText(MainActivity.this, "Chọn Settings ", Toast.LENGTH_SHORT).show();
                       }
                        return true;
                    }
                });
    }
//    public void getAllEmail(Map<String,Object> user)
    public void printResult(){

        Log.d("TAG", "printResult: "+tenDuAns.toString());
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
                Intent intent = new Intent(v.getContext(),  MainActivityProject.class);
                intent.putExtra("email", email);
                intent.putExtra("project_name",tenDuAns.get(position).getTen_Du_An());

                v.getContext().startActivity(intent);

            }
        });
    }


    public void HienThiTenDuAn()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenDuAns.clear();
                ArrayList<String> list_project = new ArrayList<String>();
                for (DataSnapshot datas: dataSnapshot.getChildren())
                {

                    for (DataSnapshot datas_1 : datas.child("member_of_project").getChildren())
                    {
//                        tenDuAns.add(new TenDuAn(datas_1.child("email_member").getValue(String.class)));
                        list_project.add(datas_1.child("email_member").getValue(String.class));

                    }
                    if (list_project.contains(email))
                    {
                        tenDuAns.add(new TenDuAn(datas.child("project_name").getValue(String.class)));
                    }
                }


                Log.d("TAG", "getListProject: "+ tenDuAns.toString());
                printResult();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        Intent intent = new Intent(v.getContext(), Activity_them_du_an.class);
                        intent.putExtra("email", email);
                        v.getContext().startActivity(intent);
                    }
                });

            }
        });



    }
}