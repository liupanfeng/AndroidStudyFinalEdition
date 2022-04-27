package com.example.androidstudyfinaledition.ftp;

import android.text.TextUtils;

import com.example.androidstudyfinaledition.ftp.bean.FtpConfig;
import com.example.androidstudyfinaledition.ftp.inter.IFtpClient;
import com.example.androidstudyfinaledition.ftp.inter.IFtpDownloadResponse;
import com.example.androidstudyfinaledition.ftp.inter.IFtpLoginResponse;


import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 下午6:47
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class FtpClient implements IFtpClient {


    @Override
    public void connectFtpServer(FtpConfig ftpConfig, IFtpLoginResponse ftpResponse) {
        FtpClientManager ftpClientManager = FtpClientManager.FtpClientManagerHelper.getInstance();
        FTPClient ftpClient = ftpClientManager.getFTPClient();

        Observable.just(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                if (ftpClient!=null){
                    try {
                        ftpClient.connect(ftpConfig.serverIp, ftpConfig.serverPort);
                        ftpClient.login(ftpConfig.username, ftpConfig.password);
                        ftpResponse.onSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                        ftpResponse.onError(e.getMessage());
                    }
                }
            }
        });



    }


    @Override
    public void downloadFile(String remoteFilePath, File targetFile, IFtpDownloadResponse ftpResponse) {
        FtpConfig ftpConfig=new FtpConfig();
        FtpClientManager ftpClientManager = FtpClientManager.FtpClientManagerHelper.getInstance();
        FTPClient ftpClient = ftpClientManager.getFTPClient();
        if (ftpClient!=null){
            try {
                ftpClient.connect(ftpConfig.serverIp, ftpConfig.serverPort);
                ftpClient.login(ftpConfig.username, ftpConfig.password);
            } catch (Exception e) {
                e.printStackTrace();
                ftpResponse.onError(e.getMessage());
                return;
            }
        }

        try {
            ftpClient.download(remoteFilePath, targetFile, new FTPDataTransferListener() {
                @Override
                public void started() {
                    ftpResponse.started();
                }

                @Override
                public void transferred(int i) {
                    ftpResponse.transferred(i);
                }

                @Override
                public void completed() {
                    ftpResponse.completed();
                }

                @Override
                public void aborted() {
                    ftpResponse.onError("aborted");
                }

                @Override
                public void failed() {
                    ftpResponse.onError("failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ftpResponse.onError("error:"+e.getMessage());
        }
    }

    @Override
    public void uploadFile(String filePath, String fileDirPath,IFtpDownloadResponse ftpResponse) {
        FtpConfig ftpConfig=new FtpConfig();
        FtpClientManager ftpClientManager = FtpClientManager.FtpClientManagerHelper.getInstance();
        FTPClient ftpClient = ftpClientManager.getFTPClient();
        if (ftpClient!=null){
            try {
                ftpClient.connect(ftpConfig.serverIp, ftpConfig.serverPort);
                ftpClient.login(ftpConfig.username, ftpConfig.password);
            } catch (Exception e) {
                e.printStackTrace();
                ftpResponse.onError(e.getMessage());
                return;
            }
        }

        try {
            ftpClient.changeDirectory(fileDirPath);
        } catch (Exception e) {
            e.printStackTrace();
            ftpResponse.onError(e.getMessage());
            return;
        }

        try {
            ftpClient.upload(new File(filePath),new FTPDataTransferListener(){

                @Override
                public void started() {
                    ftpResponse.started();
                }

                @Override
                public void transferred(int i) {
                    ftpResponse.transferred(i);
                }

                @Override
                public void completed() {
                    ftpResponse.completed();
                }

                @Override
                public void aborted() {
                    ftpResponse.onError("aborted");
                }

                @Override
                public void failed() {
                    ftpResponse.onError("failed");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
        } catch (FTPAbortedException e) {
            e.printStackTrace();
        }

    }
}
