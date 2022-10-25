package rmit.ad.week4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudent extends AppCompatActivity {
    private String message;
    private int status;
    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        Button button = findViewById(R.id.btnAddStudent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddStudent(view);
                Intent intent = new Intent(AddStudent.this, MainActivity.class);
                intent.putExtra("response", "New student added");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onAddStudent(View view) {
        nameText = (EditText) findViewById(R.id.name);
        new PostStudent().execute();
    }

    private class PostStudent extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            message = HttpHandler.postJson(nameText.getText().toString(), "https://my-json-server.typicode.com/cristalngo/demo/students");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AddStudent.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}