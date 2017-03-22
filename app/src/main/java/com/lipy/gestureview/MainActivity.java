package com.lipy.gestureview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.set).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(this, GestureLoginActivity.class));
                break;
            case R.id.set:
                startActivity(new Intent(this, GestureEditActivity.class));
                break;
        }
    }
}
