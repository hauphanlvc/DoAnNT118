package com.example.doannt118;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityProject extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);



    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
   }
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
   switch (item.getItemId())
       {
           case android.R.id.home:
               onBackPressed();
               return true;
            case R.id.menu1:
               //code xử lý khi bấm menu1
             break;
            case R.id.menu2:
                //code xử lý khi bấm menu2
                break;
           case R.id.menu3:
               //code xử lý khi bấm menu3
               break;
           default:break;
       }

       return super.onOptionsItemSelected(item);
    }*/
}