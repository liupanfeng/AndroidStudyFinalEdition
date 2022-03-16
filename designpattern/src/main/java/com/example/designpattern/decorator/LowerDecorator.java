package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 13:27
 * @Description : 转为小写装饰者
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class LowerDecorator extends Decorator{

    /**
     * 父类的是一个抽象，这里非常合适，因为这里直接super就可以了
     * @param component
     */
    public LowerDecorator(IComponent component) {
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
        return content.toLowerCase();
    }
}
