package com.zhenquan.mycustomview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by dsq on 2017/10/9.
 */

public class DisplayUtils {

  public static int getScreenHeight(Context context) {
    Point point = getScreenSize(context);
    return point == null ? 0 : point.y;
  }

  public static int getScreenWidth(Context context) {
    Point point = getScreenSize(context);
    return point == null ? 0 : point.x;
  }

  public static Point getScreenSize(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    if (wm != null) {
      Point p = new Point();
      wm.getDefaultDisplay().getSize(p);
      return p;
    }
    return null;
  }

  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static float dp2px(float dp) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
  }

  public static int spToPx(Context context, float sp) {
    return (int) TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
  }

  public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static int getActionBarHeight(Context context) {
    int result = 0;
    TypedValue tv = new TypedValue();
    context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
    result = context.getResources().getDimensionPixelSize(tv.resourceId);
    return result;
  }

  public static int calculateInSampleSize(BitmapFactory.Options options,
      int reqWidth, int reqHeight) {
    // 源图片的高度和宽度
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
      // 计算出实际宽高和目标宽高的比率
      final int heightRatio = Math.round((float) height / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);
      // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
      // 一定都会大于等于目标的宽和高。
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }
    return inSampleSize;
  }


  public static void backgroundAlpha(Window window, float bgAlpha) {
    WindowManager.LayoutParams lp = window.getAttributes();
    lp.alpha = bgAlpha; //0.0-1.0
    window.setAttributes(lp);
  }
}
