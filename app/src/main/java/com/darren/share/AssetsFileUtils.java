package com.darren.share;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

/**
 * Assets Files tools
 *
 * @author ZENG DONGYANG
 * @version 1.0
 * @date 2016年5月15日
 */
public class AssetsFileUtils {

    /**
     * 外部存储卡路径
     */
    public static String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 将Assets目录下的文件复制到ExternalStorage的目录下
     *
     * @param context      上下文（应用资源）
     * @param path         Assets目录下的文件夹路径;；null为Assets的根下的文件
     * @param externalPath ExternalStorage的目录下的目标路径； null为复制到externalStorage的根目录下的文件
     * @param fileName     文件（全名包含后缀）
     */
    public static boolean FileToExternalStorage(Context context, String path, String externalPath, String fileName) {
        boolean flag = true;
        //判断Assets下的目录文件夹是否为空
        if (path != null && path.trim().length() > 0) {
            //在对应Assets下的目录文件夹下找文件
            path = path + "/";
        } else {
            //在对应Assets的根目录下找文件
            path = "";
        }
        //判断externalStorage目标文件夹是否为空
        if (externalPath != null && externalPath.trim().length() > 0) {
            //存放到externalStorage的目标文件夹下
            externalPath = externalPath + "/";
        } else {
            //存放到externalStorage的根目录
            externalPath = "";
        }
        //判断文件夹是否存在
        File f = new File(externalStoragePath + "/" + externalPath);
        if (!f.exists()) {
            f.mkdir();
        }
        // 判断目标文件是否存在
        File file = new File(externalStoragePath + "/" + externalPath + fileName);

        if (!file.exists()) {
            FileOutputStream fos = null;
            InputStream in = null;
            try {
                //打开Assets目录下的文件
                in = context.getAssets().open(path + fileName);
                fos = new FileOutputStream(externalStoragePath + "/" + externalPath + fileName);
                byte[] buffer = new byte[1024 * 512];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
