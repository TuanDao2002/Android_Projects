package rmit.ad.itbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {
    String keywordValue = "";
    private String json = "";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        listView = (ListView) findViewById(R.id.bookList);
        new GetBooks().execute();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class GetBooks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = getIntent();
            keywordValue = (String) intent.getExtras().get("keyword");

            String url = String.format("https://api.itbook.store/1.0/search/%s", keywordValue);
            json = HttpHandler.getJson(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            List<Book> books = new ArrayList<>();
            JSONObject root;

            try {
                root = new JSONObject(json);
                JSONArray array = root.getJSONArray("books");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String isbn13 = object.getString("isbn13");
                    String title = object.getString("title");
                    String subtitle = object.getString("subtitle");

                    Book newBook = new Book(isbn13, title, subtitle);
                    books.add(newBook);
                }

                BookAdapter bookAdapter = new BookAdapter(books, BookListActivity.this);
                listView.setAdapter(bookAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(BookListActivity.this, SearchActivity.class);
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