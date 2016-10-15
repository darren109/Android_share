package com.darren.share;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.darren.android_share.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Assets Files tools
 *
 * @author ZENG DONGYANG
 * @version 1.0
 * @date 2016年5月15日
 */
public class ToolShare {

    private static ToolShare instance;

    public static ToolShare getInstance() {
        if (instance == null) {
            instance = new ToolShare();
        }
        return instance;
    }

    private ToolShare() {
    }


    /**
     * 分享文字
     *
     * @param activity      上下文
     * @param activityTitle Activity的名字
     * @param str           文本
     */
    public void shareText(Activity activity, String activityTitle, String str) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, str);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        activity.startActivity(Intent.createChooser(shareIntent, activityTitle));
    }

    /**
     * 分享图片+文本功能
     *
     * @param activity      上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public void shareMsg(Activity activity, String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本  
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/*");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activityTitle));
    }


    /**
     * 分享单张图片
     *
     * @param activity      上下文
     * @param activityTitle Activity的名字
     * @param imagePath     图片路径
     */
    public void shareSingleImage(Activity activity, String activityTitle, String imagePath) {
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, activityTitle));
    }


    /**
     * 分享多张图片
     *
     * @param activity      上下文
     * @param activityTitle Activity的名字
     * @param uriList       分享集合
     */
    public void shareMultipleImage(Activity activity, String activityTitle, ArrayList<Uri> uriList) {
        //文件系统中的
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, activityTitle));
    }
    
    //指定分享的应用（type）
    private void initShareIntent(Activity activity,String type, String activityTitle, String imagePath) {
        boolean found = false;
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/*");
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type) ) {
                    share.putExtra(Intent.EXTRA_SUBJECT,  "subject");
                    share.putExtra(Intent.EXTRA_TEXT,     "your text");
                    share.putExtra(Intent.EXTRA_STREAM, imageUri ); // Optional, just if you wanna share an image.
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return;
            activity.startActivity(Intent.createChooser(share, "Select"));
        }
    }


}
