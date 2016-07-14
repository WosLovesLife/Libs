package com.wosloveslife.intentkit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by YesingBeijing on 2016/7/14.
 */
public class IntentKit {

    /** 检查系统中是否有能够启动该Intent的Activity 可用于防止页面崩溃 */
    public static void usableIntent(Context context, Intent intent) throws Exception {
        PackageManager pm = context.getPackageManager();
        if (pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) == null){
            throw new Exception("没有能够执行此Intent的Activity");
        }
    }
}
