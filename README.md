# RoundBanner  
Dribble上很火的那种圆角Banner  
大部分代码来自com.xuezj.cardbanner:cardbanner:1.0.0  
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
具体用法，详看Demo的[CardBannerActivity](https://github.com/cahill377979485/RoundBanner/blob/master/app/src/main/java/com/wenlin/roundbanner/CardBannerActivity.java)  
