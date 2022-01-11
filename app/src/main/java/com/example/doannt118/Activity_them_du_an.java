package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_them_du_an extends AppCompatActivity {
    ImageView iv_thoat_tao_bang,iv_hoan_thanh_tao_bang;
    EditText et_ten_du_an;
    String TenDuAn,email;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
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
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
//        Toast.makeText(Activity_them_du_an.this, email, Toast.LENGTH_SHORT).show();

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
//                    Toast.makeText(Activity_them_du_an.this, "oke rồi nhen", Toast.LENGTH_SHORT).show();
                    ThemProject(TenDuAn);
                    ThemProjectVaoUser(TenDuAn);

                    onBackPressed();

                }

            }
        });
    }
    public void ThemProject(String TenDuAn)
    {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("project");
        reference.child(TenDuAn).setValue(true);

    }
    public void ThemProjectVaoUser(String TenDuAn)
    {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        Query update_project_list = reference.orderByChild("email").equalTo(email);
        ArrayList<String> old_project_list = new ArrayList<String>();
        update_project_list.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User test = dataSnapshot.getValue(User.class);

                }
//                else
//                {
//
//                }

//        reference.child(email).child("project_list").setValue()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}