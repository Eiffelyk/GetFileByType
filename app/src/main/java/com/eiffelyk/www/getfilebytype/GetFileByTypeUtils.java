package com.eiffelyk.www.getfilebytype;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 馋猫 on 2015/2/11.
 * 从sd卡中读取指定的目录的所有图片
 * 确认添加读取sdcard的权限
 * 确认手机存在外部存储
 */
public class GetFileByTypeUtils {
    private final static String TAG = "馋猫";
    private ArrayList<String> list;

    public GetFileByTypeUtils() {
        this.list = new ArrayList<>();
    }

    public ArrayList<String> getList() {
        return list;
    }

    /**
     * 判断sdcard是否存在
     *
     * @return 不存在返回null，存在返回路径
     */
    private String isSdcard() {
        File sdcardDir;
        boolean isSDExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDExist) {
            //如果存在SDcard 就找到跟目录
            sdcardDir = Environment.getExternalStorageDirectory();
            return sdcardDir.toString();
        } else {
            return null;
        }
    }

    /**
     * 获取图片主要函数
     * 采用判断形式，如果是文件，直接分析文件格式 如果是文件夹进入此目录
     *
     * @param sdpath 指定的目录
     */
    public void getPicpath(String sdpath) {
        //打开SD卡目录
        if (isSdcard() != null) {
            File file = new File(isSdcard() + sdpath);
            //获取SD卡目录列表
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    isFile(f);
                } else {
                    notfile(f);
                }
            }
        }
    }

    /**
     * 截取文件后缀名判断是否符合过滤规则
     *
     * @param file 传入的文件.
     */
    public void isFile(File file) {
        String fnm = file.getPath();
        Log.i(TAG, "isFile==" + fnm);
        String filename = file.getName();
        int idx = filename.lastIndexOf(".");

        if (idx <= 0) {
            return;
        }
        String suffix = filename.substring(idx + 1, filename.length());
        if (suffix.toLowerCase().equals("jpg") || suffix.toLowerCase().equals("jpeg") || suffix.toLowerCase().equals("bmp") || suffix.toLowerCase().equals("png") || suffix.toLowerCase().equals(".gif")) {
            list.add(file.getPath());
        }
    }

    /**
     * 不是文件，继续进入循环往复上边步骤
     *
     * @param file 传入的文件或者路径
     */
    public void notfile(File file) {
        Log.i(TAG, "notfilepath" + file.getPath());
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File file1 : files) {
            Log.i(TAG, "notfile int=" + String.valueOf(files.length));
            if (file1.isFile()) {
                isFile(file1);
            } else {
                String SDpath = file1.getPath();
                File fileSD = new File(SDpath);
                Log.i(TAG, "notfile =" + fileSD);
                File[] filess = fileSD.listFiles();
                if (filess == null) {
                    return;
                }
                for (File ignored : filess) {
                    getPicpath(fileSD.toString());
                }
            }
        }
    }
}

