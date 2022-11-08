package rmit.ad.itbooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
//            TextView textISBN13 = (TextView) convertView.findViewById(R.id.isbn13);
//            textISBN13.setText(book.getIsbn13() + "");

            TextView textTitle = (TextView) convertView.findViewById(R.id.title);
            textTitle.setText(book.getTitle() + "");

            TextView textSubtitle = (TextView) convertView.findViewById(R.id.subtitle);
            textSubtitle.setText(book.getSubtitle() + "");


            new DownloadImageTask((ImageView) convertView.findViewById(R.id.imageView), convertView)
                    .execute(book.getImageURL());
        }

        return convertView;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        View convertView;
        private ProgressBar progressBar;

        public DownloadImageTask(ImageView bmImage, View convertView) {
            this.bmImage = bmImage;
            this.convertView = convertView;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) convertView.findViewById(R.id.loading);
            progressBar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}


