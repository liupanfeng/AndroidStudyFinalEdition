package com.example.designpattern.strategy;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/27 17:40
 * @Description : 策略模式有一个上下文  策略模式 解耦了具体的算法跟客户端的隔离，这样如果某个算法如果需要改变，只需要改变某个算法的逻辑就可以
 *              不需要改客户端，扩展也比较简单，只需要增加一个相应的策略，没有大范围的修改，
 *              编程是一门艺术，大批量的改动，显然是非常丑陋的做法。
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class StrategyContext {

    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void algorithmInterface() {
        if (strategy != null) {
            strategy.algorithmInterface();
        }
    }

}
