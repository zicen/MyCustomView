
package com.zhenquan.mycustomview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DisplayUtils {

    static public int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

    static public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    static public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


}
