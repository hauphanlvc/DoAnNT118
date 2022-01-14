package com.example.doannt118;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivityProject extends AppCompatActivity {

    ImageView back;
    FloatingActionButton addDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        back = findViewById(R.id.back_danh_sach);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivityProject.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addDs = (FloatingActionButton) findViewById(R.id.add_ds);
        addDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivityProject.this, Activity_them_danh_sach.class);
                startActivity(intent);
            }
        });

    }

}