package com.sahilapps.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SendActivity extends AppCompatActivity {

    private TextView userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        Intent intent = getIntent();

        userId = findViewById(R.id.user_id);
        userId.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
    }
}
