package com.example.androidstudyfinaledition;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/10 20:40
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class Data<T> {

    private T mData;
    private String mErrorMsg;

    public Data(T data, String errorMsg) {
        mData = data;
        mErrorMsg = errorMsg;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
    }
}