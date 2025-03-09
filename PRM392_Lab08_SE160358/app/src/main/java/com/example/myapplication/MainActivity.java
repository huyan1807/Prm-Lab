package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "notification_channel_2"; // Đổi ID mới

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Yêu cầu quyền trên Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
        }

        createNotificationChannel(); // Tạo Notification Channel

        Button sendNotification = findViewById(R.id.SendNoti);
        sendNotification.setOnClickListener(v -> sendNotification()); // Gửi thông báo khi nhấn nút
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channel";
            String description = "Channel for push notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH; // Đặt thành HIGH để luôn hiển thị
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title Push Notification")
                .setContentText("Message Push Notification")
                .setSmallIcon(R.mipmap.ic_launcher) // Sử dụng icon hợp lệ
                .setLargeIcon(bitmap)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Độ ưu tiên cao
                .setDefaults(NotificationCompat.DEFAULT_ALL); // Bật âm thanh, rung

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(getNotificationId(), builder.build());
        }
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}
