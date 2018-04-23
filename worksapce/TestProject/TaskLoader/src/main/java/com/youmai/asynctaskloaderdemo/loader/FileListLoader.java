package com.youmai.asynctaskloaderdemo.loader;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.youmai.asynctaskloaderdemo.PickerManager;
import com.youmai.asynctaskloaderdemo.bean.Document;
import com.youmai.asynctaskloaderdemo.bean.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

/**
 * Created by Branch on 16/7/31.
 */
public class FileListLoader extends AsyncTaskLoader<List<Document>> {

    final String[] DOC_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Files.FileColumns.TITLE
    };

    private static ArrayList<Document> documents = new ArrayList<>();

    private final String[] mFileTypes = new String[]{"txt", "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "apk", "mp4", "mp3", "zip", "rar"};
    List<Document> mApps;
    PackageManager mPm;
    private Context mContext;

    public FileListLoader(Context context) {
        super(context);

        // Retrieve the package manager for later use; note we don't
        // use 'context' directly but instead the save global application
        // context returned by getContext().
        mPm = getContext().getPackageManager();
        mContext = context;
    }

    /**
     * This is where the bulk of our work is done.  This function is called in a background thread and
     * should generate a new set of data to be published by the loader.
     */
    @Override
    public List<Document> loadInBackground() {

        final String[] projection = DOC_PROJECTION;

        String selection = getSelection(mFileTypes);
        String[] selectionArgs = getSelectionArgs(mFileTypes);

        //查询所有文件 效率不够
        /*String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;*/

        final Cursor cursor = mContext.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                null);

        if (cursor != null) {
            documents = getDocumentFromCursor(cursor);
            cursor.close();
        }

        return documents;
    }

    /**
     * Called when there is new data to deliver to the client.  The super class will take care of
     * delivering it; the implementation here just adds a little more logic.
     */
    @Override
    public void deliverResult(List<Document> apps) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (apps != null) {
                onReleaseResources(apps);
            }
        }
        List<Document> oldApps = mApps;
        mApps = apps;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(apps);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldApps != null) {
            onReleaseResources(oldApps);
        }

    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (mApps != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mApps);
        }

        if (takeContentChanged() || mApps == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<Document> apps) {
        super.onCanceled(apps);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mApps != null) {
            onReleaseResources(mApps);
            mApps = null;
        }

    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data
     * set.
     */
    protected void onReleaseResources(List<Document> apps) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }

    private String getSelection(String[] types) {
        String res = "";
        String item = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        for (int i = 0; i < types.length; i++) {
            if (i == (types.length - 1)) {
                res += item;
            } else {
                res += (item + " or ");
            }

        }
        return res;
    }


    private String[] getSelectionArgs(String[] types) {

        ArrayList<String> list = new ArrayList<>();
        for (String item : types) {
            list.add(MimeTypeMap.getSingleton().getMimeTypeFromExtension(item));
        }

        String[] stockArr = new String[list.size()];
        stockArr = list.toArray(stockArr);

        return stockArr;
    }


    private ArrayList<Document> getDocumentFromCursor(Cursor data) {
        ArrayList<Document> documents = new ArrayList<>();
        while (data.moveToNext()) {

            int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            String title = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
            long date = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED));
            //long date = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));

            System.out.println();
//            Log.e("YW", "\npath: " + data.getString(data.getColumnIndexOrThrow(DATA)));
//            Log.e("YW", "title: " + data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE)));
//            Log.e("YW", "mime_type: " + data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)));
//            Log.e("YW", "size: " + data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)) + "\n");
            System.out.println();

            if (path != null) {
                FileType fileType = getFileType(PickerManager.getInstance().getFileTypes(), path);
                if (fileType != null && !(new File(path).isDirectory())) {

                    Document document = new Document(imageId, title, path);
                    document.setFileType(fileType);
                    document.setModifyDate(date);

                    String mimeType = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE));
                    if (mimeType != null && !TextUtils.isEmpty(mimeType))
                        document.setMimeType(mimeType);
                    else {
                        document.setMimeType("");
                    }

                    document.setSize(data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)));

                    if (mimeType.contains("text/plain")) {
                        PickerManager.getInstance().getTxtList().add(document);
                    } else if (mimeType.contains("video/mp4")) {
                        PickerManager.getInstance().getVideoList().add(document);
                    } else if (mimeType.contains("audio/mpeg") || mimeType.contains("audio/x-mpeg")) {
                        PickerManager.getInstance().getMusicList().add(document);
                    } else if (mimeType.contains("application/vnd.android.package-archive")) {
                        PickerManager.getInstance().getAppList().add(document);
                    } else if (mimeType.contains("application/zip") || mimeType.contains("application/x-zip-compressed")) {
                        PickerManager.getInstance().getZipList().add(document);
                    }

                    if (!documents.contains(document)) {
                        Log.e("YW", "document: " + document.toString());
                        documents.add(document);
                    }
                }
            }
        }

        return documents;
    }

    private FileType getFileType(ArrayList<FileType> types, String path) {
        for (int index = 0; index < types.size(); index++) {
            for (String string : types.get(index).extensions) {
                if (path.endsWith(string))
                    return types.get(index);
            }
        }
        return null;
    }

    private final String[] MIME_TXT = {
            "text/plain", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "text/html",
            "application/pdf", "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation"};

    private final String[] MIME_VIDEO = {"video/3gpp", "video/mp4"};

    private final String[][] MIME_MapTable = { //{后缀名，MIME类型}

            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

}
