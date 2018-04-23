package com.gavin.dynamicbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 根据用户指定参数,改变Button形状
 * <pre>
 *     可以指定的参数有:
 *     1. 视图高度,宽度,颜色 {@link #mHeight,#mWidth,#mColor}
 *     2. 视图边缘颜色和宽度 {@link #mStrokeColor,#mStrokeWidth}
 *     3. 变换的有无动画以及动画时常 {@link com.gavin.dynamicbutton.DynamicButton.PropertyParam#duration}
 *     4. 圆角的弧度 {@link #mCornerRadius}
 *
 * </pre>
 * Created by tom on 15/10/31.
 */
public class DynamicButton extends Button {
    public static final String TAG = "DynamicButton";

    //默认值
    public static final int DEFUALT_DYB_COLOR = 0xff0099cc;
    public static final int DEFAULT_DYB_PRESSED_COLOR = 0xff00719b;
    public static final int DEFUALT_DYB_CORNER_RADIUS = 2;
    public static final int DEFAULT_DYB_STROKE_WIDTH = 0;
    public static final int DEFAULT_DYB_STROKE_COLOR = 0x000000;

    /**
     * 控件的高度
     */
    protected int mHeight;
    /**
     * 控件的宽度
     */
    protected int mWidth;
    /**
     * 控件的背景色
     */
    protected int mColor;
    /**
     * 控件按住时的背景
     */
    protected int mPressedColor;
    /**
     * 圆角半径
     */
    protected float mCornerRadius;
    /**
     * 边缘宽度
     */
    protected int mStrokeWidth;
    /**
     * 边缘颜色
     */
    protected int mStrokeColor;
    /**
     * 是否正在执行动画
     */
    protected boolean bAnimatoring = false;
    /**
     * 正常时的背景
     */
    private CustomGradientDrawable mNormalDrawable;
    /**
     * 按下时的背景
     */
    private CustomGradientDrawable mPressedDrawable;
    /**
     * 原型模式 创建对象
     */
    PropertyParam mPropertyParam;


    public DynamicButton(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public DynamicButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public DynamicButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        mWidth = getWidth();
        Log.d(TAG, "onSizeChange:mHeight:" + mHeight + " mWidth:" + mWidth + " w:" + w + " h:" + h);
    }

    public void initView(Context mContext, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.DynamicButton, defStyleAttr, 0);
        mColor = array.getColor(R.styleable.DynamicButton_dybtn_color, DEFUALT_DYB_COLOR);
        mPressedColor = array.getColor(R.styleable.DynamicButton_dybtn_pressed_color, DEFAULT_DYB_PRESSED_COLOR);
        mCornerRadius = array.getDimensionPixelOffset(R.styleable.DynamicButton_dybtn_corner_radius, dp2px(DEFUALT_DYB_CORNER_RADIUS));
        mStrokeColor = array.getColor(R.styleable.DynamicButton_dybtn_stroke_color, DEFAULT_DYB_STROKE_COLOR);
        mStrokeWidth = array.getDimensionPixelOffset(R.styleable.DynamicButton_dybtn_stroke_width, dp2px(DEFAULT_DYB_STROKE_WIDTH));

        mNormalDrawable = createDrawable(mColor, mCornerRadius, mStrokeWidth, mStrokeColor);
        mPressedDrawable = createDrawable(mPressedColor, mCornerRadius, mStrokeWidth, mStrokeColor);

        StateListDrawable mListDrawable = new StateListDrawable();
        mListDrawable.addState(new int[]{android.R.attr.state_pressed}, mPressedDrawable);
        mListDrawable.addState(new int[]{android.R.attr.state_focused}, mPressedDrawable);
        mListDrawable.addState(new int[]{android.R.attr.state_selected}, mPressedDrawable);
        mListDrawable.addState(new int[]{}, mNormalDrawable);
        setBackground(mListDrawable);
        array.recycle();
    }


    /**
     * 创建背景图片
     *
     * @param mColor
     * @param mCornerRadius
     * @param mStrokeWidth
     * @param mStrokeColor
     * @return
     */
    public CustomGradientDrawable createDrawable(int mColor, float mCornerRadius, int mStrokeWidth, int mStrokeColor) {
        CustomGradientDrawable drawable = new CustomGradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setOrientation(GradientDrawable.Orientation.BL_TR);
        drawable.setStrokeColor(mStrokeColor);
        drawable.setStrokeWidth(mStrokeWidth);
        drawable.setColor(mColor);
        drawable.setRadius(mCornerRadius);
        return drawable;
    }

    /**
     * 设置背景
     *
     * @param mDrawable
     */
    public void setBackGroundCompat(Drawable mDrawable) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(mDrawable);
        } else {
            setBackground(mDrawable);
        }
    }

    /**
     * 开始变换Button的形状
     *
     * @param params
     */
    public void startChange(@NonNull PropertyParam params) {
        mPropertyParam = cloneParams(params);
        if (!bAnimatoring) {
            //没有正在执行动画
            mPressedDrawable.setStrokeColor(mPropertyParam.mStrokeColor);
            mPressedDrawable.setStrokeWidth(mPropertyParam.mStrokeWidth);
            mPressedDrawable.setColor(mPropertyParam.mPressedColor);
            mPressedDrawable.setCornerRadius(mPropertyParam.mCornerRadius);

            if (mPropertyParam.duration > 0) {
                changeWithAnimation();
            } else {
                changeNoAnimation();
            }

            mColor = mPropertyParam.mColor;
            mStrokeColor = mPropertyParam.mStrokeColor;
            mStrokeWidth = mPropertyParam.mStrokeWidth;
            mCornerRadius = mPropertyParam.mCornerRadius;

        }
        if (null != params)
            params = null;
    }

    /**
     * 设置Button的最终属性,如Text Icon等等
     *
     * @param params
     */
    private void changIng(final PropertyParam params) {

        if (!TextUtils.isEmpty(params.text) && params.icon != null) {
            Log.d(TAG, "text is not null && icon is not null!");
            setCompoundDrawablesWithIntrinsicBounds(params.icon, null, null, null);
            setText(params.text);
            setPadding(dp2px(10), 0, dp2px(10), 0);


        } else if (!TextUtils.isEmpty(params.text)) {
            setText(params.text);
        } else if (params.icon != null) {


            int padding = (getWidth() / 2) - (params.icon.getIntrinsicWidth() / 2);
            Log.d(TAG, "setIcon padding:" + padding);
            setCompoundDrawablesWithIntrinsicBounds(params.icon, null, null, null);
            setPadding(padding, 0, 0, 0);
        }

        bAnimatoring = false;
    }


    /**
     * 带有动画的渐变
     *
     * @param {原型模式}
     */
    public void changeWithAnimation() {
        bAnimatoring = true;
        setText(null);
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        final AnimatorBuilder.AnimatorParams mAnimatorParams = AnimatorBuilder.AnimatorParams.build(this);
        mAnimatorParams.height(mHeight, mPropertyParam.mHeight)
                .width(mWidth, mPropertyParam.mWidth)
                .cornerRadius(mCornerRadius, mPropertyParam.mCornerRadius)
                .color(mColor, mPropertyParam.mColor)
                .duration(mPropertyParam.duration)
                .listener(new AnimatorBuilder.AnimatorListener() {
                    @Override
                    public void onAnimatorEnd() {
                        Log.d(TAG, "changeWithAnimation-->onAnimatorEnd!");
                        changIng(mPropertyParam);
                    }
                })
                .strokeColor(mStrokeColor, mPropertyParam.mStrokeColor)
                .strokeWidth(mStrokeWidth, mPropertyParam.mStrokeWidth);

        AnimatorBuilder.startAnimator(mAnimatorParams);

    }

    /**
     * 无动画的骤变
     *
     * @param {原型模式}
     */
    public void changeNoAnimation() {
        mNormalDrawable.setColor(mPropertyParam.mColor);
        mNormalDrawable.setCornerRadius(mPropertyParam.mCornerRadius);
        mNormalDrawable.setStrokeWidth(mPropertyParam.mStrokeWidth);
        mNormalDrawable.setStrokeColor(mPropertyParam.mStrokeColor);

        if (mPropertyParam.mHeight != 0 && mPropertyParam.mWidth != 0) {
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.width = mPropertyParam.mWidth;
            lp.height = mPropertyParam.mHeight;
            setLayoutParams(lp);
        }
        changIng(mPropertyParam);
    }

    /**
     * 获取正常状态下的背景图片
     *
     * @return
     */
    public CustomGradientDrawable getNormalDrawable() {
        return mNormalDrawable;
    }

    //原型模式
    public PropertyParam cloneParams(PropertyParam param) {
        return new PropertyParam().apply(param);
    }

    /**
     * 使用Build模式构建该按钮的属性 - 套原型模式
     */
    public static class PropertyParam {

        public int mHeight;
        public int mWidth;
        public int mColor;
        public int mPressedColor;
        public float mCornerRadius;
        public int mStrokeWidth;
        public int mStrokeColor;
        public long duration;
        public String text;
        public Drawable icon;


        public static PropertyParam build() {
            return new PropertyParam();
        }

        public PropertyParam setHeight(int mHeight) {
            this.mHeight = mHeight;
            return this;
        }

        public PropertyParam setWidth(int mWidth) {
            this.mWidth = mWidth;
            return this;
        }

        public PropertyParam setColor(int mColor) {
            this.mColor = mColor;
            return this;
        }

        public PropertyParam setCornerRadius(int mCornerRadius) {
            this.mCornerRadius = mCornerRadius;
            return this;
        }

        public PropertyParam setStrokeWidth(int mStrokeWidth) {
            this.mStrokeWidth = mStrokeWidth;
            return this;
        }

        public PropertyParam setStrokeColor(int mStrokeColor) {
            this.mStrokeColor = mStrokeColor;
            return this;
        }

        public PropertyParam setPressedColor(int mPressedColor) {
            this.mPressedColor = mPressedColor;
            return this;
        }

        public PropertyParam duration(long duration) {
            this.duration = duration;
            return this;
        }

        public PropertyParam text(String text) {
            this.text = text;
            return this;
        }

        public PropertyParam icon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        // 原型备份
        public PropertyParam apply(PropertyParam param) {

            this.mWidth = param.mWidth;
            this.mHeight = param.mHeight;
            this.mColor = param.mColor;
            this.mPressedColor = param.mPressedColor;
            this.mCornerRadius = param.mCornerRadius;
            this.mStrokeColor = param.mStrokeColor;
            this.mStrokeWidth = param.mStrokeWidth;
            this.text = param.text;
            this.icon = param.icon;
            this.duration = param.duration;
            return this;
        }
    }

    /**
     * dp转换px
     * @param dp
     * @return
     */
    public int dp2px(int dp) {
        return (int) (this.getResources().getDisplayMetrics().density * dp + 0.5);
    }
}
