package com.example.designpattern.factory_abstract;

import android.util.Log;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 13:38
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class AccessServerUser extends AbstractUser {

    public void insert(User user){
        Log.d("lpf","AccessServerUser 在user表中增加一条数据");
    }

    public User getUser(int userId){
        Log.d("lpf","AccessServerUser 根据Id 得到一条User数据");
        return null;
    }
}
