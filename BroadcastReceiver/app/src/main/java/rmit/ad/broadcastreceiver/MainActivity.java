package rmit.ad.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String HAPPY_ACTION = "I'M HAPPY";
    public static final String MISS_ACTION = "I MISS YOU";
    protected MyBroadcastReceiver myBroadcastReceiver;
    protected IntentFilter intentFilter;

    public void happyClick(View view) {
        Intent intent = new Intent(HAPPY_ACTION);
        sendBroadcast(intent);
//        Toast.makeText(this, "happy", Toast.LENGTH_SHORT).show();
    }

    public void missClick(View view) {
        Intent intent = new Intent(MISS_ACTION);
        sendBroadcast(intent);
//        Toast.makeText(this, "miss", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerService();
    }

    private void registerService() {
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter(HAPPY_ACTION);
        intentFilter.addAction(MISS_ACTION);
        this.registerReceiver(myBroadcastReceiver, intentFilter);
    }
}