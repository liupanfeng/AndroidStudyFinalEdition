package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 11:16
 * @Description : 解密 装饰者
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class DecodeDecorator extends Decorator {

    public DecodeDecorator(IComponent component) {
        super(component);
    }

    @Override
    public String display(String content) {
        //通过调用父类的display的方法，拿到多个步骤积累的结果
        String display = super.display(content);
        //调用这个类的装饰方法，重新得到结果给返回回去
        // 这个例子好像是数据池子 过滤一遍  过滤一遍 适合多个条件这个情况 ,并且能看出来 组件还是之前的那个组件
        String transform = transform(display);
        return transform;
    }

    @Override
    protected String transform(String content) {
        System.out.println("invoke DecodeDecorator....");
        return EnDecodeUtil.encodeDecode(content);
    }
}
