package com.example.myapplication.ex1;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database;
import com.example.myapplication.R;


public class MainActivity extends AppCompatActivity {

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);

        Log.i("Lab9", "Starting data init");

        database = new Database(this, "GhiChu.sqlite", null, 1);

        database.queryData("DROP TABLE IF EXISTS CongViec");
        database.queryData("CREATE TABLE IF NOT EXISTS CongViec(id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV nvarchar(200))");

        Cursor cursorCheck = database.getData("SELECT * FROM CongViec");
        if (!cursorCheck.moveToFirst()) {
            Log.i("Lab9", "Inserting default data into the CongViec table");
            database.queryData("INSERT INTO CongViec(TenCV) VALUES('Project Android')");
            database.queryData("INSERT INTO CongViec(TenCV) VALUES('Design app')");
        } else {
            Log.i("Lab9", "CongViec table already contains data");
        }

        Cursor dataCongViec = database.getData("SELECT * FROM CongViec");
        while (dataCongViec.moveToNext()) {
            String ten = dataCongViec.getString(1);
            Toast.makeText(this, ten, Toast.LENGTH_SHORT).show();
            Log.v("Lab9Debug", ten);
        }

        cursorCheck.close();
        dataCongViec.close();
    }
}
