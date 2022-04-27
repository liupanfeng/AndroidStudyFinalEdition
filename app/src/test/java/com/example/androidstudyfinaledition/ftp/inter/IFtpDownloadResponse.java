package com.example.androidstudyfinaledition.ftp.inter;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 下午6:50
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public interface IFtpDownloadResponse {

    void started();

    /**
     * 下载进度
     * @param i
     */
    void transferred(int i);

    /**
     * 下载完成
     */
    void completed();

    void onError(String errorMsg);

}
