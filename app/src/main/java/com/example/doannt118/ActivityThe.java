package com.example.doannt118;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class ActivityThe extends AppCompatActivity {

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
        setContentView(R.layout.card_layout);

        etDate = findViewById(R.id.etDeadline);
        ivCalendar = findViewById(R.id.ivCalendar);
        tvTieuDe = findViewById(R.id.tvTieuDe);
        tvMota = findViewById(R.id.tvMota);
        ViewTieuDe=findViewById(R.id.ViewTieuDe);
        addColorButton();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityThe.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        tvTieuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(ActivityThe.this);
                mydialog.setTitle("Chỉnh sửa tiêu đề");
                final EditText title_input = new EditText(ActivityThe.this);
                title_input.setText(tvTieuDe.getText().toString());
                mydialog.setView(title_input);
                mydialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvTieuDe.setText(title_input.getText().toString());
                    }
                });
                mydialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mydialog.show();
            }
        });

        tvMota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(ActivityThe.this);
                mydialog.setTitle("Chỉnh sửa mô tả");
                final EditText title_input = new EditText(ActivityThe.this);
                title_input.setText(tvMota.getText().toString());
                mydialog.setView(title_input);
                mydialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvMota.setText(title_input.getText().toString());
                    }
                });
                mydialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mydialog.show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thẻ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.item1:

                break;
            case R.id.item2:

                break;
            case R.id.item3:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addColorButton() {
        isAllFabsVisible=false;
        red = findViewById(R.id.fabRed);
        blue = findViewById(R.id.fabBlue);
        yellow = findViewById(R.id.fabYellow);
        green = findViewById(R.id.fabGreen);
        tvNhan=findViewById(R.id.tvNhan);
        red.setVisibility(View.GONE);
        yellow.setVisibility(View.GONE);
        blue.setVisibility(View.GONE);
        green.setVisibility(View.GONE);

        tvNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabsVisible)
                {
                    red.show();
                    blue.show();
                    yellow.show();
                    green.show();
                    isAllFabsVisible=true;
                }
                else
                {
                    red.hide();
                    blue.hide();
                    yellow.hide();
                    green.hide();
                    isAllFabsVisible=false;
                }

                red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewTieuDe.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                });
                blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewTieuDe.setBackgroundColor(Color.parseColor("#ADD8E6"));
                    }
                });
                yellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewTieuDe.setBackgroundColor(Color.parseColor("#FFA500"));
                    }
                });
                green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewTieuDe.setBackgroundColor(Color.parseColor("#008080"));
                    }
                });
            }
        });
    }
}
