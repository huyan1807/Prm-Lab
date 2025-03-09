package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Button btnChangeBackgroundColor;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.constraintLayoutMain);
        btnChangeBackgroundColor = findViewById(R.id.btnChangeBackgroundColor);

        // Khi click vào button, hiển thị menu
        btnChangeBackgroundColor.setOnClickListener(v -> showPopupMenu());
    }

    // Hiển thị PopupMenu khi click vào button
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnChangeBackgroundColor);
        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());

        // Xử lý khi chọn item trong menu
        popupMenu.setOnMenuItemClickListener(item -> {
            changeBackgroundColor(item.getItemId());
            return true;
        });

        popupMenu.show(); // Hiển thị menu
    }

    // Đổi màu nền dựa theo item đã chọn
    private void changeBackgroundColor(int menuItemId) {
        int color = 0;

        if (menuItemId == R.id.menuItemRed) {
            color = ContextCompat.getColor(this, R.color.red);
        } else if (menuItemId == R.id.menuItemBlue) {
            color = ContextCompat.getColor(this, R.color.blue);
        } else if (menuItemId == R.id.menuItemYellow) {
            color = ContextCompat.getColor(this, R.color.yellow);
        }

        layout.setBackgroundColor(color); // Cập nhật màu nền
    }
}
