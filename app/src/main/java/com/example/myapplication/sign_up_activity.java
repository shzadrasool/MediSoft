package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class sign_up_activity extends AppCompatActivity {

    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        signin = findViewById(R.id.link_signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
