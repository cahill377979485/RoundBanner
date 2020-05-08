package com.wenlin.roundbanner;

import com.blankj.utilcode.util.ToastUtils;
import com.wenlin.roundbanner.base.BaseActivity;
import com.wenlin.roundbanner.diamond.DiamondCardView;

/**
 * @author 文琳
 * @time 2020/5/8 11:40
 * @desc
 */
public class DiamondActivity extends BaseActivity {

    private DiamondCardView dcv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_diamond;
    }

    @Override
    public void initView() {
        dcv = findViewById(R.id.dcv);
        findViewById(R.id.tv_start).setOnClickListener(v -> {
            ToastUtils.showShort("start");
            dcv.startOpenAnim();
        });
        findViewById(R.id.tv_stop).setOnClickListener(v -> {
            ToastUtils.showShort("stop");
            dcv.stopAnim();
        });
    }
}
