package com.sahilapps.messenger;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {

    private TextView userIdTextView;
    private TextInputEditText mMessageEditText;
    private Button mSendNotificationButton;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        Intent intent = getIntent();
        final String userId = intent.getStringExtra(Intent.EXTRA_TEXT);

        final String mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userIdTextView = findViewById(R.id.user_id);
        mMessageEditText = findViewById(R.id.notification_message_edittext);
        mSendNotificationButton = findViewById(R.id.notification_send_button);

        firebaseFirestore = FirebaseFirestore.getInstance();


        userIdTextView.setText(intent.getStringExtra("user_name"));

        mSendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notificationMessage = mMessageEditText.getText().toString();
                if (!TextUtils.isEmpty(notificationMessage)){
                    Map<String, Object> notificationMap = new HashMap<>();
                    notificationMap.put("from", mCurrentUserId);
                    notificationMap.put("message", notificationMessage);
                    firebaseFirestore.collection("users")
                            .document(userId)
                            .collection("notifications")
                            .add(notificationMap)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendActivity.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                            mMessageEditText.clearComposingText();
                        }
                    });
                }
            }
        });
    }
}
