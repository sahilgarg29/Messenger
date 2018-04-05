package com.sahilapps.messenger;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileTextView;
    private TextView mAllUsersTextView;
    private TextView mNotificationTextView;
    private ViewPager mMainActivityViewPager;
    private MainActivityPagerAdapter mMainActivityPagerAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        mProfileTextView = findViewById(R.id.profile_textview);
        mAllUsersTextView = findViewById(R.id.all_users_textview);
        mNotificationTextView = findViewById(R.id.notification_textview);
        mMainActivityViewPager = findViewById(R.id.main_activity_view_pager);

        mMainActivityPagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());

        mMainActivityViewPager.setAdapter(mMainActivityPagerAdapter);
        mMainActivityViewPager.setOffscreenPageLimit(2);

        mProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivityViewPager.setCurrentItem(0);
            }
        });

        mAllUsersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivityViewPager.setCurrentItem(1);
            }
        });

        mNotificationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivityViewPager.setCurrentItem(2);
            }
        });

        mMainActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    private void changeTabs(int position) {
        if (position == 0){
            mProfileTextView.setTextSize(22);
            mAllUsersTextView.setTextSize(18);
            mNotificationTextView.setTextSize(18);
        }else if (position == 1){
            mProfileTextView.setTextSize(18);
            mAllUsersTextView.setTextSize(22);
            mNotificationTextView.setTextSize(18);
        }else if (position == 2){
            mProfileTextView.setTextSize(18);
            mAllUsersTextView.setTextSize(18);
            mNotificationTextView.setTextSize(22);
        }
    }
}
