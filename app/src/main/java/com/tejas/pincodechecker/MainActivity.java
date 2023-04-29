package com.tejas.pincodechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button check_btn;
    EditText pin_code_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_btn = findViewById(R.id.check_btn);
        pin_code_input = findViewById(R.id.pin_code_input);


        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin_code = pin_code_input.getText().toString();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("pin_code", pin_code);
                startActivity(intent);
            }
        });

    }
}