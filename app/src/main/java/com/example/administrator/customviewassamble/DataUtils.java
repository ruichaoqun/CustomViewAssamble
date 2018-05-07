package com.example.administrator.customviewassamble;

import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 */

public class DataUtils {
    public static void setData(List<Description> descriptions){
        descriptions.add(new Description("InterestViewGroup","自定义兴趣ViewGroup"));
        descriptions.add(new Description("WaveView","仿腾讯录音OS"));
    }
}
