package rmit.ad.itbooks.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rmit.ad.itbooks.R;
import rmit.ad.itbooks.adapter.BookAdapter;
import rmit.ad.itbooks.db.DBManager;
import rmit.ad.itbooks.model.Book;

public class ViewDialog {
    private Activity activity;
    private String message;
    private boolean isDeleted = false;

    public ViewDialog(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_error_dialog);

        TextView text = dialog.findViewById(R.id.error_text_dialog);
        text.setText(message);

        Button dialogButton = dialog.findViewById(R.id.btn_dismiss_dialog);
        dialogButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public boolean askToDeleteDialog(DBManager dbManager, BookAdapter bookAdapter, List<Book> bookList, Book book) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_delete_dialog);

        TextView text = dialog.findViewById(R.id.error_text_dialog);
        text.setText(message);

        Button cancelButton = dialog.findViewById(R.id.btn_cancel_dialog);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        Button deleteButton = dialog.findViewById(R.id.btn_delete_dialog);
        deleteButton.setOnClickListener(view -> {
            dbManager.deleteFavoriteBook(book.getIsbn13());
            bookList.remove(book);
            bookAdapter.notifyDataSetChanged();
            bookAdapter.changeSwitchCompatPanelVisibility();
            dialog.dismiss();
        });

        dialog.show();

        return isDeleted;
    }
}
