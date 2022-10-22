package rmit.ad.week3b;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button button1 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File fileDir = MainActivity.this.getFilesDir();
                File file = new File(fileDir, "config.txt");

                try {
                    FileOutputStream outputStream = new FileOutputStream(file, true);
                    outputStream.write("hello world\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File fileDir = MainActivity.this.getFilesDir();
                File file = new File(fileDir, "config.txt");

                int length = (int) file.length();
                byte[] bytes = new byte[length];
                FileInputStream in;
                try {
                    in = new FileInputStream(file);
                    in.read(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String contents = new String(bytes);

                Toast.makeText(MainActivity.this, contents, Toast.LENGTH_SHORT).show();
            }
        });

        Button btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmation")
                        .setMessage("Do you want to delete your config file?")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(MainActivity.this, "File deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });

        Button btnNotify = findViewById(R.id.btnNotification);

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification("Hello", getApplicationContext());
            }
        });
    }

    private NotificationManager notificationManager;
    public void createNotification(String aMessage, Context context) {
        final int NOTIFY_ID = 0;
        String id = context.getString(R.string.app_name);
        String title = context.getString(R.string.app_name);
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            builder.setContentTitle(aMessage)
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentText(context.getString(R.string.app_name))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            builder.setContentTitle(aMessage)
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentText(context.getString(R.string.app_name))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
    }
}