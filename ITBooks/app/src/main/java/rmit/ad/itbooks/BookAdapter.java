package rmit.ad.itbooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private List<Book> bookList;
    private Context context;

    public BookAdapter(List<Book> bookList, Context context) {
        super(context, R.layout.activity_book_record, bookList);

        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_book_record, parent, false);
        }

        Book book = bookList.get(position);
        if (book != null) {
            TextView textTitle = (TextView) convertView.findViewById(R.id.title);
            textTitle.setText(book.getTitle() + "");

            TextView textSubtitle = (TextView) convertView.findViewById(R.id.subtitle);
            textSubtitle.setText(book.getSubtitle() + "");

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.loading);

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


