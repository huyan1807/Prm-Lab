package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPopupMenu = findViewById(R.id.btnPopupMenu); // Khởi tạo nút
        btnPopupMenu.setOnClickListener(v -> showPopupMenu()); // Gán sự kiện click để hiển thị popup menu
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnPopupMenu); // Tạo PopupMenu
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu()); // Gắn menu từ XML

        // Xử lý sự kiện khi chọn item từ menu
        popupMenu.setOnMenuItemClickListener(item -> {
            String itemName = item.getTitle().toString(); // Lấy tên của item được chọn
            btnPopupMenu.setText("Menu " +itemName); // Cập nhật text của button với tên item đã chọn
            return true;
        });

        popupMenu.show();  // Hiển thị PopupMenu
    }
}
