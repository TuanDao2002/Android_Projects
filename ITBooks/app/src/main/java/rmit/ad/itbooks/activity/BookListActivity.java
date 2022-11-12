package rmit.ad.itbooks.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rmit.ad.itbooks.db.DBManager;
import rmit.ad.itbooks.model.Book;
import rmit.ad.itbooks.adapter.BookAdapter;
import rmit.ad.itbooks.http.HttpHandler;
import rmit.ad.itbooks.R;

public class BookListActivity extends AppCompatActivity {
    String keywordValue = "";
    private String json = "";
    private ListView listView;
    private SwitchCompat switchCompat;
    private LinearLayout switchCompatPanel;
    private TextView noBookText;
    private Boolean viewNewBooks = false;
    private Boolean viewFavoriteBooks = false;
    List<Book> books;
    List<Book> sortedBooks = new ArrayList<>();

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

        noBookText = findViewById(R.id.noBookText);
        switchCompatPanel = findViewById(R.id.switchSortPanel);
        switchCompat = findViewById(R.id.switchSort);
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            BookAdapter newBookAdapter;
            if (switchCompat.isChecked()) {
                sortedBooks = new ArrayList<>(books);
                Collections.sort(sortedBooks, (book1, book2) -> {
                    double price1 = Double.parseDouble(book1.getPrice().substring(1));
                    double price2 = Double.parseDouble(book2.getPrice().substring(1));
                    return (int) ((price1 - price2) * 100);
                });
                newBookAdapter = new BookAdapter(sortedBooks, this, viewFavoriteBooks, switchCompatPanel, noBookText);
                listView.setAdapter(newBookAdapter);
            } else {
                List<Book> toRemove = new ArrayList<>();
                for (Book book : books) {
                    boolean exist = false;
                    for (Book sb : sortedBooks) {
                        if (sb.getIsbn13().equals(book.getIsbn13())) {
                            exist = true;
                            break;
                        }
                    }

                    if (!exist) toRemove.add(book);
                }

                if (toRemove.size() != 0) books.removeAll(toRemove);
                newBookAdapter = new BookAdapter(books, this, viewFavoriteBooks, switchCompatPanel, noBookText);
                listView.setAdapter(newBookAdapter);
            }
        });
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
            } else if (intent.getExtras().getBoolean("favorite")) {
                viewFavoriteBooks = true;
                DBManager dbManager = new DBManager(BookListActivity.this);
                books = dbManager.fetchFavoriteBooks();
                return null;
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

                if (!viewFavoriteBooks) {
                    root = new JSONObject(json);
                    String error = root.get("error").toString();
                    if (!error.equals("0")) {
                        responseToSearchActivity(error);
                        return;
                    }

                    JSONArray array = root.getJSONArray("books");
                    if (array.length() == 0) {
                        waitingProgressBar.setVisibility(View.GONE);
                        switchCompatPanel.setVisibility(View.GONE);
                        noBookText.setVisibility(View.VISIBLE);
                        return;
                    }

                    books = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String isbn13 = object.getString("isbn13");
                        String title = object.getString("title");
                        String subtitle = object.getString("subtitle");
                        String imageURL = object.getString("image");
                        String bookURL = object.getString("url");
                        String price = object.getString("price");

                        Book newBook = new Book(isbn13, title, subtitle, bookURL, imageURL, price);
                        books.add(newBook);
                    }
                } else if (books.size() == 0) {
                    noBookText.setVisibility(View.VISIBLE);
                    noBookText.setText("There is no favorite book");
                }

                BookAdapter bookAdapter = new BookAdapter(books, BookListActivity.this, viewFavoriteBooks, switchCompatPanel, noBookText);
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
            if (viewNewBooks || viewFavoriteBooks) {
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