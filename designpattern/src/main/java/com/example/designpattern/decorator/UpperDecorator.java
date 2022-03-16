package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 13:24
 * @Description : 大写装饰
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class UpperDecorator extends Decorator{

    public UpperDecorator(IComponent component) {
        super(component);
    }

    @Override
    public String display(String content) {
        String display = super.display(content);
        String transform = transform(display);
        System.out.println("大写之后："+transform);
        return transform;
    }

    @Override
    protected String transform(String content) {
        return content.toUpperCase();
    }
}
