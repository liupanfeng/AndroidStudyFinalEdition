package com.meishe.base.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * All rights reserved,Designed by www.meishesdk.com
 * 版权所有www.meishesdk.com
 *
 * @Author : yangtailin
 * @CreateDate :2020/12/2 17:41
 * @Description :Android 版本工具类 Android version utils.
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class AndroidVersionUtils {
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");

    /**
     * 判断是否大于Android Q版本
     * Judge whether it is above Android Q version
     *
     * @return true:yes; false:no
     */
    public static boolean isAboveAndroid_Q() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * Get path by id
     *
     * @param id the id
     * @return the path
     */
    public static String getPath(int id) {
        return getRealPathAndroid_Q(id);
    }

    /**
     * Get real path in Android_Q
     *
     * @param id the id
     * @return the path
     */
    private static String getRealPathAndroid_Q(int id) {
        return QUERY_URI.buildUpon().appendPath(id + "").build().toString();
    }

    /**
     * Get real path from Uri
     * <p>
     * 通过URI获取绝对路径
     *
     * @param context 上下文 the context
     * @param uri     the uri
     * @return 路径 the real path
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT < 19) {
            return getRealPathFromUri_BelowApi19(context, uri);
        }
        return getRealPathFromUri_AboveApi19(context, uri);
    }

    /**
     * Get real path from URI above Api19.
     * The absolute path of the image is obtained according to the URI
     * 适配api19以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文 the context
     * @param uri     the uri
     * @return 路径 the real path
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    contentUri = MediaStore.Files.getContentUri("external");
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get real path from URI Api19 and below Api19.
     * The absolute path of the image is obtained according to the URI
     * 适配api19及其以下以,根据uri获取图片的绝对路径
     *
     * @param context 上下文 the context
     * @param uri     the uri
     * @return 路径 the real path
     */
    private static String getRealPathFromUri_BelowApi19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.MediaColumns.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
