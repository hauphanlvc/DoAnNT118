package com.example.doannt118;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityTheHau extends AppCompatActivity {
    EditText etDate;
    ImageView ivCalendar;
    TextView tvTieuDe,tvMota, tvNhan;
    FloatingActionButton red,blue,yellow,green;
    boolean isAllFabsVisible;
    View ViewTieuDe;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_hau);
//        SetGridView();
    }
//    public void SetGridView()
//    {
//        ArrayList<String> danh_sach_nhan = new ArrayList<>();
//        danh_sach_nhan.add("1");
//        danh_sach_nhan.add("2");
//        danh_sach_nhan.add("3");
//        danh_sach_nhan.add("44234");
//        GridView gr_danh_sach_nhan = (GridView) findViewById(R.id.gv_danh_sach_nhan);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,danh_sach_nhan)
//        {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                Log.d("TAG", "getView: "+position);
//                switch (position){
//                    case 0:
//                        view.setBackgroundColor(getResources().getColor(R.color.LightBlue));
//                        break;
//                    case 1:
//                        view.setBackgroundColor(getResources().getColor(R.color.Yellow));
//                        break;
//                    default:
//                        view.setBackgroundColor(getResources().getColor(R.color.Red));
//
//                };
//
//                return view;
//            }
//        };
//
//        gr_danh_sach_nhan.setAdapter(arrayAdapter);
//
//    }

}