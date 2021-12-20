package com.example.doannt118;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_them_du_an extends AppCompatActivity {
    ImageView iv_thoat_tao_bang,iv_hoan_thanh_tao_bang;
    EditText et_ten_du_an;
    String TenDuAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_du_an);
        iv_hoan_thanh_tao_bang = (ImageView) findViewById(R.id.iv_hoan_thanh_tao_bang);
        iv_thoat_tao_bang = (ImageView) findViewById(R.id.iv_thoat_tao_bang);
        et_ten_du_an = (EditText) findViewById(R.id.et_ten_du_an);
        TenDuAn = et_ten_du_an.getText().toString();
        iv_thoat_tao_bang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_hoan_thanh_tao_bang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TenDuAn = et_ten_du_an.getText().toString();
                if (TenDuAn.equals(""))
                {
                    Toast.makeText(Activity_them_du_an.this, "Đừng bỏ trống tên dự án nhen", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(Activity_them_du_an.this, "oke rồi nhen", Toast.LENGTH_SHORT).show();

                    onBackPressed();

                }



            }
        });
    }
}