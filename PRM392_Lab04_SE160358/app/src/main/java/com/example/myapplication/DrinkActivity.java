package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrinkActivity extends AppCompatActivity {
    private final Map<String, Integer> drinkPrices = new HashMap<>();
    private final ArrayList<String> selectedDrinks = new ArrayList<>();
    private int totalDrinkPrice = 0; // Tổng tiền nước uống
    private int totalFoodPrice = 0;  // Tổng tiền món ăn, sẽ nhận từ MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        ScrollView scrollView = findViewById(R.id.scrollViewDrink);
        LinearLayout drinkListLayout = findViewById(R.id.drinkListLayout);
        Button btnOrderDrink = findViewById(R.id.btnOrderDrink);

        // Nhận tổng tiền món ăn từ MainActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            totalFoodPrice = bundle.getInt("totalFoodPrice", 0); // Lấy tổng tiền món ăn
        }

        // Khai báo giá tiền cho từng loại nước uống
        drinkPrices.put("Pepsi", 15000);
        drinkPrices.put("Heineken", 25000);
        drinkPrices.put("Tiger", 20000);
        drinkPrices.put("Sài Gòn Đỏ", 18000);
        drinkPrices.put("CocaCola", 15000);
        drinkPrices.put("Trà Sữa", 30000);

        // Hiển thị danh sách đồ uống với giá tiền
        for (Map.Entry<String, Integer> entry : drinkPrices.entrySet()) {
            String drink = entry.getKey();
            int price = entry.getValue();

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(drink + " - " + price + "đ");
            checkBox.setTextSize(18);
            checkBox.setTextColor(Color.parseColor("#4A4A4A"));
            checkBox.setPadding(12, 12, 12, 12);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedDrinks.add(drink);
                    totalDrinkPrice += price;
                } else {
                    selectedDrinks.remove(drink);
                    totalDrinkPrice -= price;
                }
            });
            drinkListLayout.addView(checkBox);
        }

        // Nút đặt đồ uống - gửi danh sách và tổng tiền về MainActivity
        btnOrderDrink.setOnClickListener(v -> {
            if (selectedDrinks.isEmpty()) {
                Toast.makeText(DrinkActivity.this, "Bạn chưa chọn đồ uống nào.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(DrinkActivity.this, MainActivity.class);
            Bundle resultBundle = new Bundle();

            // Gửi danh sách đồ uống và tổng tiền về MainActivity
            resultBundle.putStringArrayList("orderedDrink", selectedDrinks);
            resultBundle.putInt("totalDrinkPrice", totalDrinkPrice); // Gửi tổng tiền nước uống
            resultBundle.putInt("totalFoodPrice", totalFoodPrice);   // Gửi tổng tiền món ăn

            // Thêm Bundle vào Intent và chuyển về MainActivity
            intent.putExtras(resultBundle);
            startActivity(intent);
        });
    }
}
