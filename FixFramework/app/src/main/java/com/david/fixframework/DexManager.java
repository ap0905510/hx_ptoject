package com.david.fixframework;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 * Created by david on 2017/8/25.
 */

public class DexManager {
    private Context context;
    private static final DexManager ourInstance = new DexManager();

    public static DexManager getInstance() {
        return ourInstance;
    }

    private DexManager() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void loadFile(File file) {
        if (file.exists()) {
            Log.e("YW", "file exists ... ");
        }
        try {
            DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(),
                    new File(context.getCacheDir(), "opt").getAbsolutePath(), Context.MODE_PRIVATE);
            //下一步  得到class   ----取出修复好的Method
            Enumeration<String> entry= dexFile.entries();
            while (entry.hasMoreElements()) {
//                拿到全类名
                String className=entry.nextElement();
//                    Class.forName(className);//
                Class clazz=dexFile.loadClass(className, context.getClassLoader());
                if (clazz != null) {
                    fixClazz(clazz);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixClazz(Class realClazz) {
//        服务器修复好的  realClazz
        Method[] methods=realClazz.getDeclaredMethods();
        for (Method rightMethod : methods) {
            Replace replace = rightMethod.getAnnotation(Replace.class);
            if (replace == null) {
                continue;
            }
            //找到了修复好的Method    找到出bug的Method
            String wrongClazz=replace.clazz();
            String  wrongMethodName=replace.method();
            try {
                Class clazz=Class.forName(wrongClazz);
                Method wrongMethod = clazz.getDeclaredMethod(wrongMethodName, rightMethod.getParameterTypes());
                if (Build.VERSION.SDK_INT <= 22) {
                    replace(Build.VERSION.SDK_INT ,wrongMethod, rightMethod);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }


        }
    }

    public native void replace(int  sdk,Method wrongMethod, Method rightMethod);
}
