package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<String> orderedFood = new ArrayList<>();
    private static ArrayList<String> orderedDrink = new ArrayList<>();
    private int totalFoodPrice = 0;
    private int totalDrinkPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOrderFood = findViewById(R.id.btnOrderFood);
        Button btnOrderDrink = findViewById(R.id.btnOrderDrink);
        Button btnQuit = findViewById(R.id.btnQuit);
        TextView tvOrderedFood = findViewById(R.id.tvOrderedFood);
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);

        // Nhận dữ liệu từ Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Nhận danh sách món ăn và đồ uống từ Bundle
            ArrayList<String> newOrderedFood = bundle.getStringArrayList("orderedFood");
            ArrayList<String> newOrderedDrink = bundle.getStringArrayList("orderedDrink");

            // Cập nhật danh sách nếu có món mới
            if (newOrderedFood != null) {
                orderedFood.clear();
                orderedFood.addAll(newOrderedFood);
            }
            if (newOrderedDrink != null) {
                orderedDrink.clear();
                orderedDrink.addAll(newOrderedDrink);
            }

            // Nhận tổng tiền món ăn và đồ uống
            totalFoodPrice = bundle.getInt("totalFoodPrice", 0);
            totalDrinkPrice = bundle.getInt("totalDrinkPrice", 0);
        }

        // Hiển thị danh sách món ăn và đồ uống
        tvOrderedFood.setText(formatOrderText());

        // Tính tổng tiền cả món ăn và đồ uống
        int totalPrice = totalFoodPrice + totalDrinkPrice;

        // Hiển thị tổng tiền món ăn và đồ uống
        tvTotalPrice.setText("Tổng tiền: " + totalPrice + "đ");

        // Chuyển đến FoodActivity để chọn món ăn
        btnOrderFood.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
            intent.putStringArrayListExtra("orderedFood", orderedFood);
            intent.putExtra("totalFoodPrice", totalFoodPrice); // Gửi tổng tiền món ăn về MainActivity
            intent.putExtra("totalDrinkPrice", totalDrinkPrice); // Gửi tổng tiền đồ uống về MainActivity

            startActivity(intent);
        });

        // Chuyển đến DrinkActivity để chọn đồ uống
        btnOrderDrink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            intent.putStringArrayListExtra("orderedDrink", orderedDrink);
            intent.putExtra("totalFoodPrice", totalFoodPrice); // Gửi tổng tiền món ăn về MainActivity
            intent.putExtra("totalDrinkPrice", totalDrinkPrice); // Gửi tổng tiền đồ uống về MainActivity
            startActivity(intent);
        });

        // Nút thoát ứng dụng
        btnQuit.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Thoát")
                    .setMessage("Bạn có chắc chắn muốn thoát ứng dụng?")
                    .setPositiveButton("Thoát", (d, w) -> {
                        finishAffinity();
                        System.exit(0);
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });
    }

    // Hàm định dạng danh sách món ăn & đồ uống
    private String formatOrderText() {
        String foodText = orderedFood.isEmpty() ? "Chưa có món ăn" : String.join(", ", orderedFood);
        String drinkText = orderedDrink.isEmpty() ? "Chưa có đồ uống" : String.join(", ", orderedDrink);
        return "Món ăn: " + foodText + "\nĐồ uống: " + drinkText;
    }
}
