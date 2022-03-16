package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 11:11
 * @Description : 加密装饰者
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class EncodeDecorator extends Decorator{

    public EncodeDecorator(IComponent component) {
        super(component);
    }

    @Override
    public String display(String content) {
        String display = super.display(content);
        String transform = transform(display);
        return transform;
    }

    @Override
    protected String transform(String content) {
        System.out.println("invoke EncodeDecorator....");
        return EnDecodeUtil.encodeDecode(content);
    }
}
