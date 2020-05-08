package com.wenlin.roundbanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenlin.roundbanner.banner.BannerAdapter;
import com.wenlin.roundbanner.banner.BannerViewHolder;
import com.wenlin.roundbanner.banner.MyBannerViewHolder;
import com.wenlin.roundbanner.banner.MyCardBanner;

import java.util.ArrayList;
import java.util.List;

public class CardBannerActivity extends AppCompatActivity {

    private MyCardBanner myCardBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCardBanner = findViewById(R.id.banner);
        setData();
    }

    private void setData() {
        //获取数据，这里设置模拟数据
        List<String> listPictures = new ArrayList<>();
        //这里的ThumbsBean是模拟的很简单的数据实体类
        List<String> listLinks = new ArrayList<>();
        //编造模拟数据
        String picUrl = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1588818908&di=c1428dc330be1d2d82cf83be2c695222&src=http://hbimg.b0.upaiyun.com/3804079cb84b828dc620501323a72e29a50e54328922-JRfbnX_fw658";
        String link = "https://www.baidu.com";
        for (int i = 0; i < 4; i++) {
            listPictures.add(picUrl);
            listLinks.add(link);
        }
        //装填数据
        myCardBanner.setDataCount(listPictures.size())
                .setBannerAdapter(new BannerAdapter() {
                    @Override
                    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MyBannerViewHolder(LayoutInflater.from(CardBannerActivity.this).inflate(R.layout.common_item_banner, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(BannerViewHolder holder, int position) {
                        if (CardBannerActivity.this.isFinishing()) return;
                        MyBannerViewHolder viewHolder = (MyBannerViewHolder) holder;
                        String pic = listPictures.get(position);
                        Glide.with(CardBannerActivity.this)
                                .load(pic)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .bitmapTransform(new CropCircleTransformation(getContext()))
                                .into(viewHolder.riv);
                        viewHolder.riv.setOnLongClickListener(view -> {
                            Toast.makeText(CardBannerActivity.this, "查看大图" + pic, Toast.LENGTH_SHORT).show();
                            //todo 长按轮播图片跳转到查看大图页面，看具体业务需要不需要，不需要的话可以删掉。
                            return true;
                        });
                        viewHolder.riv.setOnClickListener((view -> {
                            String link = listLinks.get(position);
                            Toast.makeText(CardBannerActivity.this, "跳转网页" + link, Toast.LENGTH_SHORT).show();
                            //todo 点击轮播图片跳转到网页。
                        }));
                    }
                });
        //开始轮播
        myCardBanner.start();
    }
}
