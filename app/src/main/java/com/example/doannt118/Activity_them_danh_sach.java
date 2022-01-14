package com.example.doannt118;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_them_danh_sach extends AppCompatActivity {

    ImageView iv_thoat_them_ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_sach);

        iv_thoat_them_ds = (ImageView) findViewById(R.id.iv_thoat_tao_danh_sach);
        iv_thoat_them_ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
