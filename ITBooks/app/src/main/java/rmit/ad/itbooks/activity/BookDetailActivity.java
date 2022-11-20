package rmit.ad.itbooks.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import rmit.ad.itbooks.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

// Activity to view the detail of a book in a website embedded in this app
public class BookDetailActivity extends AppCompatActivity {
    private boolean timeout = true;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Book detail");
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable originalDrawable = ContextCompat.getDrawable(this, R.drawable.back_arrow);
            if (originalDrawable != null) {
                Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                Drawable resizeDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
                actionBar.setHomeAsUpIndicator(resizeDrawable);
            }
        }

        Intent intent = getIntent();
        String bookURL = intent.getExtras().getString("bookURL");

        ProgressBar progressBar = findViewById(R.id.waitingForWebDetail);
        WebView webView = findViewById(R.id.webContent);

        // response error to previous activity if the website took too long to render
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (timeout) {
                        Intent intent1 = new Intent(BookDetailActivity.this, BookListActivity.class);
                        intent1.putExtra("error", "Connection error");
                        setResult(RESULT_OK, intent1);
                        finish();
                    }
                }).start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                timeout = false;
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

        // allow the website to download book from Internet to user's device
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(bookURL);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}