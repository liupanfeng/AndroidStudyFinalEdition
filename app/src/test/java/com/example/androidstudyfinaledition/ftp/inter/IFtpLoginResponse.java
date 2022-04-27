package com.example.androidstudyfinaledition.ftp.inter;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 下午6:50
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public interface IFtpLoginResponse {

    void onSuccess();

    void onError(String errorMsg);

}
