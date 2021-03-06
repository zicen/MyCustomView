package com.zhenquan.mycustomview.activity;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.customview.ShadowHelper;
import com.zhenquan.mycustomview.utils.DisplayUtils;

public class ShadowHelperAvtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_helper_avtivity);
        View btn1 = findViewById(R.id.btn1);
        View btn2 = findViewById(R.id.btn2);

        ShadowHelper.getInstance()
                .setBgColor(R.color.colorPrimary)
                .setShapeRadius((int) DisplayUtils.dp2px(6))
                .setShadowColor(Color.parseColor("#03000000"))
                .setShadowRadius((int) DisplayUtils.dp2px(6))
                .setOffsetX((int) DisplayUtils.dp2px(4))
                .setOffsetY((int) DisplayUtils.dp2px(4))
                .into(btn1);
        ViewCompat.setElevation(btn2, DisplayUtils.dp2px(4));
    }

}
