package com.cahill.roundbanner;

import android.view.View;

/**
 * @author 文琳
 * @create 2018/7/12 17:49
 * @email 377979485@qq.com
 * @description
 * @history
 */
public class MyBannerViewHolder extends BannerViewHolder {
    public RoundImageView riv;

    public MyBannerViewHolder(View itemView) {
        super(itemView);
        riv = itemView.findViewById(R.id.riv);
    }
}
