package rmit.ad.itbooks.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import rmit.ad.itbooks.R;
import rmit.ad.itbooks.util.ViewDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button searchBook = findViewById(R.id.searchBook);
        searchBook.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 100);
        });

        Button viewNewBook = findViewById(R.id.newBook);
        viewNewBook.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            intent.putExtra("new", true);
            startActivityForResult(intent, 300);
        });

        Button viewFavoriteBook = findViewById(R.id.favoriteBook);
        viewFavoriteBook.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            intent.putExtra("favorite", true);
            startActivityForResult(intent, 400);
        });

        ImageView logo = (ImageView) findViewById(R.id.logo);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 125, getResources().getDisplayMetrics());
            logo.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }

        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        logo.startAnimation(animZoomIn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this,"back from search", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 300) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this,"back from new", Toast.LENGTH_SHORT).show();
                String res;
                if (data == null) return;
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    res = (String) bundle.get("error");

                    String message;
                    if (res.equals("Connection error")) {
                        message = "Check your Internet connection and try again";
                    } else if (res.equals("Bad request error")) {
                        message = "There is error in request. Please try again";
                    } else {
                        message = "Please fix the keyword or try again";
                    }

                    ViewDialog viewDialog = new ViewDialog(MainActivity.this, message);
                    viewDialog.showDialog();
                }
            }
        }

        if (requestCode == 400) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this,"back from favorite", Toast.LENGTH_SHORT).show();
            }
        }
    }
}