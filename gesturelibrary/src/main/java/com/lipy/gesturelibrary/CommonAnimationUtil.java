package com.lipy.gesturelibrary;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 震动晃动的动画
 * Created by Lipy on 15/11/3.
 */
public class CommonAnimationUtil {
    private Vibrator vibrator;
    private Context mContext;

    public CommonAnimationUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 晃动动画
     */
    public static Animation shakeAnimation() {
        Animation translateAnimation = new TranslateAnimation(0, 0, 0, 6);
        translateAnimation.setInterpolator(new CycleInterpolator(4));
        translateAnimation.setDuration(20000);
        return translateAnimation;
    }

    /**
     * 开启震动
     */
    public void startVibrator() {
        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    /**
     * 关闭振动器
     */
    public void stopVibrator() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    /**
     * 设置晃动动画 震动
     */
    public void setShakeAnimation(View view) {
        setShakeAnimation(view,5);
    }

    /**
     * 设置晃动动画 震动
     */
    public void setShakeAnimation(View view, int count) {
        cleanShakeAnimation(view);
        view.setAnimation(shakeAnimation(count));
        startVibrator();
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     */
    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        //设置一个循环加速器，使用传入的次数就会出现摆动的效果。
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(200);
        return translateAnimation;
    }

    /**
     * 清除 动画 震动
     */
    public void cleanShakeAnimation(View view) {
        view.clearAnimation();
        stopVibrator();
    }

}
