package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.doannt118.Class.CongViec;
import com.example.doannt118.Class.TenCacTask;
import com.example.doannt118.Class.TenThanhVienThe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityTheHau extends AppCompatActivity {
    ImageView iv_thoat_hoan_thanh_the,iv_hoan_thanh_the;
    TextView tv_ten_viec,tv_thanh_vien;
    EditText ed_tieu_de,ed_mo_ta_the;
    ArrayList<TenCacTask> tenCacTasks;
    ArrayList<CongViec> congViecs;
    Dialog dialog,dialog_ngay_het_han;
    RVDanhSachCongViec adapter_danh_sach_cong_viec;
    ArrayList<TenThanhVienThe> thanhVienThes;
    String email,project_name,task_list_name,task_name;
    String due_date,due_time,reminder_time,description,title,tag;
    boolean done_task;
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

        tenCacTasks = new ArrayList<TenCacTask>();
        thanhVienThes = new ArrayList<TenThanhVienThe>();
        congViecs = new ArrayList<CongViec>();
        SetTieuDeVaMoTaThe();

        TieuDeVaMoTaThe();
        ClickThanhVien();
        NgayHetHan();
        getNhan();
        ThemCongViec();
        DanhSachCongViec();
        DoneTask();

    }
    public void DoneTask()
    {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                done_task = dataSnapshot.child("done_task").getValue(Boolean.class)  != null ? dataSnapshot.child("done_task").getValue(Boolean.class) : false;

                setDone_task();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setDone_task()
    {
        CheckBox cb_done_task = (CheckBox) findViewById(R.id.cb_done_task);
        cb_done_task.setChecked(done_task);
        cb_done_task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                done_task = isChecked;

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");

                reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("done_task").setValue(done_task);

            }
        });
    }
    public void SetTieuDeVaMoTaThe()
    {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               title = dataSnapshot.child("title").getValue(String.class);
               description = dataSnapshot.child("description").getValue(String.class);
               ed_tieu_de.setText(title);
               ed_mo_ta_the.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void TieuDeVaMoTaThe()
    {
        ed_tieu_de.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    String new_title = s.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");

                        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("title").setValue(new_title);

            }
        });
        ed_mo_ta_the.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String new_description = s.toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");

                reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("description").setValue(new_description);

            }
        });
    }
    public void getNhan()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tag = dataSnapshot.child("tag").getValue(String.class);
                Nhan();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void Nhan()
    {
        // Danh sách nhãn
        Spinner spinner = (Spinner) findViewById(R.id.sp_nhan);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<String> list_nhan = new ArrayList<String>();
        list_nhan.add("Cấp 1");
        list_nhan.add("Cấp 2");
        list_nhan.add("Cấp 3");

        ArrayAdapter adapter_nhan = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_nhan);
        // Specify the layout to use when the list of choices appears
        adapter_nhan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter_nhan);
//        if (tag!=null) {
//            spinner.setSelection(list_nhan.indexOf(tag));
//        }
        Log.d("TAG", "Nhan: " + tag);
        spinner.setSelection(list_nhan.indexOf(tag));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = list_nhan.get(position);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("tag").setValue(tag);
                adapter_nhan.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




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

        TextView tv_ngay_het_han = (TextView) findViewById(R.id.tv_ngay_het_han);
        TextView tv_show_deadline = (TextView) findViewById(R.id.tv_show_deadline);

        tv_ngay_het_han.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNgayHetHan();
//                ShowNgayHeHanDialog();
            }
        });
    }
    public void getNgayHetHan()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                due_date = dataSnapshot.child("due_date").getValue(String.class);
                due_time = dataSnapshot.child("due_time").getValue(String.class);
                reminder_time = dataSnapshot.child("reminder_time").getValue(String.class);
                ShowNgayHeHanDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void ShowNgayHeHanDialog()
    {
        dialog_ngay_het_han =  new Dialog(this,R.style.Theme_Dialog);
        dialog_ngay_het_han.setCancelable(false);
        dialog_ngay_het_han.setContentView(R.layout.custom_dialog_chon_ngay_het_han);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        if (!isFinishing())
        {
            dialog_ngay_het_han.show();
        }
        TextView tv_chon_ngay_het_han_diaglog = (TextView) dialog_ngay_het_han.findViewById(R.id.tv_chon_ngay_het_han_diaglog);
        TextView tv_chon_thoi_gian_het_han_diaglog = (TextView) dialog_ngay_het_han.findViewById(R.id.tv_chon_thoi_gian_het_han_diaglog);

        // Danh sách nhãn
        Spinner sp_thiet_lap_nhac_nho = (Spinner) dialog_ngay_het_han.findViewById(R.id.sp_thiet_lap_nhac_nho);
// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter_thiet_lap_nhac_nho = ArrayAdapter.createFromResource(this, R.array.list_thoi_gian_nhac_nho, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        ArrayList<String> list_thoi_gian_nhac_nho = new ArrayList<String>();
        list_thoi_gian_nhac_nho.add("Vào thời điểm ngày hết hạn");
        list_thoi_gian_nhac_nho.add("15 phút trước");
        list_thoi_gian_nhac_nho.add("1 giờ trước");
        list_thoi_gian_nhac_nho.add("2 giờ trước");
        list_thoi_gian_nhac_nho.add("1 ngày trước");
        ArrayAdapter adapter_thiet_lap_nhac_nho= new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_thoi_gian_nhac_nho);
        adapter_thiet_lap_nhac_nho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp_thiet_lap_nhac_nho.setAdapter(adapter_thiet_lap_nhac_nho);
        Calendar calendar = Calendar.getInstance();
       int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
       int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int year,month,day;
