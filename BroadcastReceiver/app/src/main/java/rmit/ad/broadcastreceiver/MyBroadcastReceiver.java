package rmit.ad.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final String HAPPY_TEXT = "Mom I'm happy";
    private static final String MISS_TEXT = "Mom, I miss you!";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MainActivity.HAPPY_ACTION)) {
            sendMessage(context, HAPPY_TEXT);
        } else if (intent.getAction().equals(MainActivity.MISS_ACTION)) {
            sendMessage(context, MISS_TEXT);
        }
    }

    private void sendMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
