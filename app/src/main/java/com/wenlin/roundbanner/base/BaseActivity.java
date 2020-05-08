package com.wenlin.roundbanner.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author 文琳
 * @time 2020/5/8 11:46
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract int getLayoutId();

    public abstract void initView();
}
