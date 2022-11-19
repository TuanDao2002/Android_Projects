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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class BookContentActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Book content");
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable originalDrawable = ContextCompat.getDrawable(this, R.drawable.back_arrow);
            if (originalDrawable != null) {
                Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                Drawable resizeDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
                actionBar.setHomeAsUpIndicator(resizeDrawable);
            }
        }

        Intent intent = getIntent();
        String url = intent.getExtras().getString("bookURL");
        Toast.makeText(BookContentActivity.this, url, Toast.LENGTH_SHORT).show();

        WebView webView = findViewById(R.id.webContent);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(BookContentActivity.this, MainActivity.class);
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