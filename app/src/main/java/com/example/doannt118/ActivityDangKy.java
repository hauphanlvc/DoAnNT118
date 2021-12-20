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
    EditText ed_email,ed_password;
    Button button_dang_ky_activity;
    String userId,email_true,password_true,email_nhap,password_nhap;
    User user_nhap;
    DatabaseReference mDatabase;
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
                if (ed_email.getText().toString().equals("") || ed_password.getText().toString().equals("") )
                {
                    Toast.makeText(ActivityDangKy.this, "Nhập đầy đủ email và password đi bạn ơi", Toast.LENGTH_SHORT).show();

                }
                else
                {
//                    Log.d("fdas", "Value is: " + user_nhap);
//                    Toast.makeText(ActivityDangKy.this, "Đổi email đi bạn , email tồn tại rồi bạn ơi", Toast.LENGTH_SHORT).show();


                    email_nhap= ed_email.getText().toString();
                    password_nhap = ed_password.getText().toString();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        private static final String TAG = "read dataa";
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User user_nhap = dataSnapshot.getValue(User.class);

                        Log.d(TAG, "Value is: " + user_nhap);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
                    if (user_nhap!=null) {
                        if (user_nhap.getEmail().equals(email_nhap) && user_nhap.getPassword().equals(password_nhap)) {
                           Toast.makeText(ActivityDangKy.this, "Không ", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            v.getContext().startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ActivityDangKy.this, "Đổi email đi bạn , email tồn tại rồi bạn ơi", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        writeNewUser(email_nhap,email_nhap,password_nhap);
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        v.getContext().startActivity(intent);
                        Toast.makeText(ActivityDangKy.this, "Cos ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }
    public void writeNewUser(String userId, String email, String password) {
        User user = new User( email,password);

        mDatabase.child("user/" + userId).setValue(user);
    }
}