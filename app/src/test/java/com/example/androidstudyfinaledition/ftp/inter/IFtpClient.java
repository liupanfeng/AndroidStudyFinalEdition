package com.example.androidstudyfinaledition.ftp.inter;

import com.example.androidstudyfinaledition.ftp.bean.FtpConfig;

import java.io.File;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 下午6:47
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public interface IFtpClient {

     /**
      * ftp 连接
      * @param ftpConfig 连接配置
      * @param ftpResponse 连接回调
      */
     void connectFtpServer(FtpConfig ftpConfig, IFtpLoginResponse ftpResponse);

     /**
      * ftp 下载
      * @param remoteFilePath 远端的文件地址    "/android/SDKdemo/master/assets_master.zip"
      * @param targetFile    目标文件          "/home/ms/kehu/assets_master.zip"
      * @param ftpResponse   回调方法
      */
     void downloadFile(String remoteFilePath, File targetFile, IFtpDownloadResponse  ftpResponse);

     /**
      * ftp 文件上传
      * @param filePath 待上传的文件   "/android/SDKdemo/master/test.txt"
      * @param fileDirPath 远端的文件路径 "/android/SDKdemo/master"
      * @param ftpDownloadResponse 回调接口
      */
     void uploadFile(String filePath,String fileDirPath,IFtpDownloadResponse ftpDownloadResponse);

}
