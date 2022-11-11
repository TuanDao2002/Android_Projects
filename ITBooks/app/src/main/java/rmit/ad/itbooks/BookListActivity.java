package rmit.ad.itbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {
    String keywordValue = "";
    private String json = "";
    private ListView listView;
    private Boolean viewNewBooks = false;
    List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        listView = findViewById(R.id.bookList);
        new GetBooks().execute();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    private void responseToSearchActivity(String errorMessage) {
        Intent intent = new Intent(BookListActivity.this, SearchActivity.class);
        intent.putExtra("error", errorMessage);
        setResult(RESULT_OK, intent);
        finish();
    }

    private class GetBooks extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = getIntent();

            String url;
            if (intent.getExtras().getBoolean("new")) {
                url = "https://api.itbook.store/1.0/new";
                viewNewBooks = true;
            } else {
                keywordValue = (String) intent.getExtras().get("keyword");
                url = String.format("https://api.itbook.store/1.0/search/%s", keywordValue);
            }

            try {
                json = HttpHandler.getJson(url);
            } catch (Exception e) {
                responseToSearchActivity("Connection error");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            JSONObject root;

            try {
                ProgressBar waitingProgressBar = findViewById(R.id.waitingForResults);
                TextView noBookText = findViewById(R.id.noBookText);

                root = new JSONObject(json);

                String error = root.get("error").toString();
                if (!error.equals("0")) {
                    responseToSearchActivity(error);
                    return;
                }

                JSONArray array = root.getJSONArray("books");

                if (array.length() == 0) {
                    waitingProgressBar.setVisibility(View.GONE);
                    noBookText.setVisibility(View.VISIBLE);
                    return;
                }

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String isbn13 = object.getString("isbn13");
                    String title = object.getString("title");
                    String subtitle = object.getString("subtitle");
                    String imageURL = object.getString("image");
                    String bookURL = object.getString("url");
                    String price = object.getString("price");

                    if (price.equals("$0.00")) {
                        price = "FREE";
                    }

                    Book newBook = new Book(isbn13, title, subtitle, bookURL, imageURL, price);
                    books.add(newBook);
                }

                BookAdapter bookAdapter = new BookAdapter(books, BookListActivity.this);
                listView.setAdapter(bookAdapter);
                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Book book = (Book) listView.getItemAtPosition(i);
                    Uri uri = Uri.parse(book.getBookURL());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                });

                waitingProgressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                responseToSearchActivity("Bad request error");
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent;
            if (viewNewBooks) {
                intent = new Intent(BookListActivity.this, MainActivity.class);
            } else {
                intent = new Intent(BookListActivity.this, SearchActivity.class);
            }

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