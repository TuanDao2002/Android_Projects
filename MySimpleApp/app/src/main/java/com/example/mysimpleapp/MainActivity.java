package com.example.mysimpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btnHello);

        final TextView textView = findViewById(R.id.tvNumber);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Hello Android", Toast.LENGTH_SHORT).show();
                int num = Integer.parseInt(textView.getText().toString());
                num++;

                textView.setText(num + "");
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setText("guess age");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ageText = (EditText) findViewById(R.id.editTextAge);
                int age = Integer.parseInt(ageText.getText().toString());
                if (age < 18) {
                    Toast.makeText(MainActivity.this, "Not old enough to get married", Toast.LENGTH_SHORT).show();
                } else {
                    int rand = (int)(Math.random() * 50);
                    if (rand % 5 == 3) {
                        Toast.makeText(MainActivity.this, "Can get married now: " + rand, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot marry yet: " + rand, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(MainActivity.this, "This is onStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "This is onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(MainActivity.this, "This is onStop", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this, "This is onPause", Toast.LENGTH_SHORT).show();
    }
}