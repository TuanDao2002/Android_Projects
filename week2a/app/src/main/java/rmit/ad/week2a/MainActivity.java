package rmit.ad.week2a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button, btnCallStudent;
    TextView textView;
    int no = Integer.MIN_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.bthGuess);
        btnCallStudent = findViewById(R.id.btnCallStudent);
        textView = findViewById(R.id.txtNo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    no = Integer.parseInt(textView.getText().toString());
                } catch (NumberFormatException | NullPointerException e) {
                    no = Integer.MIN_VALUE;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnCallStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentActivity.class);

                intent.putExtra("number", Integer.toString(no));

                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String res = "";
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    res = (String) bundle.get("response_message");
                }

                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
            }
        }
    }
}