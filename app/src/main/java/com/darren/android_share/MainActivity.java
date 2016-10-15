package com.darren.android_share;

import android.Manifest;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.darren.permissionlibrary.utils.PermissionInfo;
import com.darren.permissionlibrary.utils.PermissionUtil;
import com.darren.permissionlibrary.utils.callback.PermissionResultAdapter;
import com.darren.share.AssetsFileUtils;
import com.darren.share.ToolShare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * share test
 *
 * @author ZENG DONGYANG
 * @version 1.0
 * @date 2016年5月15日
 */
public class MainActivity extends Activity {

    private Map<String, List<PermissionInfo>> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtil.getInstance().request(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        },
                new PermissionResultAdapter() {

                    @Override
                    public void onPermissionGranted(String... permissions) {
                        for (String permission : permissions)
                            Toast.makeText(MainActivity.this, permission + " is granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(String... permissions) {
                        for (String permission : permissions)
                            Toast.makeText(MainActivity.this, permission + " is denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRationalShow(String... permissions) {
                        for (String permission : permissions)
                            Toast.makeText(MainActivity.this, permission + " is rational", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void init() {
        boolean flag = false;
        map = PermissionUtil.getInstance().checkMultiPermissions(
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE});
        List<PermissionInfo> acceptList = map.get(PermissionUtil.ACCEPT);
        if (acceptList.size() == 2) {
            if (AssetsFileUtils.FileToExternalStorage(MainActivity.this, null, "share", "myweixin.jpg"))
                if (AssetsFileUtils.FileToExternalStorage(MainActivity.this, null, "share", "myqqo.jpg"))
                    if (AssetsFileUtils.FileToExternalStorage(MainActivity.this, null, "share", "myqqt.jpg"))
                        Toast.makeText(MainActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "复制失败", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "复制失败", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "复制失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "请允许App访问SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    //分享文字
    public void shareText(View view) {
        ToolShare.getInstance().shareText(this, "分享到", "This is my Share text.");
    }

    //分享单张图片
    public void shareSingleImage(View view) {
        init();
        String imagePath;
        switch (view.getId()) {
            case R.id.weixin:
                imagePath = Environment.getExternalStorageDirectory() + File.separator + "share/myweixin.jpg";
                break;
            case R.id.qqs:
                imagePath = Environment.getExternalStorageDirectory() + File.separator + "share/myqqo.jpg";
                break;
            case R.id.qqw:
                imagePath = Environment.getExternalStorageDirectory() + File.separator + "share/myqqt.jpg";
                break;
            default:
                return;
        }
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);
        ToolShare.getInstance().shareSingleImage(this, "分享到", imagePath);
    }


    //分享多张图片
    public void shareMultipleImage(View view) {
        init();
        ArrayList<Uri> uriList = new ArrayList<Uri>();
        //文件系统中的
        String path = Environment.getExternalStorageDirectory() + File.separator + "share/";
        uriList.add(Uri.fromFile(new File(path + "myqqo.jpg")));
        uriList.add(Uri.fromFile(new File(path + "myqqt.jpg")));
        uriList.add(Uri.fromFile(new File(path + "myweixin.jpg")));

        ToolShare.getInstance().shareMultipleImage(this, "分享到", uriList);

    }


}
