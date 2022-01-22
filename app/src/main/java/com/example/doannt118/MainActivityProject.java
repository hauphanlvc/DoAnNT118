package com.example.doannt118;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.Notification;
import com.example.doannt118.Class.TenDanhSachTask;
import com.example.doannt118.Class.TenThanhVienThe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivityProject extends AppCompatActivity {

    String email,project_name;
    ArrayList<TenDanhSachTask> tenDanhSachTasks;
    RVDanhSachTaskList adapter;
    NavigationView nv_chinh_sua_ten_du_an;
    DrawerLayout dl_project_activity;
    Dialog dialog_chinh_sua_ten_du_an;
    TextView boards_danh_sach_the;
    Dialog dialog_them_thanh_vien ;
    ArrayList<TenThanhVienThe> thanhVienThes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        project_name = intent.getStringExtra("project_name");
        tenDanhSachTasks = new ArrayList<TenDanhSachTask>();
        boards_danh_sach_the = (TextView) findViewById(R.id.boards_danh_sach_the);

        dl_project_activity = (DrawerLayout) findViewById(R.id.dl_project_activity);
        nv_chinh_sua_ten_du_an = (NavigationView) findViewById(R.id.nv_chinh_sua_ten_du_an);
        findViewById(R.id.iv_navigation_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_project_activity.openDrawer(GravityCompat.END);
            }
        });
        thanhVienThes = new ArrayList<TenThanhVienThe>();
        nv_chinh_sua_ten_du_an.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.it_chinh_sua_du_an)
                {
//                            Toast.makeText(MainActivityProject.this, "Chọn Settings ", Toast.LENGTH_SHORT).show();
                    displayDialog(MainActivityProject.this);
                    dl_project_activity.closeDrawers();
                }
                else if (id == R.id.it_them_thanh_vien)
                {
//                    displayThemThanhVien(MainActivityProject.this);

                    GetAllTenUserCoTrongProject();
                    dl_project_activity.closeDrawers();
                }
                return true;
            }
        });


        CacThaoTacTrenThanhBar();
        HienThiTenCacDanhSachTask();
        ThemDanhSachTask();

    }
    public void GetAllTenUserCoTrongProject()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("member_of_project").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                thanhVienThes.clear();
                for (DataSnapshot i:dataSnapshot.getChildren())
                {
                    thanhVienThes.add(new TenThanhVienThe(i.child("email_member").getValue(String.class),true));
                    Log.d("TAG", thanhVienThes.toString());
                }
                displayThemThanhVien(MainActivityProject.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void displayThemThanhVien(Activity activity)
    {

        dialog_them_thanh_vien = new Dialog(this,R.style.Theme_Dialog);
        dialog_them_thanh_vien.setCancelable(false);
        dialog_them_thanh_vien.setContentView(R.layout.custom_dialog_them_thanh_vien);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        EditText ed_chinh_sua_ten_thanh_vien = (EditText) dialog_them_thanh_vien.findViewById(R.id.ed_chinh_sua_ten_thanh_vien);

        if (!isFinishing())
        {
            dialog_them_thanh_vien.show();
        }
        RecyclerView ListThanhVienDialog = (RecyclerView) dialog_them_thanh_vien.findViewById(R.id.rv_danh_sach_thanh_vien);
        ListThanhVienDialog.setHasFixedSize(false);
        RVAdapterDanhSachThanhVienTheDiaglog adapter = new RVAdapterDanhSachThanhVienTheDiaglog(thanhVienThes, this);
//
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ListThanhVienDialog.setAdapter(adapter);
        ListThanhVienDialog.setLayoutManager(linearLayoutManager);
        Button bt_hoan_tat_them_thanh_vien_dialog = (Button) dialog_them_thanh_vien.findViewById(R.id.bt_hoan_tat_them_thanh_vien_dialog);
        bt_hoan_tat_them_thanh_vien_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_thanh_vien = ed_chinh_sua_ten_thanh_vien.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("email").equalTo(email_thanh_vien);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(email_thanh_vien).child("notifications").child(email+"_" + project_name).setValue(new Notification(email,project_name,false,false,email+"_" + project_name));
                            dialog_them_thanh_vien.cancel();
                        }
                        else
                        {
                            ed_chinh_sua_ten_thanh_vien.setError("Tài khoàn không tồn tại ");
                            ed_chinh_sua_ten_thanh_vien.requestFocus();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        Button bt_cancel_them_thanh_vien_dialog = (Button) dialog_them_thanh_vien.findViewById(R.id.bt_cancel_them_thanh_vien_dialog);
        bt_cancel_them_thanh_vien_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_them_thanh_vien.cancel();
            }
        });


    }
    public void displayDialog(Activity activity)
    {
        dialog_chinh_sua_ten_du_an = new Dialog(this,R.style.Theme_Dialog);
        dialog_chinh_sua_ten_du_an.setCancelable(false);
        dialog_chinh_sua_ten_du_an.setContentView(R.layout.custom_dialog_chinh_sua_ten);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        TextView tv_chinh_sua_ten_danh_sach = (TextView) dialog_chinh_sua_ten_du_an.findViewById(R.id.tv_chinh_sua_ten_danh_sach);
        tv_chinh_sua_ten_danh_sach.setText("Chỉnh sửa tên dự án");
        EditText ed_chinh_sua_ten_danh_sach = (EditText) dialog_chinh_sua_ten_du_an.findViewById(R.id.ed_chinh_sua_ten_danh_sach);

        ed_chinh_sua_ten_danh_sach.setText(project_name);
        String old_project_name = project_name;
        if (!isFinishing())
        {
            dialog_chinh_sua_ten_du_an.show();
        }

        Button bt_hoan_tat_chinh_sua_dialog = (Button) dialog_chinh_sua_ten_du_an.findViewById(R.id.bt_hoan_tat_chinh_sua_dialog);
        bt_hoan_tat_chinh_sua_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_project_name = project_name;
                String new_project_name= ed_chinh_sua_ten_danh_sach.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                reference.child(old_project_name).child("project_name").setValue(new_project_name);

                DatabaseReference new_refernce = reference.child(new_project_name);
                reference.child(old_project_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        new_refernce.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    Log.d("TAG", "Success!");
                                    reference.child(old_project_name).removeValue();
                                } else {
                                    Log.d("TAG", "Copy failed!");
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                project_name = new_project_name;
                boards_danh_sach_the.setText("Các danh sách của " + project_name);
                dialog_chinh_sua_ten_du_an.cancel();
            }
        });
        Button bt_cancel_chinh_sua_dialog = (Button) dialog_chinh_sua_ten_du_an.findViewById(R.id.bt_cancel_chinh_sua_dialog);
        bt_cancel_chinh_sua_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_chinh_sua_ten_du_an.cancel();
            }
        });


    }
    public void CacThaoTacTrenThanhBar()
    {
        TextView tv_boards_danh_sach = (TextView) findViewById(R.id.boards_danh_sach_the);
        tv_boards_danh_sach.setText(project_name);
        ImageView im_back_danh_sach = (ImageView) findViewById(R.id.back_danh_sach_the);
        im_back_danh_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void printResult()
    {
        Log.d("TAG", "printResult: "+tenDanhSachTasks.toString());
        RecyclerView ListDanhSachTask = (RecyclerView) findViewById(R.id.List_danh_sach_the);
        adapter = new RVDanhSachTaskList(tenDanhSachTasks,this,email,project_name);
        ListDanhSachTask.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ListDanhSachTask.setAdapter(adapter);
        ListDanhSachTask.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(new RVDanhSachTaskList.onClickListner() {
            private static final String TAG = "adapter";
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(MainActivityProject.this, tenDanhSachTasks.get(position).getTen_danh_sach_task(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),  TaskList.class);
                intent.putExtra("email", email);
                intent.putExtra("project_name",project_name);
                intent.putExtra("task_list_name",tenDanhSachTasks.get(position).getTen_danh_sach_task());
                v.getContext().startActivity(intent);

            }
        });
    }
    public void HienThiTenCacDanhSachTask()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("task_lists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    tenDanhSachTasks.clear();
                    for (DataSnapshot i: dataSnapshot.getChildren())
                    {
                        tenDanhSachTasks.add(new TenDanhSachTask(i.child("task_list_name").getValue(String.class)));
                        if (i.exists())
                        {
                            Log.d("TAG", "onDataChange: " + "co ton tai task list name");
                        }
                        else
                            Log.d("TAG", "onDataChange: " + "meo ton tai task list name");
                    }
                }

                printResult();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void ThemDanhSachTask()
    {
        EditText ed_them_danh_sach = (EditText) findViewById(R.id.ed_them_the);
        ImageView im_back_danh_sach = (ImageView) findViewById(R.id.back_danh_sach_the);
        ImageView iv_hoan_thanh_tao_danh_sach_task = (ImageView) findViewById(R.id.iv_navigation_view);
        FloatingActionButton fab_them_danh_sach = (FloatingActionButton) findViewById(R.id.fab_them_the);
        fab_them_danh_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_them_danh_sach.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivityProject.this, "Vui lòng đừng bỏ trống ô nhập thêm danh sách ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                    reference.child(project_name).child("task_lists").child(ed_them_danh_sach.getText().toString()).child("task_list_name").setValue(ed_them_danh_sach.getText().toString());

                    tenDanhSachTasks.add(new TenDanhSachTask(ed_them_danh_sach.getText().toString()));
                    adapter.notifyDataSetChanged();
                    ed_them_danh_sach.setText("");
                }
            }
        });
//        ed_them_danh_sach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                im_back_danh_sach.setImageDrawable( ContextCompat.getDrawable(v.getContext(),android.R.drawable.ic_delete));
//                iv_hoan_thanh_tao_danh_sach_task.setImageDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.complete));
//
//            }
//        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        HienThiTenCacDanhSachTask();

    }
}