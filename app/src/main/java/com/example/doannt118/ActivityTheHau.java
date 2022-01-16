package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.doannt118.Class.CongViec;
import com.example.doannt118.Class.TenCacTask;
import com.example.doannt118.Class.TenThanhVienThe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityTheHau extends AppCompatActivity {
    ImageView iv_thoat_hoan_thanh_the,iv_hoan_thanh_the;
    TextView tv_ten_viec,tv_thanh_vien;
    EditText ed_tieu_de,ed_mo_ta_the;
    ArrayList<TenCacTask> tenCacTasks;
    Dialog dialog;
    ArrayList<TenThanhVienThe> thanhVienThes;
    String email,project_name,task_list_name,task_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_hau);
        iv_hoan_thanh_the = (ImageView) findViewById(R.id.iv_hoan_thanh_the);
        iv_thoat_hoan_thanh_the = (ImageView) findViewById(R.id.iv_thoat_hoan_thanh_the);
        tv_ten_viec = (TextView) findViewById(R.id.tv_ten_viec);
        ed_tieu_de = (EditText) findViewById(R.id.ed_tieu_de);
        ed_mo_ta_the = (EditText) findViewById(R.id.ed_mo_ta_the);
        tv_thanh_vien = (TextView) findViewById(R.id.tv_thanh_vien);
        iv_hoan_thanh_the.setVisibility(View.GONE);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        project_name = intent.getStringExtra("project_name");
        task_list_name = intent.getStringExtra("task_list_name");
        task_name = intent.getStringExtra("task_name");
        iv_thoat_hoan_thanh_the.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        // Danh sách nhãn
        Spinner spinner = (Spinner) findViewById(R.id.sp_nhan);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        tenCacTasks = new ArrayList<TenCacTask>();
        thanhVienThes = new ArrayList<TenThanhVienThe>();
        ClickThanhVien();
        NgayHetHan();
        DanhSachCongViec();

    }
    public void ClickThanhVien()
    {
        tv_thanh_vien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllTenUserCoTrongProject();
//                displayDialog(ActivityTheHau.this);
            }
        });
    }
    public void GetAllTenUserCoTrongProject()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                thanhVienThes.clear();
                for (DataSnapshot i:dataSnapshot.child("member_of_task").getChildren())
                {
                    thanhVienThes.add(new TenThanhVienThe(i.child("ten_thanh_vien").getValue(String.class),i.child("is_added").getValue(Boolean.class)));
                    Log.d("TAG", thanhVienThes.toString());
                }
                displayDialog(ActivityTheHau.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void displayDialog(Activity activity)
    {
        dialog = new Dialog(this,R.style.Theme_Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_diaglog_thanh_vien);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        RecyclerView ListThanhVienDialog = (RecyclerView) dialog.findViewById(R.id.rv_thanh_vien_the_dialog);
        ListThanhVienDialog.setHasFixedSize(true);
        RVAdapterDanhSachThanhVienTheDiaglog adapter = new RVAdapterDanhSachThanhVienTheDiaglog(thanhVienThes, this);
//
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//

        ListThanhVienDialog.setAdapter(adapter);
        ListThanhVienDialog.setLayoutManager(linearLayoutManager);
        if (!isFinishing())
        {
            dialog.show();
        }

            Button button_cancel_them_thanh_vien_dialog = (Button) dialog.findViewById(R.id.button_cancel_them_thanh_vien_dialog);
            button_cancel_them_thanh_vien_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                    for (TenThanhVienThe i : adapter.getTenThanhVienThes()) {

                        reference.child(project_name + "/task_lists/" + task_list_name + "/tasks").child(task_name).child("member_of_task").child(i.getTen_thanh_vien()).setValue(i);

                    }
//                    dialog.dismiss();
                    dialog.cancel();
//                    finish();
                }
            });


    }
    public void NgayHetHan()
    {
        DatePickerDialog.OnDateSetListener setListener;
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        TextView tv_ngay_het_han = (TextView) findViewById(R.id.tv_ngay_het_han);
        TextView tv_show_deadline = (TextView) findViewById(R.id.tv_show_deadline);
        tv_ngay_het_han.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityTheHau.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        tv_show_deadline.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void DanhSachCongViec()
    {

    }
}