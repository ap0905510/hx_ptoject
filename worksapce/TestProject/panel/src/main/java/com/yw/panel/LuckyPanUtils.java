package com.yw.panel;

/**
 * 作者：create by YW
 * 日期：2017.07.31 17:13
 * 描述：
 */
public class LuckyPanUtils {

    /**
     * 抽奖的文字
     */
    private static String[] mStrs = new String[]{
            "单反相机", "IPAD", "恭喜发财",
            "IPHONE", "妹子一只", "谢谢惠顾",
            "1", "2"};
    /**
     * 每个盘块的颜色
     */
    private int[] mColors = new int[]{0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01};
    /**
     * 与文字对应的图片
     */
    private int[] mImgs = new int[]{R.mipmap.danfan, R.mipmap.ipad, R.mipmap.f040,
            R.mipmap.iphone, R.mipmap.meizi, R.mipmap.f040,
            R.mipmap.ic_launcher_round, R.mipmap.meizi};

    private void init() {

    }
}
