package rmit.ad.week2a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.bthGuess);

        textView = findViewById(R.id.txtNo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int no;
                try {
                    no = Integer.parseInt(textView.getText().toString());
                } catch (NumberFormatException | NullPointerException e) {
                    no = Integer.MIN_VALUE;
                }

                if (no % 5 == 0 && no >= 0) {
                    Toast.makeText(MainActivity.this, "lucky number", Toast.LENGTH_SHORT).show();
                } else if (no % 5 != 0 && no >= 0) {
                    Toast.makeText(MainActivity.this, "bad number", Toast.LENGTH_SHORT).show();
                } else if (no != Integer.MIN_VALUE) {
                    Toast.makeText(MainActivity.this, "must be positive", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "invalid integer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}