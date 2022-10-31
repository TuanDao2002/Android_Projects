package rmit.ad.datapersistent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;
    final String[] from = new String[] {
            DatabaseHelper.ID,
            DatabaseHelper.COUNTRY,
            DatabaseHelper.CURRENCY
    };

    final int[] to = new int[] {
            R.id.lId,
            R.id.lCountry,
            R.id.lCurrency
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVisible(R.id.addLayout, false);
        setVisible(R.id.updateLayout, false);
        showCurrencyList();
    }

    private void setVisible(int id, boolean isVisible) {
        View aView = findViewById(id);
        if (isVisible) {
            aView.setVisibility(View.VISIBLE);
        } else {
            aView.setVisibility(View.INVISIBLE);
        }
    }

    private void showCurrencyList() {
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        if (cursor.getCount() == 0) {
            setVisible(R.id.noRecordText, true);
        }

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setVisibility(View.VISIBLE);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure you want to delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbManager.delete(id);
                                showCurrencyList();
                            }
                        }).create().show();
            }
        });
    }

    public void onAddClick(View view) {
        LinearLayout addLayout = findViewById(R.id.addLayout);
        addLayout.setVisibility(View.VISIBLE);
        setVisible(R.id.noRecordText, false);
        setVisible(R.id.list, false);
    }

    public void onSubmitCurrency(View view) {
        EditText tCountry = findViewById(R.id.tCountry);
        EditText tCurrency = findViewById(R.id.tCurrency);
        dbManager.insert(tCountry.getText().toString(), tCurrency.getText().toString());
        tCountry.setText("");
        tCurrency.setText("");
        setVisible(R.id.addLayout, false);
        showCurrencyList();
    }
}