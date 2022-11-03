package rmit.ad.week2a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Button btnQuit = findViewById(R.id.btnQuit);

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        String number = bundle.getString("number");

        TextView textView = findViewById(R.id.txtMessage);
        String message;
        if (Integer.parseInt(number) == Integer.MIN_VALUE) {
            message = "Your id is not a number";
        } else if (Integer.parseInt(number) < 0) {
            message = "Your id must be positive";
        } else {
            message = "Your id is: " + number;
        }
        textView.setText(message);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StudentActivity.this, MainActivity.class);
                intent2.putExtra("response_message", "Thanks");
                setResult(RESULT_OK, intent2);
                finish();
            }
        });
    }
}