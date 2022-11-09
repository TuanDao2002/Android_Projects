package rmit.ad.itbooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    EditText keywordText;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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
                if (data != null && data.getExtras() != null && !data.getExtras().getString("error").isEmpty()) {
                    Toast.makeText(SearchActivity.this,data.getExtras().getString("error"), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage(data.getExtras().getString("error"))
                            .setPositiveButton("OK", (dialog, i) -> {
                                dialog.dismiss();

                            });
                    builder.create().show();
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