package com.androidaura.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<Employee> array_list;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);

        if(Appconstant.flag) {

            if(db.getRecordsCount() >0 ){
                db.deleteRows();
            }

            Log.d("vt", "Inserting Data ...");
            db.addEmployee(new Employee("888261294", "Vishal", "Torgal"));
            db.addEmployee(new Employee("888261299", "Rohit", "Shah"));


            Appconstant.flag=false;
        }
        array_list = db.getAllEmployee();
        db.getJsonfromDB();
      //  Log.d("vt", "Check Data ..."+array_list);


    }



}
