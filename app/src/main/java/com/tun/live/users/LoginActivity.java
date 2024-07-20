package com.tun.live.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tun.live.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
TextView btn = findViewById(R.id.registerbtn);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
      /*  btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when text is clicked
                Toast.makeText(LoginActivity.this, "Text clicked", Toast.LENGTH_SHORT).show();
            }
        });

       */

    }

}