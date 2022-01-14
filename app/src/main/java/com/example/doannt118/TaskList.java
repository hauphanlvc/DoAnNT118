package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doannt118.Class.TenCacTask;
import com.example.doannt118.Class.TenDanhSachTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskList extends AppCompatActivity {
    TextView boards_danh_sach_task_list;
    EditText ed_them_task;
    FloatingActionButton fab_them_task;
    String email,project_name,task_list_name;
    ImageView iv_back_task_list;
    ArrayList<TenCacTask> tenCacTasks;
    RVDanhSachThe adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        boards_danh_sach_task_list = (TextView) findViewById(R.id.boards_danh_sach_task_list);
        ed_them_task = (EditText) findViewById(R.id.ed_them_task);
        fab_them_task = (FloatingActionButton) findViewById(R.id.fab_them_task);
        iv_back_task_list = (ImageView) findViewById(R.id.iv_back_task_list);
        tenCacTasks = new ArrayList<TenCacTask>();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        project_name = intent.getStringExtra("project_name");
        task_list_name = intent.getStringExtra("task_list_name");
        boards_danh_sach_task_list.setText(task_list_name);
        iv_back_task_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        HienThiTenCacDanhSachTask();
        ThemDanhSachThe();
    }
    public void printResult()
    {
        Log.d("TAG", "printResult: "+tenCacTasks.toString());
        RecyclerView ListDanhSachTask = (RecyclerView) findViewById(R.id.List_danh_sach_task_list);
        adapter = new RVDanhSachThe(tenCacTasks,this);
        ListDanhSachTask.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ListDanhSachTask.setAdapter(adapter);
        ListDanhSachTask.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(new RVDanhSachThe.onClickListner() {
            private static final String TAG = "adapter";
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(TaskList.this, "chọn dự án ", Toast.LENGTH_SHORT).show();
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
        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").addValueEventListener(new ValueEventListener() {
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
                    Log.d("Tag", "onClick: " + reference.getRef().toString());
                    ed_them_task.setText("");


                }
            }
        });

    }

}