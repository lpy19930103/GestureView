package com.lipy.gestureview;

import com.lipy.gesturelibrary.CommonAnimationUtil;
import com.lipy.gesturelibrary.GestureContentView;
import com.lipy.gesturelibrary.GestureDrawline;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码设置界面
 * Created by lipy on 2016/10/25.
 */
public class GestureEditActivity extends Activity implements View.OnClickListener {
    private TextView gesture_tip_tv;
    private TextView gesture_reset_tv;
    private GestureContentView mGestureContentView;
    private String mPassword = "";
    private boolean mIsVerify = false;
    private CommonAnimationUtil mCommonAnimationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gesture_edit);
        initView();
    }

    private void initView() {
        FrameLayout mGestureContentView_fl = (FrameLayout) findViewById(R.id.gesture_container);
        gesture_tip_tv = (TextView) findViewById(R.id.gesture_edit_tip);
        gesture_reset_tv = (TextView) findViewById(R.id.reset_gesture);
        mCommonAnimationUtil = new CommonAnimationUtil(this);
        gesture_reset_tv.setClickable(false);
        gesture_reset_tv.setVisibility(View.GONE);
        gesture_reset_tv.setOnClickListener(this);
        mGestureContentView = new GestureContentView(this, new GestureDrawline.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                if (isInputPassValidate(inputCode)) {
                    mCommonAnimationUtil.cleanShakeAnimation(gesture_tip_tv);
                    mCommonAnimationUtil.setShakeAnimation(gesture_tip_tv, 2);
                    gesture_tip_tv.setText(getString(R.string.gesture_edit_tip_error));
                    changTipColor(true);
                    mGestureContentView.clearDrawlineState(501L);
                    return;
                }
                mIsVerify = true;
                mPassword = inputCode;
                mGestureContentView.clearDrawlineState(500L);
                mGestureContentView.setVerifyState(mIsVerify, mPassword);
                gesture_tip_tv.setText(getResources().getString(
                        R.string.gesture_edit_tip_again));
                changTipColor(false);
                gesture_reset_tv.setVisibility(View.VISIBLE);
                gesture_reset_tv.setClickable(true);
                gesture_reset_tv.setText(getString(R.string.gesture_edit_reset));
            }

            @Override
            public void checkedSuccess() {
                /**
                 * 代表用户绘制的密码与传入的密码相同
                 */
                List<String> gesturePassword = new ArrayList<>();
                for (int i = 0; i < mPassword.length(); i++) {
                    gesturePassword.add(String.valueOf(mPassword.charAt(i)));
                }
                mGestureContentView.clearDrawlineState(500L);
                PassWord.PASSWORD = mPassword;
                Toast.makeText(GestureEditActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            /**
             * 代表用户绘制的密码与传入的密码不相同
             */
            @Override
            public void checkedFail() {
                gesture_tip_tv.setText(getString(R.string.gesture_edit_tip_twice_different));
                changTipColor(true);
                mCommonAnimationUtil.cleanShakeAnimation(gesture_tip_tv);
                mCommonAnimationUtil.setShakeAnimation(gesture_tip_tv);
                // 保持绘制的线，1.5秒后清除
                mGestureContentView.clearDrawlineState(501L);
            }

            @Override
            public void onTouchDown() {
                if (mIsVerify) {
                    gesture_tip_tv.setText(getResources().getString(
                            R.string.gesture_edit_tip_again));
                    changTipColor(false);
                } else {
                    gesture_tip_tv.setText(getResources().getString(R.string.gesture_edit_tip));
                    changTipColor(false);
                }
            }

            @Override
            public void onTouchUp(int count) {

            }
        });
        mGestureContentView.setParentView(mGestureContentView_fl);
    }


    private void backNotShowJump() {
        mIsVerify = false;
        mPassword = "";
        mGestureContentView.setVerifyState(mIsVerify, mPassword);
        gesture_reset_tv.setVisibility(View.GONE);
        gesture_tip_tv.setText(getResources().getString(R.string.gesture_edit_tip));
        changTipColor(false);
    }

    private boolean isInputPassValidate(String inputPassword) {
        return TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reset_gesture) {
            backNotShowJump();
        }
    }

    public void changTipColor(boolean changError) {
        if (changError) {
            gesture_tip_tv.setTextColor(getResources().getColor(R.color.common_gesture_error_tv));
        } else {
            gesture_tip_tv.setTextColor(
                    getResources().getColor(R.color.common_listitem_left_text_color));
        }
    }
}
