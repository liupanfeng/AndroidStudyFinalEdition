package com.example.designpattern.factory_abstract;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 13:42
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class AccessServerFactory  implements IFactory{

    @Override
    public AbstractUser createUser() {
        return new AccessServerUser();
    }

    @Override
    public AbstractDepartment createDepartment() {
        return new AccessDepartment();
    }
}
