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

    @Test
    public void testRemoveDir(){
        String zipDirPath ="/home/ms/kehu/assets";
        deleteAllFile(zipDirPath);
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


    @Test
    public void testFileName(){
        String zipFileName ="/home/ms/kehu/assets_master.zip";
        File file=new File(zipFileName);
        System.out.println("file.name=" + file.getName());

        String zipDirPath ="/home/ms/kehu/assets";
        File file2=new File(zipDirPath);
        String parent = file2.getParent();
        deleteAllFile(zipDirPath);
        System.out.println("file2.getParent()=" + parent);

    }


    public static boolean deleteAllFile(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除文件夹失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子文件夹
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子文件夹
            else if (files[i].isDirectory()) {
                flag = deleteAllFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除文件夹失败！");
            return false;
        }
        // 删除当前文件夹
        if (dirFile.delete()) {
            System.out.println("删除文件夹" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }


    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径只有单个文件
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println(fileName + "不存在！");
            return false;
        }
    }

}
