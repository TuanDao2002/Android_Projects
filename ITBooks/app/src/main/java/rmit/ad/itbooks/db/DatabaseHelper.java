package rmit.ad.itbooks.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Favorite.DB";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String TABLE_NAME = "Favorite";
    public static final String ISBN13 = "_id";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String BOOK_URL = "bookURL";
    public static final String IMAGE_URL = "imageURL";
    public static final String PRICE = "price";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ISBN13 + " INTEGER PRIMARY KEY, "
            + TITLE + " TEXT NOT NULL,"
            + SUBTITLE + " TEXT, "
            + BOOK_URL + " TEXT, "
            + IMAGE_URL + " TEXT, "
            + PRICE + " TEXT"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