//        int day ;
       int hour = 9;
       int minute = 30;
        if (due_date!=null) {
            tv_chon_ngay_het_han_diaglog.setText(due_date);
            tv_chon_thoi_gian_het_han_diaglog.setText(due_time);
            sp_thiet_lap_nhac_nho.setSelection(list_thoi_gian_nhac_nho.indexOf(reminder_time));
            SimpleDateFormat due_date_formater = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            try {
                Date d = due_date_formater.parse(due_date);
                cal.setTime(d);
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
            } catch (ParseException ex) {
                Log.v("Exception", ex.getLocalizedMessage());
            }
        }
        if (due_time!=null)
        {
            SimpleDateFormat due_time_formater = new SimpleDateFormat("hh:mm");
            Calendar cal = Calendar.getInstance();
            try {
                Date d = due_time_formater.parse(due_date);
                cal.setTime(d);
                hour = cal.get(Calendar.HOUR_OF_DAY);
                minute = cal.get(Calendar.MINUTE);
            } catch (ParseException ex) {
                Log.v("Exception", ex.getLocalizedMessage());
            }
        }


        Button bt_cancel_chon_ngay_het_han_dialog = (Button) dialog_ngay_het_han.findViewById(R.id.bt_cancel_chon_ngay_het_han_dialog);
        Button bt_hoan_tat_chon_ngay_het_han_dialog = (Button) dialog_ngay_het_han.findViewById(R.id.bt_hoan_tat_chon_ngay_het_han_dialog);
        bt_cancel_chon_ngay_het_han_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ngay_het_han.cancel();

            }
        });

        int finalDay = day;

        int finalYear = year;
        int finalMonth = month;
        tv_chon_ngay_het_han_diaglog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityTheHau.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        tv_chon_ngay_het_han_diaglog.setText(date);
                        due_date = date;
                    }
                }, finalYear, finalMonth, finalDay);
                datePickerDialog.show();
            }
        });
        int finalHour = hour;
        int finalMinute = minute;
        tv_chon_thoi_gian_het_han_diaglog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Calendar mcurrentTime = Calendar.getInstance();
////                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
////                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityTheHau.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tv_chon_thoi_gian_het_han_diaglog.setText( selectedHour + ":" + selectedMinute);
                        due_time = selectedHour + ":" + selectedMinute;
                    }
                }, finalHour, finalMinute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
//        due_date = tv_chon_ngay_het_han_diaglog.getText().toString();
//        due_time = tv_chon_thoi_gian_het_han_diaglog.getText().toString();
        sp_thiet_lap_nhac_nho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reminder_time = list_thoi_gian_nhac_nho.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bt_hoan_tat_chon_ngay_het_han_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!due_date.equals("Chọn một ngày ") && !due_time.equals("Chọn thời gian")) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                    reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("due_date").setValue(due_date);
                    reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("due_time").setValue(due_time);
                    reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("reminder_time").setValue(reminder_time);


//                }
                dialog_ngay_het_han.cancel();
            }
        });

    }
    public void ThemCongViec()
    {
        ImageView iv_them_cong_viec = (ImageView) findViewById(R.id.iv_them_cong_viec);
        EditText ed_them_cong_viec = (EditText) findViewById(R.id.ed_them_cong_viec);
//        congViecs.add(new CongViec("fdasjf",true));
//        congViecs.add(new CongViec("fdiureoqwiruoqweasjf",false));

        iv_them_cong_viec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String them_cong_viec = ed_them_cong_viec.getText().toString();
                if (them_cong_viec.equals(""))
                {
                    Toast.makeText(ActivityTheHau.this, "Vui lòng đừng bỏ trống ô nhập thêm công việc  nhen", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
                    CongViec congViec = new CongViec(them_cong_viec,false);
                    reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("work_checklist").child(them_cong_viec).setValue(congViec);

                    congViecs.add(congViec);
                    adapter_danh_sach_cong_viec.notifyDataSetChanged();
                    ed_them_cong_viec.setText("");
                }
            }
        });

    }
    public void DanhSachCongViec()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");

        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("work_checklist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    congViecs.clear();

                    for (DataSnapshot i: dataSnapshot.getChildren())
                    {
//                        congViecs.add(i.getValue(CongViec.class));
                        congViecs.add(new CongViec(i.child("ten_cong_viec").getValue(String.class) , i.child("done").getValue(boolean.class)));
//
                    }
//                Log.d("Tag", "HienThiDanhSachCongViec: " + congViecs.toString());
                HienThiDanhSachCongViec();
//                Log.d("Tag", "HienThiDanhSachCongViec: " + congViecs.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void HienThiDanhSachCongViec()
    {
        Log.d("Tag 1", "HienThiDanhSachCongViec: " + congViecs.toString());
        RecyclerView ListCongViec = (RecyclerView) findViewById(R.id.rv_danh_sach_cong_viec);
        ListCongViec.setHasFixedSize(false);

        adapter_danh_sach_cong_viec = new RVDanhSachCongViec(congViecs,ActivityTheHau.this,email,project_name,task_list_name,task_name);
        LinearLayoutManager linearLayoutManager_danh_sach_cong_viec = new LinearLayoutManager(ActivityTheHau.this);
//
        ListCongViec.setAdapter(adapter_danh_sach_cong_viec);
        ListCongViec.setLayoutManager(linearLayoutManager_danh_sach_cong_viec);

    }

}