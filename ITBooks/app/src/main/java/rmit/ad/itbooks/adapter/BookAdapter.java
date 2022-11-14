package rmit.ad.itbooks.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Locale;

import rmit.ad.itbooks.R;
import rmit.ad.itbooks.db.DBManager;
import rmit.ad.itbooks.model.Book;

public class BookAdapter extends ArrayAdapter<Book> {
    private List<Book> bookList;
    private Context context;
    private boolean viewFavoriteBooks;
    private LinearLayout switchCompatPanel;
    private TextView noTextView;

    public BookAdapter(List<Book> bookList, Context context, boolean viewFavoriteBooks, LinearLayout switchCompatPanel, TextView noTextView) {
        super(context, R.layout.activity_book_record, bookList);
        this.bookList = bookList;
        this.context = context;
        this.viewFavoriteBooks = viewFavoriteBooks;
        this.switchCompatPanel = switchCompatPanel;
        this.noTextView = noTextView;
    }

    private void changeSwitchCompatPanelVisibility() {
        if (bookList.size() != 0)  {
            switchCompatPanel.setVisibility(View.VISIBLE);
        } else {
            switchCompatPanel.setVisibility(View.GONE);
            noTextView.setText("There is no favorite book");
            noTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_book_record, parent, false);
        }

        changeSwitchCompatPanelVisibility();

        Book book = getItem(position);
        if (book != null) {
            TextView textTitle = (TextView) convertView.findViewById(R.id.title);
            textTitle.setText(book.getTitle() + "");

            TextView textSubtitle = (TextView) convertView.findViewById(R.id.subtitle);
            textSubtitle.setText(book.getSubtitle() + "");

            TextView textPrice = (TextView) convertView.findViewById(R.id.price);
            if (book.getPrice().equals("$0.00")) {
                textPrice.setText("FREE");
            } else {
                textPrice.setText(book.getPrice() + "");
            }

            ImageView imageView = convertView.findViewById(R.id.imageView);
            ProgressBar progressBar = convertView.findViewById(R.id.loading);

            Button favoriteFeatureBtn = convertView.findViewById(R.id.favoriteFeatureBtn);
            boolean alreadyExistInFavorite = false;
            if (viewFavoriteBooks) {
                favoriteFeatureBtn.setText("- Remove favorite book");
            } else {
                DBManager dbManager = new DBManager(context);
                if (dbManager.checkFavoriteExist(book.getIsbn13())) {
                    favoriteFeatureBtn.setText("Already favorite".toUpperCase(Locale.ROOT));
                    favoriteFeatureBtn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.light_yellow));
                    favoriteFeatureBtn.setEnabled(false);
                    alreadyExistInFavorite = true;
                } else {
                    favoriteFeatureBtn.setText("+ Add to favorite");
                    favoriteFeatureBtn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.yellow));
                    favoriteFeatureBtn.setEnabled(true);
                }
            }

            boolean canInsertToFavorite = !alreadyExistInFavorite;
            favoriteFeatureBtn.setOnClickListener(view -> {
                DBManager dbManager = new DBManager(context);

                if (viewFavoriteBooks) {
                    dbManager.deleteFavoriteBook(book.getIsbn13());
                    bookList.remove(book);
                } else if (canInsertToFavorite) {
                    dbManager.insertFavoriteBook(book);
                }

                notifyDataSetChanged();
                changeSwitchCompatPanelVisibility();
            });

            Glide.with(convertView)
                    .load(book.getImageURL())
                    .centerCrop()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
        }

        return convertView;
    }
}


