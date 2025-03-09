package com.example.myapplication.ex5;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import com.example.myapplication.Database;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;

    public void refreshCongViecItems() {
        arrayCongViec.clear();
        Cursor dataCongViec = database.getData("SELECT * FROM CongViec");
        while (dataCongViec.moveToNext()) {
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id, ten));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex5);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvCongViec = findViewById(R.id.listViewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        database = new Database(this, "GhiChu.sqlite", null, 1);

        database.queryData("DROP TABLE IF EXISTS CongViec");
        database.queryData("CREATE TABLE IF NOT EXISTS CongViec(id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV nvarchar(200))");

        database.queryData("INSERT INTO CongViec(TenCV) VALUES('Project Android')");
        database.queryData("INSERT INTO CongViec(TenCV) VALUES('Design app')");

        refreshCongViecItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_them_cong_viec);

            EditText editTen = dialog.findViewById(R.id.editTextTenCV);
            Button buttonThem = dialog.findViewById(R.id.buttonThem);
            Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

            buttonThem.setOnClickListener(v -> {
                String ten = editTen.getText().toString();
                if (ten.equals("")) {
                    Toast.makeText(this, "Vui long nhap ten cong viec", Toast.LENGTH_SHORT).show();
                } else {
                    database.queryData("INSERT INTO CongViec(TenCV) VALUES('" + ten + "')");
                    Toast.makeText(this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    refreshCongViecItems();
                }
            });
            buttonHuy.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void generateDialogEdit(int id, String ten) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);

        EditText editTen = dialog.findViewById(R.id.editTextTenCV);
        Button buttonSua = dialog.findViewById(R.id.buttonSua);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        editTen.setText(ten);

        buttonSua.setOnClickListener(v -> {
            String tenMoi = editTen.getText().toString();
            if (tenMoi.equals("")) {
                Toast.makeText(this, "Vui long nhap ten cong viec", Toast.LENGTH_SHORT).show();
            } else {
                database.queryData("UPDATE CongViec SET TenCV = '" + tenMoi + "' WHERE id = " + id);
                Toast.makeText(this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                refreshCongViecItems();
            }
        });
        buttonHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void generateDialogDelete(int id, String ten) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xoa cong viec");
        builder.setMessage("Ban co muon xoa cong viec " + ten + " khong?");
        builder.setPositiveButton("Co", (dialog, which) -> {
            database.queryData("DELETE FROM CongViec WHERE id = " + id);
            Toast.makeText(this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
            refreshCongViecItems();
        });
        builder.setNegativeButton("Khong", null);
        builder.show();
    }
}
