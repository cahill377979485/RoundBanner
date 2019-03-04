package com.wenlin.roundbanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wenlin.roundbanner.banner.MyBannerViewHolder;
import com.wenlin.roundbanner.banner.MyCardBanner;
import com.xuezj.cardbanner.adapter.BannerAdapter;
import com.xuezj.cardbanner.adapter.BannerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        String picUrl = "https://s0.xingxiaoban.com/upfile/activity/2572/5EvHuVmy.png";
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
                        MyBannerViewHolder holder = null;
                        try {
                            //这样更安全
                            holder = new MyBannerViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.common_item_banner, parent, false));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return holder;
                    }

                    @Override
                    public void onBindViewHolder(BannerViewHolder holder, int position) {
                        if (MainActivity.this.isFinishing()) return;
                        MyBannerViewHolder viewHolder = (MyBannerViewHolder) holder;
                        Glide.with(MainActivity.this)
                                .load(listPictures.get(position))
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .bitmapTransform(new CropCircleTransformation(getContext()))
                                .into(viewHolder.riv);
                        viewHolder.riv.setOnLongClickListener(view -> {
                            String pic = listPictures.get(position);
                            Toast.makeText(MainActivity.this, "查看大图" + pic, Toast.LENGTH_SHORT).show();
                            //todo 长按轮博图片跳转到查看大图页面，看具体业务需要不需要，不需要的话可以删掉。
                            return true;
                        });
                        viewHolder.riv.setOnClickListener((view -> {
                            String link = listLinks.get(position);
                            Toast.makeText(MainActivity.this, "跳转网页" + link, Toast.LENGTH_SHORT).show();
                            //todo 点击轮博图片跳转到网页。
                        }));
                    }
                });
        //开始轮博
        myCardBanner.start();
    }
}
