package rmit.ad.itbooks.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    public static String getJson(String urlStr) throws Exception {
        HttpURLConnection httpURLConnection;
        StringBuilder stringBuilder = new StringBuilder();

        URL url = new URL(urlStr);
        httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

}
