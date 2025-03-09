package com.example.myapplication.ex2;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.myapplication.Database;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex2);

        lvCongViec = findViewById(R.id.listViewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        database = new Database(this, "GhiChu.sqlite", null, 1);

        database.queryData("DROP TABLE IF EXISTS CongViec");
        database.queryData("CREATE TABLE IF NOT EXISTS CongViec(id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV nvarchar(200))");

        database.queryData("INSERT INTO CongViec(TenCV) VALUES('Project Android')");
        database.queryData("INSERT INTO CongViec(TenCV) VALUES('Design app')");

        Cursor dataCongViec = database.getData("SELECT * FROM CongViec");
        while (dataCongViec.moveToNext()) {
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id, ten));
        }

        adapter.notifyDataSetChanged();
    }
}