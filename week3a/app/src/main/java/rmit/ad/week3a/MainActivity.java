package rmit.ad.week3a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btnButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cbTomato = findViewById(R.id.ckbTomato);
                CheckBox cbCheese = findViewById(R.id.ckbCheese);
                CheckBox cbCream = findViewById(R.id.ckbCream);

                StringBuilder sb = new StringBuilder();
                if (cbTomato.isChecked()) {
                    sb.append("Tomato");
                }

                if (cbCheese.isChecked()) {
                    sb.append("Cheese");
                }

                if (cbCream.isChecked()) {
                    sb.append("Cream");
                }

                RadioGroup radioGroup = findViewById(R.id.rgAnswer);
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                String deliveryAnswer;
                if (radioButton != null) {
                    deliveryAnswer = radioButton.getText().toString();
                } else {
                    deliveryAnswer = "not given";
                }

                Toast.makeText(MainActivity.this, "You selected: " + sb + " and home delivery: " + deliveryAnswer, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;

        if (checkBox.isChecked()) {
            Toast.makeText(this, "You've selected " + checkBox.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}