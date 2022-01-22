package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doannt118.Class.TenCacTask;
import com.example.doannt118.Class.TenDanhSachTask;
import com.example.doannt118.Class.TenThanhVienThe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskList extends AppCompatActivity {
    TextView boards_danh_sach_task_list;
    EditText ed_them_task;
    FloatingActionButton fab_them_task;
    String email,project_name,task_list_name;
    ImageView iv_back_task_list;
    ArrayList<TenCacTask> tenCacTasks;
    RVDanhSachThe adapter;
    NavigationView nv_chinh_sua_ten_the;
    DrawerLayout dl_danh_sach_the;
    Dialog dialog_chinh_sua_ten_danh_sach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        dl_danh_sach_the = findViewById(R.id.dl_danh_sach_the);

        nv_chinh_sua_ten_the = findViewById(R.id.nv_chinh_sua_ten_danh_sach);
        findViewById(R.id.iv_chinh_sua_ten_danh_sach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_danh_sach_the.openDrawer(GravityCompat.END);
            }
        });
        nv_chinh_sua_ten_the.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.it_chinh_danh_sach)
                        {
//                            Toast.makeText(TaskList.this, "Chọn Settings ", Toast.LENGTH_SHORT).show();
                            displayDialog(TaskList.this);
                            dl_danh_sach_the.closeDrawers();
                        }
                        return true;
                    }
                });
        nv_chinh_sua_ten_the.setItemIconTintList(null);
        boards_danh_sach_task_list = (TextView) findViewById(R.id.boards_danh_sach_task_list);
        ed_them_task = (EditText) findViewById(R.id.ed_them_task);
        fab_them_task = (FloatingActionButton) findViewById(R.id.fab_them_task);
        iv_back_task_list = (ImageView) findViewById(R.id.iv_back_task_list);
        tenCacTasks = new ArrayList<TenCacTask>();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        project_name = intent.getStringExtra("project_name");
        task_list_name = intent.getStringExtra("task_list_name");
        boards_danh_sach_task_list.setText("Các thẻ của danh sách " + task_list_name);
        iv_back_task_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        HienThiTenCacDanhSachTask();
        ThemDanhSachThe();
    }
    public void displayDialog(Activity activity)
    {
        dialog_chinh_sua_ten_danh_sach = new Dialog(this,R.style.Theme_Dialog);
        dialog_chinh_sua_ten_danh_sach.setCancelable(false);
        dialog_chinh_sua_ten_danh_sach.setContentView(R.layout.custom_dialog_chinh_sua_ten);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        EditText ed_chinh_sua_ten_danh_sach = (EditText) dialog_chinh_sua_ten_danh_sach.findViewById(R.id.ed_chinh_sua_ten_danh_sach);
        ed_chinh_sua_ten_danh_sach.setText(task_list_name);
        String old_task_list_name = task_list_name;
        if (!isFinishing())
        {
            dialog_chinh_sua_ten_danh_sach.show();
        }

        Button bt_hoan_tat_chinh_sua_dialog = (Button) dialog_chinh_sua_ten_danh_sach.findViewById(R.id.bt_hoan_tat_chinh_sua_dialog);
        bt_hoan_tat_chinh_sua_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_task_list_name = task_list_name;
                String new_task_list_name= ed_chinh_sua_ten_danh_sach.getText().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                reference.child(project_name).child("task_lists").child(old_task_list_name).child("task_list_name").setValue(new_task_list_name);

                DatabaseReference new_refernce = reference.child(project_name).child("task_lists").child(new_task_list_name);

                reference.child(project_name).child("task_lists").child(old_task_list_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        new_refernce.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    Log.d("TAG", "Success!");
                                    reference.child(project_name).child("task_lists").child(old_task_list_name).removeValue();
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
                task_list_name = new_task_list_name;
                boards_danh_sach_task_list.setText("Các thẻ của danh sách " + task_list_name);
                dialog_chinh_sua_ten_danh_sach.cancel();

            }
        });
        Button bt_cancel_chinh_sua_dialog = (Button) dialog_chinh_sua_ten_danh_sach.findViewById(R.id.bt_cancel_chinh_sua_dialog);
        bt_cancel_chinh_sua_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_chinh_sua_ten_danh_sach.cancel();
            }
        });


    }

    public void printResult()
    {
        Log.d("TAG", "printResult: "+tenCacTasks.toString());
        RecyclerView ListDanhSachTask = (RecyclerView) findViewById(R.id.List_danh_sach_task_list);
        adapter = new RVDanhSachThe(tenCacTasks,this,email,project_name,task_list_name);
        ListDanhSachTask.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ListDanhSachTask.setAdapter(adapter);
        ListDanhSachTask.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(new RVDanhSachThe.onClickListner() {
            private static final String TAG = "adapter";
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(TaskList.this,tenCacTasks.get(position).getTen_cac_task() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),  ActivityTheHau.class);
                intent.putExtra("email", email);
                intent.putExtra("project_name",project_name);
                intent.putExtra("task_list_name",task_list_name);
                intent.putExtra("task_name",tenCacTasks.get(position).getTen_cac_task());

                v.getContext().startActivity(intent);

            }
        });

    }
    public void HienThiTenCacDanhSachTask()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    tenCacTasks.clear();
                    for (DataSnapshot i: dataSnapshot.getChildren())
                    {

                        if (i.exists())
                        {
                            Log.d("TAG", "onDataChange: " + "co ton tai task name");
                            tenCacTasks.add(new TenCacTask(i.child("task_name").getValue(String.class)));
                        }
                        else
                            Log.d("TAG", "onDataChange: " + "meo ton tai task name");
                    }
                }

                printResult();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void ThemDanhSachThe()
    {

        fab_them_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_them_task.getText().toString().equals(""))
                {
                    Toast.makeText(TaskList.this, "Vui lòng đừng bỏ trống ô nhập thêm danh sách ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                    reference.child(project_name + "/task_lists/"+task_list_name + "/tasks").child(ed_them_task.getText().toString()).child("task_name").setValue(ed_them_task.getText().toString());
                    reference.child(project_name).child("member_of_project").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> list_project = new ArrayList<String>();
                            for (DataSnapshot i: dataSnapshot.getChildren())
                            {
//                                list_project.add(i.child("email_member").getValue(String.class));
                                String user_name = i.child("email_member").getValue(String.class);
                                TenThanhVienThe tenThanhVienThe = new TenThanhVienThe(user_name,false);
                                reference.child(project_name + "/task_lists/"+task_list_name + "/tasks").child(ed_them_task.getText().toString()).child("member_of_task").child(user_name).setValue(tenThanhVienThe);
                                tenCacTasks.add(new TenCacTask(ed_them_task.getText().toString()));
                                adapter.notifyDataSetChanged();
                            }
                            ed_them_task.setText("");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Log.d("Tag", "onClick: " + reference.getRef().toString());
                }
            }
        });

    }

}