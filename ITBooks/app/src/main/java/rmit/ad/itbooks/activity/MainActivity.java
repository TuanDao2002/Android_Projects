package rmit.ad.itbooks.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import rmit.ad.itbooks.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }
        }
    }
}