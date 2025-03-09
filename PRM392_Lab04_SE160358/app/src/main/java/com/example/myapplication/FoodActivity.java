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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {
    private final List<String> foodList = Arrays.asList("Phở Hà Nội", "Bún Bò Huế", "Mì Quảng", "Hủ Tíu Sài Gòn");
    private final Map<String, Integer> foodPrices = new HashMap<>();
    private final ArrayList<String> selectedFoods = new ArrayList<>();
    private int totalFoodPrice = 0; // Tổng tiền món ăn
    private int totalDrinkPrice = 0; // Tổng tiền đồ uống, sẽ nhận từ DrinkActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        ScrollView scrollView = findViewById(R.id.scrollViewFood);
        LinearLayout foodListLayout = findViewById(R.id.foodListLayout);
        Button btnOrderFood = findViewById(R.id.btnOrderFood);

        // Nhận tổng tiền đồ uống từ DrinkActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            totalDrinkPrice = bundle.getInt("totalDrinkPrice", 0); // Lấy tổng tiền đồ uống
        }

        // Khai báo giá tiền cho từng loại món ăn
        foodPrices.put("Phở Hà Nội", 40000);
        foodPrices.put("Bún Bò Huế", 35000);
        foodPrices.put("Mì Quảng", 30000);
        foodPrices.put("Hủ Tíu Sài Gòn", 25000);

        // Tạo danh sách món ăn dưới dạng CheckBox
        for (String food : foodList) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(food + " - " + foodPrices.get(food) + "đ");
            checkBox.setTextSize(18);
            checkBox.setTextColor(Color.parseColor("#4A4A4A"));
            checkBox.setPadding(12, 12, 12, 12);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int price = foodPrices.get(food);
                if (isChecked) {
                    selectedFoods.add(food);
                    totalFoodPrice += price;
                } else {
                    selectedFoods.remove(food);
                    totalFoodPrice -= price;
                }
            });
            foodListLayout.addView(checkBox);
        }

        // Nút đặt món - gửi danh sách và tổng tiền về MainActivity
        btnOrderFood.setOnClickListener(v -> {
            if (selectedFoods.isEmpty()) {
                Toast.makeText(FoodActivity.this, "Bạn chưa chọn món ăn nào.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(FoodActivity.this, MainActivity.class);

            // Tạo một Bundle để gửi dữ liệu
            Bundle resultBundle = new Bundle();
            resultBundle.putStringArrayList("orderedFood", selectedFoods);
            resultBundle.putInt("totalFoodPrice", totalFoodPrice); // Gửi tổng tiền món ăn
            resultBundle.putInt("totalDrinkPrice", totalDrinkPrice); // Gửi tổng tiền đồ uống

            // Gửi Bundle vào Intent
            intent.putExtras(resultBundle);

            startActivity(intent);
        });
    }
}
