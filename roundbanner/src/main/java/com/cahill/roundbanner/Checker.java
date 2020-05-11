package com.cahill.roundbanner;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wenlin on 2017/8/17.
 */
public class Checker {
    public static boolean notNull(Object object) {
        return object != null;
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean hasList(List list) {
        return list != null && !list.isEmpty();
    }

    public static boolean noList(List list){
        return !hasList(list);
    }

   public static boolean hasMap(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean hasWord(String str) {
        return str != null && !TextUtils.isEmpty(str);
    }

    public static boolean noWord(String str) {
        return !hasWord(str);
    }

    public static String formatWord(String str){
        return hasWord(str)?str:"";
    }

    public static boolean isAvatar(String headUrl){
        return hasWord(headUrl) && headUrl.startsWith("http") && headUrl.length() > 10;
    }

    public static boolean notAvatar(String headUrl){
        return !isAvatar(headUrl);
    }
}
