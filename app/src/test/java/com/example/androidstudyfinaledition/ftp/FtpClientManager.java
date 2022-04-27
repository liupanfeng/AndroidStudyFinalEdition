package com.example.androidstudyfinaledition.ftp;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 下午6:53
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class FtpClientManager {

    private static final FtpClientManager instance = new FtpClientManager();

    public FtpClientManager() {
    }

    public static class FtpClientManagerHelper{
        public static FtpClientManager getInstance(){
            return instance;
        }
    }

    public FTPClient getFTPClient(){
        return new FTPClient();
    }
}
