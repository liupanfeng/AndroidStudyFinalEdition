package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 10:57
 * @Description : 具体的组件对象
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class DetailComponent implements IComponent{

    @Override
    public String display(String content) {
        System.out.println("原始内容："+content);
        return content;
    }
}
