package rmit.ad.itbooks.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rmit.ad.itbooks.R;
import rmit.ad.itbooks.util.ViewDialog;

public class SearchActivity extends AppCompatActivity {

    EditText keywordText;
    String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable originalDrawable = ContextCompat.getDrawable(this, R.drawable.back_arrow);
            if (originalDrawable != null) {
                Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                Drawable resizeDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
                actionBar.setHomeAsUpIndicator(resizeDrawable);
            }
        }

        keywordText = findViewById(R.id.keywordInput);
        keywordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyword = keywordText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button findBooksBtn = findViewById(R.id.findBooks);
        findBooksBtn.setOnClickListener(view -> {
            keyword = keyword.trim();
            Pattern specialChars = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = specialChars.matcher(keyword);

            String title = "", message = "";
            if (keyword.isEmpty()) {
                title = "Empty keyword";
                message = "Keyword must not be empty";
            } else if (matcher.find()) {
                title = "Invalid keyword";
                message = "The keyword must not have strange characters beside alphabet and numeric characters";
            }

            if (!title.isEmpty()) {
                ViewDialog viewDialog = new ViewDialog();
                viewDialog.showDialog(SearchActivity.this, message);
                return;
            }

            Intent intent = new Intent(SearchActivity.this, BookListActivity.class);
            intent.putExtra("keyword", keyword);
            startActivityForResult(intent, 200);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(SearchActivity.this,"back from book list", Toast.LENGTH_SHORT).show();
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

                    ViewDialog viewDialog = new ViewDialog();
                    viewDialog.showDialog(SearchActivity.this, message);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
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