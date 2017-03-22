package com.lipy.gestureview;


import com.lipy.gesturelibrary.CommonAnimationUtil;
import com.lipy.gesturelibrary.GestureContentView;
import com.lipy.gesturelibrary.GestureDrawline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 手势密码登录页
 * Created by lipy on 2016/10/25.
 */
public class GestureLoginActivity extends Activity implements View.OnClickListener {

    private GestureContentView gestureContentView;

    private GestureDrawline.GestureCallBack gestureCallBack = new GestureDrawline.GestureCallBack() {

        @Override
        public void onGestureCodeInput(String inputCode) {
            gestureContentView.clearCanvas();
            login(inputCode);
        }

        @Override
        public void checkedSuccess() {

        }

        @Override
        public void checkedFail() {

        }

        @Override
        public void onTouchDown() {

        }

        @Override
        public void onTouchUp(int count) {

        }
    };
    private CommonAnimationUtil mCommonAnimationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gesture_login);
        initView();
        mCommonAnimationUtil = new CommonAnimationUtil(this);
    }

    private void initView() {
        TextView phoneNumber = (TextView) findViewById(R.id.phone_number);
        FrameLayout container = (FrameLayout) findViewById(R.id.gesture_container);
        findViewById(R.id.num_login).setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);
        gestureContentView = new GestureContentView(this, gestureCallBack);
        gestureContentView.setParentView(container);
        phoneNumber.setText("18888888888");
    }

    private void login(String inputCode) {
        if (inputCode.equals(PassWord.PASSWORD)) {
            gestureContentView.clearDrawlineState(100L);
            Toast.makeText(this, "密码正确", Toast.LENGTH_SHORT).show();
        } else {
            gestureContentView.clearDrawlineState(501L);
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.num_login) {
            accountLogin();
        } else if (v.getId() == R.id.button_back) {
            finish();
        }
    }

    /**
     * 跳转到账号密码登录
     */
    private void accountLogin() {
        Toast.makeText(this, "账号密码登陆", Toast.LENGTH_SHORT).show();
    }

}
