package com.example.doannt118;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.TenDanhSachTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivityProject extends AppCompatActivity {

    String email,project_name;
    ArrayList<TenDanhSachTask> tenDanhSachTasks;
    RVTenDanhSachTask adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        project_name = intent.getStringExtra("project_name");
        tenDanhSachTasks = new ArrayList<TenDanhSachTask>();
        CacThaoTacTrenThanhBar();
        HienThiTenCacDanhSachTask();
        ThemDanhSachTask();
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
        adapter = new RVTenDanhSachTask(tenDanhSachTasks,this);
        ListDanhSachTask.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ListDanhSachTask.setAdapter(adapter);
        ListDanhSachTask.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(new RVTenDanhSachTask.onClickListner() {
            private static final String TAG = "adapter";
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(MainActivityProject.this, "chọn danh sách ", Toast.LENGTH_SHORT).show();
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
                    ed_them_danh_sach.setText("");
//                    tenDanhSachTasks.add(new TenDanhSachTask(ed_them_danh_sach.getText().toString()));

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
}