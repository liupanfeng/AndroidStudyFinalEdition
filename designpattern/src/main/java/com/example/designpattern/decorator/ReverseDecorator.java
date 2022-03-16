package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 13:22
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class ReverseDecorator extends Decorator{

    public ReverseDecorator(IComponent component) {
        super(component);
    }

    @Override
    public String display(String content) {
        String display = super.display(content);
        String transform = transform(display);
        System.out.println("反转之后："+transform);
        return transform;
    }

    @Override
    protected String transform(String content) {
        StringBuilder stringBuilder = new StringBuilder(content);
        return stringBuilder.reverse().toString();
    }
}
