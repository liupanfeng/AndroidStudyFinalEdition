package com.example.androidstudyfinaledition.ftp;

import com.example.androidstudyfinaledition.ftp.bean.FtpConfig;
import com.example.androidstudyfinaledition.ftp.inter.IFtpClient;
import com.example.androidstudyfinaledition.ftp.inter.IFtpDownloadResponse;
import com.example.androidstudyfinaledition.ftp.inter.IFtpLoginResponse;
import com.example.androidstudyfinaledition.ftp.utils.ZipUtils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 下午8:25
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class FtpTest {

    private IFtpClient ftpClient;

    @Test
    public void testConnect() {
        ftpClient = new FtpClient();
        ftpClient.connectFtpServer(new FtpConfig(), new IFtpLoginResponse() {
            @Override
            public void onSuccess() {
                System.out.println("connect onSuccess");
            }

            @Override
            public void onError(String errorMsg) {
                System.out.println("onError errorMsg =" + errorMsg);
            }
        });
    }

    /**
     * ftp下载
     */
    @Test
    public void download() {
        ftpClient = new FtpClient();
        File file=new File("/home/ms/kehu/assets_master.zip");
        String remoteFilePath="/android/SDKdemo/master/assets_master.zip";
        ftpClient.downloadFile(remoteFilePath, file, new IFtpDownloadResponse() {
            @Override
            public void started() {
                System.out.println("downloadFile started");
            }

            @Override
            public void transferred(int i) {
                System.out.println("downloadFile transferred:" + i);
            }

            @Override
            public void completed() {
                System.out.println("downloadFile completed");
            }

            @Override
            public void onError(String errorMsg) {
                System.out.println("downloadFile onError:" + errorMsg);
            }
        });
    }

    @Test
    public void testUpload(){
        FtpClient ftpClient=new FtpClient();
        String filePath="/android/SDKdemo/master/test.txt";
        String remoteFilePath="/android/SDKdemo/master";
        ftpClient.uploadFile(filePath, remoteFilePath,new IFtpDownloadResponse() {
            @Override
            public void started() {
                System.out.println("uploadFile started");
            }

            @Override
            public void transferred(int i) {
                System.out.println("uploadFile transferred:" + i);
            }

            @Override
            public void completed() {
                System.out.println("uploadFile completed");
            }

            @Override
            public void onError(String errorMsg) {
                System.out.println("uploadFile onError:" + errorMsg);
            }
        });
    }

    /**
     * 测试解压
     */
    @Test
    public void unzip(){
        String zipFileName ="/home/ms/kehu/assets_master.zip";
        String zipDirPath ="/home/ms/kehu/assets";
        try {
            ZipUtils.unzip(zipFileName,zipDirPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("unzipFilesToPath onError:" + e.getMessage());
        }

    }

    /**
     * 添加压缩方法
     */
    @Test
    public void testZip(){
        String zipFileName ="/home/ms/kehu/assets_master.zip";
        String zipDirPath ="/home/ms/kehu/assets";
        File file=new File(zipDirPath);
        try {
            ZipUtils.zipFile(file,zipFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
