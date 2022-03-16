package com.example.designpattern;

import com.example.libbase.FlowContent;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/21 19:41
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private List<FlowContent> list = new ArrayList<>();
    static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        FlowContent flowContent=new FlowContent();
        flowContent.setTitle("依赖倒置");
        flowContent.setContent("依赖倒置原则（Dependence Inversion Principle，简称DIP ）。\n\n" +
                "高层模块不应该依赖于底层模块，二者都应该依赖于抽象。\n\n" +
                "高层模块，通常是指调用端，底层模块通常称为实现端。\n\n" +
                "抽象不应该依赖具体的实现细节，实现细节应该依赖抽象。\n\n" +
                "也就是模块之前的依赖是基于抽象的，实现类之前不能相互依赖，其依赖关系是通过接口或者抽象类的。\n\n" +
                "核心思想就是：面向接口编程，不要面向实现编程。");
        list.add(flowContent);

        flowContent=new FlowContent();
        flowContent.setTitle("单一指责");
        flowContent.setContent("单一职责原则（Single Responsibility Principle，简称SRP ）。\n\n" +
                "应该有且仅有一个原因引起类的变更，通俗来讲就是一个类只干一件事。\n\n"
                );
        list.add(flowContent);

        flowContent=new FlowContent();
        flowContent.setTitle("迪米特法则");
        flowContent.setContent("迪米特法则（Law of Demeter，简称LOD）。\n\n" +
                "只与朋友通信，不要搭理陌生人。\n\n"
                );
        list.add(flowContent);

        flowContent=new FlowContent();
        flowContent.setTitle("里氏替换原则");
        flowContent.setContent("里氏替换原则（ Liskov Substitution Principle 简称LSP）。\n\n" +
                "子类可以实现父类的抽象方法，但不能覆盖父类的非抽象方法。\n\n"+
                "子类中可以增加自己特有的方法。\n\n"+
                "当子类的方法重载父类的方法时，方法的前置条件（即方法的形参）要比父类方法的输入参数更宽松。\n\n"+
                "当子类的方法实现父类的抽象方法时，方法的后置条件（即方法的返回值）要比父类更严格。\n\n"+
                "里氏代换原则是很多其它设计模式的基础。它和开放封闭原则的联系尤其紧密。违背了里氏代换原则就一定不符合开放封闭原则。\n\n"
                );
        list.add(flowContent);

        flowContent=new FlowContent();
        flowContent.setTitle("接口隔离原则");
        flowContent.setContent("接口隔离原则（ISP）。\n\n" +
                "接口隔离的目的就是将庞大的接口拆分成更小的或者说更具体的接口，使得系统的耦合度大大降低，从而容易重构、修改\n\n"
                );
        list.add(flowContent);

        flowContent=new FlowContent();
        flowContent.setTitle("开闭原则");
        flowContent.setContent("英文全称（Open Close Principle）,简称：OCP。\n\n" +
                "开闭原则就是对扩展开放，对修改关闭。\n\n"
                );
        list.add(flowContent);
    }

    public List<FlowContent> getList() {
        return list;
    }
}
