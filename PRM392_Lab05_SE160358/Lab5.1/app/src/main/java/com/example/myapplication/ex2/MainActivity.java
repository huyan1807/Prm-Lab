package com.example.myapplication.ex2;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ex2.CustomImageAdapter;
import com.example.myapplication.ex2.Item;
import com.example.myapplication.ex2.ItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Item> itemList = new ArrayList<>();
    private ItemAdapter adapter;

    private EditText editTitle, editDescription, editCategory;
    private ImageView imagePreview;
    private int selectedImageRes;
    private int selectedItemIndex = -1;

    // Danh sách hình ảnh
    private List<Integer> imageResources = Arrays.asList(
            R.drawable.android_icon, R.drawable.ios_icon
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editCategory = findViewById(R.id.editCategory);
        imagePreview = findViewById(R.id.imagePreview);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        adapter = new ItemAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadSampleData();
        // Xử lý chọn ảnh
        btnSelectImage.setOnClickListener(v -> showImageSelectionDialog());
//
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ModulesApplication");
        toolbar.setTitleTextColor(Color.WHITE);
        // Chọn một item để sửa
        adapter.setOnItemClickListener(position -> {
            selectedItemIndex = position;
            Item item = itemList.get(position);
            editTitle.setText(item.getTitle());
            editDescription.setText(item.getDescription());
            editCategory.setText(item.getCategory());
            imagePreview.setImageResource(item.getImageRes());
            selectedImageRes = item.getImageRes();
        });

        // Thêm mới
        btnAdd.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String description = editDescription.getText().toString();
            String category = editCategory.getText().toString();

            if (selectedImageRes != 0) {
                itemList.add(new Item(selectedImageRes, title, description, category));
                adapter.notifyDataSetChanged();
                clearInputs();
            }
        });

        // Sửa item
        btnEdit.setOnClickListener(v -> {
            if (selectedItemIndex != -1) {
                Item item = itemList.get(selectedItemIndex);
                item.setTitle(editTitle.getText().toString());
                item.setDescription(editDescription.getText().toString());
                item.setCategory(editCategory.getText().toString());
                item.setImageRes(selectedImageRes);

                adapter.notifyItemChanged(selectedItemIndex);
                clearInputs();
                selectedItemIndex = -1;
            }
        });

        // Xóa item
        btnDelete.setOnClickListener(v -> {
            if (selectedItemIndex != -1) {
                itemList.remove(selectedItemIndex);
                adapter.notifyItemRemoved(selectedItemIndex);
                selectedItemIndex = -1;
                clearInputs();
            }
        });
    }
    private void loadSampleData() {
        itemList.add(new Item(R.drawable.android_icon, "ListView trong Android",
                "ListView trong Android là một thành phần dùng để nhóm nhiều mục (item)...", "Android"));

        itemList.add(new Item(R.drawable.ios_icon, "Xử lý sự kiện trong iOS",
                "Xử lý sự kiện trong iOS. Sau khi các bạn đã biết cách thiết kế giao diện...", "iOS"));

        adapter.notifyDataSetChanged();
    }
    // Hiển thị hộp thoại chọn ảnh
    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hình ảnh");

        ListView listView = new ListView(this);
        CustomImageAdapter adapter = new CustomImageAdapter(this, imageResources);
        listView.setAdapter(adapter);

        builder.setView(listView);
        AlertDialog dialog = builder.create();
        dialog.show();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedImageRes = imageResources.get(position);
            imagePreview.setImageResource(selectedImageRes);
            dialog.dismiss();
        });
    }

    // Xóa dữ liệu nhập
    private void clearInputs() {
        editTitle.setText("");
        editDescription.setText("");
        editCategory.setText("");
        imagePreview.setImageResource(0);
        selectedImageRes = 0;
    }
}
