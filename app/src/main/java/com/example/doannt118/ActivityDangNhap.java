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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityDangNhap extends AppCompatActivity {
    EditText ed_email,ed_password;
    Button button_dang_nhap_activity;
    String userId,email_true,password_true,email_nhap,password_nhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        button_dang_nhap_activity = (Button) findViewById(R.id.button_dang_nhap_activity);
        ed_email = (EditText) findViewById(R.id.ed_email_dang_nhap);
        ed_password = (EditText) findViewById(R.id.ed_password_dang_nhap);
        ed_email.setText("");
        ed_password.setText("");
        password_nhap = ed_password.getText().toString();
        button_dang_nhap_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);

            }
        });
        findViewById(R.id.exit_login).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), StartActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });



    }
    public Boolean validateUsername()
    {
        email_nhap = ed_email.getText().toString();
        String noWhiteSpace = "\\\\A\\\\w{4,20}\\\\z";
        if (email_nhap.isEmpty())
        {
          ed_email.setError("Đừng để tên đăng nhập trống  ");
        }
        else if (email_nhap.length()>=15)
        {
            ed_email.setError("Tên đăng nhập quá dài");

        }
        else
        {
            ed_email.setError(null);
            return true;
        }
        return false;
    }
    public Boolean validatePassword()
    {
        password_nhap = ed_password.getText().toString();
//        String noWhiteSpace = "\\\\A\\\\w{4,20}\\\\z";
        if (password_nhap.isEmpty())
        {
            ed_password.setError("Đừng để tên đăng nhập trống  ");
        }
        else
        {
            ed_password.setError(null);
            return true;
        }
        return false;
    }
    public void loginUser(View view)
    {
        if (!validateUsername() || !validatePassword())
        {
            return;
        }
        else
        {
            isUser(); // Kiểm tra có phải là user hay không
        }
    }
    public void isUser()
    {
        email_nhap = ed_email.getText().toString();
        password_nhap = ed_password.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("email").equalTo(email_nhap);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ed_email.setError(null);

                    String passwordFromDB = dataSnapshot.child(email_nhap).child("password").getValue(String.class);
                    if (passwordFromDB.equals(password_nhap)) {
                        String emailFromDB = dataSnapshot.child(email_nhap).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("passsword", passwordFromDB);
                        startActivity(intent);

                    } else {
                        ed_password.setError("Nhập sai mật khẩu");
                        ed_password.requestFocus();
                    }
                }
                else
                {
                    ed_email.setError("Tài khoản chưa đăng ký ");
                    ed_email.requestFocus();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}