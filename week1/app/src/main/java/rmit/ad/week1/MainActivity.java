package rmit.ad.week1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonC, buttonDel,
            buttonAdd, buttonMinus, buttonMultiply, buttonDivide, buttonEqual;

    TextView input, output;
    boolean add, minus, multiply, divide;
    int firstNum = -1, secondNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonDel = (Button) findViewById(R.id.buttonDel);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonEqual = (Button) findViewById(R.id.buttonEqual);

        input = (TextView) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button0.getText().toString();
                input.setText(txt);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button1.getText().toString();
                input.setText(txt);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button2.getText().toString();
                input.setText(txt);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button3.getText().toString();
                input.setText(txt);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button4.getText().toString();
                input.setText(txt);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button5.getText().toString();
                input.setText(txt);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button6.getText().toString();
                input.setText(txt);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button7.getText().toString();
                input.setText(txt);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button8.getText().toString();
                input.setText(txt);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = input.getText().toString() + button9.getText().toString();
                input.setText(txt);
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add = false;
                minus = false;
                multiply = false;
                divide = false;
                firstNum = 0;
                secondNum = 0;
                input.setText("");
                output.setText("");
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputTxt = input.getText().toString();
                if (inputTxt.length() == 0) return;
                String txt = inputTxt.substring(0, inputTxt.length() - 1);
                input.setText(txt);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!add && !minus && !multiply && !divide) {
                    firstNum = Integer.parseInt(input.getText().toString());
                }
                add = true;
                minus = false;
                multiply = false;
                divide = false;
                input.setText("");
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!add && !minus && !multiply && !divide) {
                    firstNum = Integer.parseInt(input.getText().toString());
                }
                minus = true;
                add = false;
                multiply = false;
                divide = false;
                input.setText("");
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!add && !minus && !multiply && !divide) {
                    firstNum = Integer.parseInt(input.getText().toString());
                }
                multiply = true;
                add = false;
                minus = false;
                divide = false;
                input.setText("");
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!add && !minus && !multiply && !divide) {
                    firstNum = Integer.parseInt(input.getText().toString());
                }
                divide = true;
                add = false;
                minus = false;
                multiply = false;
                input.setText("");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondNum = Integer.parseInt(input.getText().toString());
                if (secondNum == -1) {
                    output.setText("Second number missing");
                    return;
                }

                output.setText("");
                int result = 0;
                if (add) {
                    result = firstNum + secondNum;
                } else if (minus) {
                    result = firstNum - secondNum;
                } else if (multiply) {
                    result = firstNum * secondNum;
                } else if (divide) {
                    if (secondNum == 0) {
                        output.setText("divide by 0");
                        return;
                    }
                    result = firstNum / secondNum;
                }
                
                output.setText(String.valueOf(result));
                firstNum = result;
            }
        });
    }
}