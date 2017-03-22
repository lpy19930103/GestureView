package com.lipy.gesturelibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码容器类,创建九宫格手机密码
 * Created by Lipy on 16/10/21.
 */
public class GestureContentView extends ViewGroup {

    private int baseNum = 6;

    private int[] screenDispaly;

    /**
     * 每个点区域的宽度
     */
    private int blockWidth;
    /**
     * 声明一个集合用来封装坐标集合
     */
    private List<GesturePoint> list;
    private Context context;
    private GestureDrawline gestureDrawline;

    public GestureContentView(Context context, GestureDrawline.GestureCallBack callBack) {
        this(context, false, "", callBack);
    }

    /**
     * 包含9个ImageView的容器，初始化
     *
     * @param isVerify 是否为校验手势密码
     * @param passWord 用户传入密码
     * @param callBack 手势绘制完毕的回调
     */
    public GestureContentView(Context context, boolean isVerify, String passWord, GestureDrawline.GestureCallBack callBack) {
        super(context);
        screenDispaly = DensityUtil.getScreenDispaly(context);
        blockWidth = (screenDispaly[0] - DensityUtil.dp2px(context, 114)) / 3;
        this.list = new ArrayList<>();
        this.context = context;
        // 添加9个图标
        addChild();
        // 初始化一个可以画线的view
        gestureDrawline = new GestureDrawline(context, list, isVerify, passWord, callBack);
    }

    /**
     * 设置是否校验手势密码的状态
     */
    public void setVerifyState(boolean isVerify, String mPassword) {
        gestureDrawline.setDrawLineVerifyState(isVerify, mPassword);
    }

    private void addChild() {
        for (int i = 0; i < 9; i++) {
            ImageView image = new ImageView(context);
            image.setBackgroundResource(R.drawable.gesture_node_normal);
            this.addView(image);
            invalidate();
            // 第几行
            int row = i / 3;
            // 第几列
            int col = i % 3;
            // 定义点的每个属性
            int leftX = col * blockWidth + blockWidth / baseNum;
            int topY = row * blockWidth + blockWidth / baseNum;
            int rightX = col * blockWidth + blockWidth - blockWidth / baseNum;
            int bottomY = row * blockWidth + blockWidth - blockWidth / baseNum;
            GesturePoint p = new GesturePoint(leftX, rightX, topY, bottomY, image, i + 1);
            this.list.add(p);
        }
    }

    public void setParentView(ViewGroup parent) {
        // 得到屏幕的宽度
        int width = screenDispaly[0] - DensityUtil.dp2px(context, 114);
        LayoutParams layoutParams = new LayoutParams(width, width);
        this.setLayoutParams(layoutParams);
        gestureDrawline.setLayoutParams(layoutParams);
        parent.addView(gestureDrawline);
        parent.addView(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            //第几行
            int row = i / 3;
            //第几列
            int col = i % 3;
            View v = getChildAt(i);
            v.layout(col * blockWidth + blockWidth / baseNum, row * blockWidth + blockWidth / baseNum,
                    col * blockWidth + blockWidth - blockWidth / baseNum, row * blockWidth + blockWidth - blockWidth / baseNum);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 遍历设置每个子view的大小
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 保留路径delayTime时间长
     */
    public void clearDrawlineState(long delayTime) {
        gestureDrawline.clearDrawlineState(delayTime);
    }

    /**
     * 清除九宫格画布
     */
    public void clearCanvas() {
        gestureDrawline.clearCanvas();
    }

}
