package com.example.androidstudyfinaledition.mvp;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/11 20:34
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class IpInfo {

    private int code;  //请求码

    private IpData ipData;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public IpData getIpData() {
        return ipData;
    }

    public void setIpData(IpData ipData) {
        this.ipData = ipData;
    }
}
