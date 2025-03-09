package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvFruit;
    ArrayList<Fruit> fruitList = new ArrayList<Fruit>();
    EditText txtName, txtDescription;
    Spinner imageSpinner;
    Button btnAdd, btnRemove, btnEdit;
    int selectedPos = -1; // Position of the currently selected fruit
    int selectedImage = -1; // Position of the selected image in the spinner

    // Array of fruit images (ensure these resources exist in your drawable folder)
    private int[] fruitImages = {
            R.drawable.banana,
            R.drawable.dragonfruit,
            R.drawable.orange,
            R.drawable.melon,
            R.drawable.strawberry
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display for the activity
        setContentView(R.layout.activity_main); // Set the layout for the activity
        // Apply window insets to manage padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components and set up data
        Mapping();
        // Step 2: Set up the custom adapter
        ImageSpinnerAdapter spinnerAdapter = new ImageSpinnerAdapter(this, fruitImages);
        imageSpinner.setAdapter(spinnerAdapter);
        // Set up the adapter for the ListView displaying fruits
        FruitAdapter adapter = new FruitAdapter(this, R.layout.list_item,fruitList);
        lvFruit.setAdapter(adapter);


        // Listener for selecting an image from the spinner
        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedImage = i; // Update the selected image index
                //Toast.makeText(MainActivity.this, "Selected position: " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing
            }
        });
        // Listener for clicking on an item in the ListView

// Image Dropdown list (Spinner)
        lvFruit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected fruit from the list
                Fruit selectedFruit = fruitList.get(i);
                txtDescription.setText(selectedFruit.getDescription());
                txtName.setText(selectedFruit.getName());

                // Find the position of the fruit's image in the image array
                int imagePos = 0;
                for (int j = 0; j < fruitImages.length; j++){
                    if (fruitImages[j] == selectedFruit.getImage()){
                        imagePos = j; // Store the image position
                        break;
                    }
                }
                imageSpinner.setSelection(imagePos);  // Set the spinner selection to the image position
                selectedPos = i; // Set the selected position
            }
        });
        // Add fruit
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString().trim();
                String description = txtDescription.getText().toString().trim();

                // Kiểm tra nếu tên, mô tả hoặc ảnh chưa được chọn
                if (name.isEmpty() || description.isEmpty() || selectedImage == -1) {
                    // Nếu tên hoặc mô tả rỗng, hiển thị thông báo lỗi trong ô input
                    if (name.isEmpty()) {
                        txtName.setError("Vui lòng nhập tên trái cây!");
                    }
                    if (description.isEmpty()) {
                        txtDescription.setError("Vui lòng nhập mô tả!");
                    }
                    if (selectedImage == -1) {
                        Toast.makeText(MainActivity.this, "Vui lòng chọn một hình ảnh!", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                // Thêm trái cây vào danh sách
                fruitList.add(new Fruit(name, description, fruitImages[selectedImage]));
                adapter.notifyDataSetChanged(); // Cập nhật danh sách
                Toast.makeText(MainActivity.this, "Thêm mới trái cây thành công", Toast.LENGTH_SHORT).show();

                // Reset các trường nhập sau khi thêm
                txtName.setText("");
                txtDescription.setText("");
                imageSpinner.setSelection(0);
                selectedImage = -1; // Reset ảnh đã chọn
            }
        });



        btnEdit.setOnClickListener(view -> {
            if (selectedPos == -1) {
                Toast.makeText(MainActivity.this, "Hãy chọn trái cây cần sửa!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = txtName.getText().toString().trim();
            String description = txtDescription.getText().toString().trim();

            // Kiểm tra nếu tên, mô tả hoặc ảnh chưa được chọn
            if (name.isEmpty()) {
                txtName.setError("Vui lòng nhập tên trái cây!");
            }
            if (description.isEmpty()) {
                txtDescription.setError("Vui lòng nhập mô tả!");
            }
            if (selectedImage == -1) {
                Toast.makeText(MainActivity.this, "Vui lòng chọn một hình ảnh!", Toast.LENGTH_SHORT).show();
            }

            // Kiểm tra nếu tất cả các điều kiện đều hợp lệ
            if (name.isEmpty() || description.isEmpty() || selectedImage == -1) {
                return; // Nếu có lỗi, không thực hiện cập nhật
            }

            // Cập nhật thông tin trái cây
            Fruit updatedFruit = new Fruit(name, description, fruitImages[selectedImage]);
            fruitList.set(selectedPos, updatedFruit);
            adapter.notifyDataSetChanged(); // Cập nhật danh sách
            Toast.makeText(MainActivity.this, "Cập nhật trái cây thành công!", Toast.LENGTH_SHORT).show();

            // Reset các trường nhập sau khi sửa
            txtName.setText("");
            txtDescription.setText("");
            imageSpinner.setSelection(0);
            selectedPos = -1;
        });


        // Remove fruit
        btnRemove.setOnClickListener(view -> {
            if (selectedPos != -1) {
                // Hiển thị AlertDialog xác nhận xóa
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc chắn muốn xóa trái cây này không?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            // Xóa trái cây
                            fruitList.remove(selectedPos);
                            adapter.notifyDataSetChanged();
                            txtName.setText(""); // Xóa nội dung nhập
                            txtDescription.setText("");
                            selectedPos = -1; // Đặt lại vị trí được chọn
                            Toast.makeText(MainActivity.this, "Xóa trái cây thành công!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> {
                            // Hủy xóa
                            Toast.makeText(MainActivity.this, "Đã hủy xóa!", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            } else {
                Toast.makeText(MainActivity.this, "Hãy chọn trái cây cần xóa!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void Mapping(){
        lvFruit = findViewById(R.id.lvFruits);
        txtName = findViewById(R.id.etTen);
        txtDescription = findViewById(R.id.etMoTa);
        imageSpinner = findViewById(R.id.spAnh);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnRemove = findViewById(R.id.btnDelete);

        fruitList.add(new Fruit("Chuối tiêu","Chuối tiêu Long An", R.drawable.banana));
        fruitList.add(new Fruit("Thanh long","Thanh long ruột đỏ", R.drawable.dragonfruit));
        fruitList.add(new Fruit("Dưa hấu","Dưa hấu mê lon", R.drawable.melon));
        fruitList.add(new Fruit("Cam cam","Cam cam là do cam màu cam", R.drawable.orange));
        fruitList.add(new Fruit("Dâu tây","Dâu tây Đã Lạt", R.drawable.strawberry));
    };
}