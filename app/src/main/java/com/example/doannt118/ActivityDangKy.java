package com.example.doannt118;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityDangKy extends AppCompatActivity {
    EditText ed_email, ed_password;
    Button button_dang_ky_activity;
    String email_nhap, password_nhap;
    User user_nhap;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        button_dang_ky_activity = (Button) findViewById(R.id.button_dang_ky_activity);
        ed_email = (EditText) findViewById(R.id.ed_email_dang_ky);
        ed_password = (EditText) findViewById(R.id.ed_password_dang_ky);


        button_dang_ky_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                // Lấy giá trị nhập vào
                String email = ed_email.getText().toString();
                String password = ed_password.getText().toString();
                User user = new User(email,password);
                reference.child(email).setValue(user);
//                Log.d("TAG", "onClick: ", user.getEmail());
                Intent intent = new Intent(v.getContext(), ActivityDangNhap.class);
                v.getContext().startActivity(intent);
                finish();

            }
        });

    }
}