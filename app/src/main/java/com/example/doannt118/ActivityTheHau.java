package com.example.doannt118;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.doannt118.Class.CongViec;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityTheHau extends AppCompatActivity {
    ImageView iv_thoat_hoan_thanh_the,iv_hoan_thanh_the;
    TextView tv_ten_viec;
    EditText ed_tieu_de,ed_mo_ta_the;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_hau);
        iv_hoan_thanh_the = (ImageView) findViewById(R.id.iv_hoan_thanh_the);
        iv_thoat_hoan_thanh_the = (ImageView) findViewById(R.id.iv_thoat_hoan_thanh_the);
        tv_ten_viec = (TextView) findViewById(R.id.tv_ten_viec);
        ed_tieu_de = (EditText) findViewById(R.id.ed_tieu_de);
        ed_mo_ta_the = (EditText) findViewById(R.id.ed_mo_ta_the);
        iv_hoan_thanh_the.setVisibility(View.GONE);
        // Danh sách nhãn
        Spinner spinner = (Spinner) findViewById(R.id.sp_nhan);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        NgayHetHan();
        DanhSachCongViec();

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