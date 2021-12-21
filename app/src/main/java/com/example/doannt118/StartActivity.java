package com.example.doannt118;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    Button button_dang_nhap,button_dang_ky;
    TextView tv_demo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        button_dang_ky = (Button) findViewById(R.id.button_dang_ky);
        button_dang_nhap = (Button) findViewById(R.id.button_dang_nhap);
        button_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityDangNhap.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });
        button_dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityDangKy.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });


    }
}