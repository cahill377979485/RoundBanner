# RoundBanner  
Dribble上很火的那种圆角Banner  
大部分代码来自 [com.xuezj.cardbanner:cardbanner:1.0.0](https://github.com/xuezj/CardBannerDemo)
针对部分代码加了一些优化  
效果如下：  
![avatar](http://m.qpic.cn/psb?/V12E9lnJ0HXeuU/aU3qb61h0v9WGbN6YaJoF8.yoI5CnRyZBfuKC4DUhlQ!/b/dL4AAAAAAAAA&bo=OARJAgAAAAARB0c!&rf=viewer_4)  

使用方式：  
在项目的build.gradle里添加如下代码：  
allprojects {  
&emsp;repositories {  
&emsp;&emsp;...  
&emsp;&emsp;maven { url 'https://jitpack.io' }  
&emsp;}  
}  
然后添加依赖：  
dependencies {  
&emsp;implementation 'com.github.cahill377979485:RoundBanner:1.0'  
}  
使用方法：

```
        //获取数据，这里设置模拟数据
        List<String> listPictures = new ArrayList<>();
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
        
```
具体用法，详看Demo的[CardBannerActivity](https://github.com/cahill377979485/RoundBanner/blob/master/app/src/main/java/com/wenlin/roundbanner/CardBannerActivity.java)  
