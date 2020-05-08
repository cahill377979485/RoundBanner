package com.wenlin.roundbanner.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * @author 文琳
 * @time 2020/5/8 11:39
 * @desc
 */
public class MyApplication extends Application {
    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Utils.init(this);
    }
}
