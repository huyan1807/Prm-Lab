package com.example.myapplication.ex1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // Tham chiếu đến TextView tiêu đề
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("RecycleView"); // Đặt nội dung cho tiêu đề
        // Initialize the RecyclerView for displaying the user list
        RecyclerView rvUserList = findViewById(R.id.rvUserList);

        // Create a list of User objects with predefined data
        users = new ArrayList<>();
        users.add(new User("NguyenTT", "Tran Thanh Nguyen", "nguyentt@fpt.edu.vn"));
        users.add(new User("Antv", "Tran Van AN", "Antv@gmail.com"));
        users.add(new User("BangTT", "Tran Thanh Bang", "Bangtt@gmail.com"));
        users.add(new User("KhangTT", "Tran Thanh Khang", "khangtt@gmail.com"));
        users.add(new User("BaoNT", "Nguyen Thanh Bao", "baott@gmail.com"));
        users.add(new User("HungVH", "Vo Huy Hung", "huyhungtt@gmail.com"));
        users.add(new User("NguyenTT", "Tran Thanh Nguyen", "nguyentt@fpt.edu.vn"));
        users.add(new User("Antv", "Tran Van AN", "Antv@gmail.com"));
        users.add(new User("BangTT", "Tran Thanh Bang", "Bangtt@gmail.com"));
        users.add(new User("KhangTT", "Tran Thanh Khang", "khangtt@gmail.com"));
        users.add(new User("BaoNT", "Nguyen Thanh Bao", "baott@gmail.com"));
        users.add(new User("HungVH", "Vo Huy Hung", "huyhungtt@gmail.com"));



        // Set up the adapter and layout manager for the RecyclerView
        UserAdapter userAdapter = new UserAdapter(users);
        rvUserList.setAdapter(userAdapter);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
    }
}