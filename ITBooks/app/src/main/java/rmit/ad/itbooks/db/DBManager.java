package rmit.ad.itbooks.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import rmit.ad.itbooks.model.Book;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;
    private SQLiteDatabase database;
    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertFavoriteBook(Book book) {
        open();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ISBN13, book.getIsbn13());
        contentValue.put(DatabaseHelper.TITLE, book.getTitle());
        contentValue.put(DatabaseHelper.SUBTITLE, book.getSubtitle());
        contentValue.put(DatabaseHelper.BOOK_URL, book.getBookURL());
        contentValue.put(DatabaseHelper.IMAGE_URL, book.getImageURL());
        contentValue.put(DatabaseHelper.PRICE, book.getPrice());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
        database.close();
    }

    public void deleteFavoriteBook(String isbn13) {
        open();
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ISBN13 + "=?" , new String[]{isbn13});
        database.close();
    }

    public ArrayList<Book> fetchFavoriteBooks() {
        open();
        ArrayList<Book> favoriteBooks = new ArrayList<>();
        String[] columns = new String[] {
                DatabaseHelper.ISBN13,
                DatabaseHelper.TITLE,
                DatabaseHelper.SUBTITLE,
                DatabaseHelper.BOOK_URL,
                DatabaseHelper.IMAGE_URL,
                DatabaseHelper.PRICE
        };

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String isbn13 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ISBN13));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TITLE));
                String subTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBTITLE));
                String bookURL = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BOOK_URL));
                String imageURL = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.IMAGE_URL));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRICE));

                favoriteBooks.add(new Book(isbn13, title, subTitle, bookURL, imageURL, price));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return favoriteBooks;
    }

    public boolean checkFavoriteExist(String isbn13) {
        open();
        String[] columns = { DatabaseHelper.ISBN13 };
        String selection = DatabaseHelper.ISBN13 + " =?";
        String[] selectionArgs = { isbn13 };
        String limit = "1";

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        database.close();
        return exists;
    }
}
