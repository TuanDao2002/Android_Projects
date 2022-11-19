package rmit.ad.itbooks.http;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static String getJson(String urlStr) throws Exception {
        HttpURLConnection httpURLConnection;
        StringBuilder stringBuilder = new StringBuilder();

        URL url = new URL(urlStr);
        httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setConnectTimeout(1000);
        httpURLConnection.setReadTimeout(1000);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

}
