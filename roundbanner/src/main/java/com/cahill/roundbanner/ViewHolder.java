package com.cahill.roundbanner;

import android.view.View;
import android.widget.TextView;

/**
 * Created by xuezj on 2017/7/29.
 */

public class ViewHolder extends BannerViewHolder {
    public RoundedImageView roundedImageView;
    public TextView mainTitle,subtitleTitle;
    public ViewHolder(View itemView) {
        super(itemView);

        roundedImageView=(RoundedImageView)itemView.findViewById(R.id.item_img);
        mainTitle=(TextView)itemView.findViewById(R.id.main_text);
        subtitleTitle=(TextView)itemView.findViewById(R.id.subtitle_text);
    }
}
