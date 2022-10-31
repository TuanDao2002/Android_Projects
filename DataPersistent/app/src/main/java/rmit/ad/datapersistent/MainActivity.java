package rmit.ad.datapersistent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
        showCurrencyList();

        Button btnAdd = findViewById(R.id.button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitCurrency(view);
            }
        });
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
        } else {
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setVisibility(View.VISIBLE);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Are you sure you want to delete?")
                            .setPositiveButton("Delete", null).create().show();
                }
            });
        }
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