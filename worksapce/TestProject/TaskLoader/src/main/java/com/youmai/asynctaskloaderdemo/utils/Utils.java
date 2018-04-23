package com.youmai.asynctaskloaderdemo.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.android.internal.util.Predicate;
import com.youmai.asynctaskloaderdemo.PickerManager;
import com.youmai.asynctaskloaderdemo.R;
import com.youmai.asynctaskloaderdemo.bean.Document;
import com.youmai.asynctaskloaderdemo.bean.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by droidNinja on 29/07/16.
 */
public class Utils {

    public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean contains(String[] types, String path) {
        for (String string : types) {
            if (path.toLowerCase().endsWith(string)) return true;
        }
        return false;
    }

    private static final int WIDTH_INDEX = 0;
    private static final int HEIGHT_INDEX = 1;

    public static int[] getScreenSize(Context context) {
        int[] widthHeight = new int[2];
        widthHeight[WIDTH_INDEX] = 0;
        widthHeight[HEIGHT_INDEX] = 0;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        widthHeight[WIDTH_INDEX] = size.x;
        widthHeight[HEIGHT_INDEX] = size.y;

        if (!isScreenSizeRetrieved(widthHeight)) {
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            widthHeight[0] = metrics.widthPixels;
            widthHeight[1] = metrics.heightPixels;
        }

        // Last defense. Use deprecated API that was introduced in lower than API 13
        if (!isScreenSizeRetrieved(widthHeight)) {
            widthHeight[0] = display.getWidth(); // deprecated
            widthHeight[1] = display.getHeight(); // deprecated
        }

        return widthHeight;
    }

    private static boolean isScreenSizeRetrieved(int[] widthHeight) {
        return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0;
    }

    public static ArrayList<Document> refreshFileList(File dir) {

        ArrayList<Document> weChats = new ArrayList<>();
        File[] files = dir.listFiles();//文件夹下的所有文件或文件夹

        if (files == null) {
            return null;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                System.out.println("---" + files[i].getAbsolutePath());
                refreshFileList(files[i]);//递归文件夹！！！
            } else {

                /*System.out.println("文件大小:" + ShowLongFileSize(files[i].length()));// 计算文件大小
                // B,KB,MB,
                System.out.println("文件名称：" + files[i].getName());
                System.out.println("文件是否存在：" + files[i].exists());
                System.out.println("文件的相对路径：" + files[i].getPath());
                System.out.println("文件的绝对路径：" + files[i].getAbsolutePath());
                System.out.println("文件可以读取：" + files[i].canRead());
                System.out.println("文件可以写入：" + files[i].canWrite());
                System.out.println("文件上级路径：" + files[i].getParent());
                System.out.println("文件最后的更改时间： " + files[i].lastModified());
                System.out.println("文件大小：" + files[i].length() + "B");*/

                FileType fileType = getFileType(PickerManager.getInstance().getFileTypes(), files[i].getAbsolutePath());
                if (fileType != null && !(new File(files[i].getAbsolutePath()).isDirectory())) {
                    Document document = new Document(R.drawable.hx_icon_folder_txt, files[i].getName(), files[i].getAbsolutePath());
                    document.setFileType(fileType);
                    document.setMimeType(""); //多媒体分类
                    document.setSize(files[i].length() + "");
                    document.setModifyDate(files[i].lastModified());
                    weChats.add(document);
                }
            }
        }
        return weChats;
    }

    private static FileType getFileType(ArrayList<FileType> types, String path) {
        for (int index = 0; index < types.size(); index++) {
            for (String string : types.get(index).extensions) {
                if (path.endsWith(string))
                    return types.get(index);
            }
        }
        return new FileType("QQ文件", new String[]{"apk"}, R.drawable.hx_icon_folder_default);
    }

}
