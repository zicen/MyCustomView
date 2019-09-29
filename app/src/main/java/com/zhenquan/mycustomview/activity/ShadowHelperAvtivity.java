package com.zhenquan.mycustomview.activity;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.customview.ShadowHelper;
import com.zhenquan.mycustomview.utils.DisplayUtils;

public class ShadowHelperAvtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_helper_avtivity);
        View btn1 = findViewById(R.id.btn1);
        ShadowHelper.getInstance()
                .setBgColor(Color.parseColor("#FF3D00"))
                .setShapeRadius(DisplayUtils.dipToPx(this, 6))
                .setShadowColor(Color.parseColor("#991DE9B6"))
                .setShadowRadius(DisplayUtils.dipToPx(this, 6))
                .setOffsetX(DisplayUtils.dipToPx(this, 4))
                .setOffsetY(DisplayUtils.dipToPx(this, 4))
                .into(btn1);

    }

}
