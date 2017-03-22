package com.lipy.gesturelibrary;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by lipy on 2014/7/29.
 */
public class DensityUtil {
    // 界面参考缩放参数
    private static float SCALEDATE_W;
    private static float SCALEDATE_H;

    /**
     * 获取屏幕分辨率
     *
     * @param context 上下文
     * @return 屏幕宽高尺寸
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        return new int[]{width, height};
    }

    /**
     * px 转换成 dip
     *
     * @param px
     * @return dip
     */
    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * dip 转换成 px
     *
     * @param dip
     * @return px
     */
    public static int dp2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /****
     * dp To px
     *
     * @param context
     * @param dpValue
     * @return
     */
    @Deprecated
    public static int dip2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /***
     * sp To px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /***
     * px To sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    // 获取界面宽高
    public static void calculateScreenSize(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth_ = dm.widthPixels;
        int screenHeight_ = dm.heightPixels;

        setScaledParams(screenWidth_, screenHeight_);
    }

    /**
     * 由于ophone1.6以下的系统无法支持<supports-screens android:smallScreens="true" android:normalScreens="true"
     * android:largeScreens="true" android:anyDensity="false"> </supports-screens>属性设置，但是会出现大屏幕界面布局局限的情况
     * 该方法针对此种情况对界面数据进行相应的缩放，参照的比例是320X480
     *
     * @param screenWidth 参照宽度
     */
    private static void setScaledParams(float screenWidth, float screenHeight) {
        final float origWidth = 320;
        final float origHeight = 480;
        SCALEDATE_W = screenWidth / origWidth;
        SCALEDATE_H = screenHeight / origHeight;
    }

    /************************
     * 高度跟320的换算
     *
     * @param num
     * @return
     */
    public static int getScaledWValue(int num) {
        if (SCALEDATE_W != 0) {
            num = (int) (SCALEDATE_W * num);
        }
        return num;
    }

    /************************
     * 高度跟480的换算
     *
     * @param num
     * @return
     */
    public static int getScaledHValue(int num) {
        if (SCALEDATE_H != 0) {
            num = (int) (SCALEDATE_H * num);
        }
        return num;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    @Deprecated
    public static int px2dip(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 动态设置ListView的高度,解决ScrollView中嵌套ListView不能滚动的问题
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        int totalHeight = 0;
        if (listView == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * 动态设置ListView的高度,count大于6设置固定高度
     * 在Android4.4.4版本之前,手动计算View的高度,而View中含有RelativeLayout
     * 就会出现onMeasure时NullPointerException这种错误，
     * 解决办法：
     * 一是调用measure方法前判断一下版本，
     * 二是尽量不要使用RelativeLayout，使用LinearLayout或者FrameLayout等布局。
     * @param listView
     */
    public static void setListViewHeight(ListView listView, int childs) {
        int totalHeight = 0;
        if (listView == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        if (listAdapter.getCount() <= childs) {
            return;
        }
        for (int i = 0; i < childs; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeight(ListView listView) {
        setListViewHeight(listView, 6);
    }

}
                                                                                                                                                    