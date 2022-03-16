package com.example.designpattern;

import android.util.Log;

import com.example.designpattern.decorator.DetailComponent;
import com.example.designpattern.decorator.EncodeDecorator;
import com.example.designpattern.decorator.ReverseDecorator;
import com.example.designpattern.decorator.UpperDecorator;
import com.example.designpattern.factory.Operation;
import com.example.designpattern.factory.OperationFactory;
import com.example.designpattern.factory.OperationTest;
import com.example.designpattern.factory_method.FactoryAdd;
import com.example.designpattern.factory_method.IFactory;
import com.example.designpattern.prototype.Brain;
import com.example.designpattern.prototype.Person;
import com.example.designpattern.prototype.Water;
import com.example.designpattern.singleton.DataManager;

import org.junit.Test;

public class Client {
    @Test
    public void test() {
//        OperationTest operationTest=new OperationTest();
//        operationTest.setNumA(10);
//        operationTest.setNumB(15);
//        int result = operationTest.getResult("+");
//        System.out.println("result="+result);

        for (int i = 0; i < 20; i++) {
            DataManager instance = DataManager.getInstance();
            System.out.println(instance.toString());
        }
    }

    @Test
    public void noFactory(){
        operation(8,9,"+");
    }

    @Test
    public void testFactoryMethod(){
        // 需要什么算法直接创建相应的算法工厂
        // 需求变更只需要需改某个算法类
        // 需求增加只需要相应的增加工厂和算法就可以扩展也很方便。
        IFactory factory=new FactoryAdd();
        Operation operation = factory.concreteOperation();
        operation.numberA=10;
        operation.numberB=16;
        operation.getResult();
    }

    @Test
    public void testFactory(){
        Operation operation = OperationFactory.createOperation("+");
        operation.numberA=10;
        operation.numberB=16;
        operation.getResult();
    }

    public int  operation(int numA,int numB,String operation){
            switch (operation){
                case "+":
                    return numA+numB;
                case "-":
                    return numA-numB;
                case "*":
                    return numA*numB;
                case "/":
                    return numA/numB;
                default:
                    return 0;
            }
    }



    @Test
    public void testPrototype() {
        Person person = new Person();
        Brain brain = new Brain();
        brain.setWater(new Water().setMineral(10));
        person.setBrain(brain);
        System.out.println("person:" + person.toString());

        try {
//            Person clone = (Person) person.clone();
//
//            clone.getBrain().setWater(clone.getBrain().getWater().setMineral(15));
//            clone.setAge(15);
//            System.out.println("更改clone对象之后---------------------");
//            System.out.println("clone:"+clone.toString());
//            System.out.println("person:"+person.toString());
//            System.out.println("证明调用了clone方法，类里边的对象指向的是同一个地址，一个修改了另外一个也就修改,基础数据类型拷贝成功了");


            Person clone = (Person) person.getDeepCloneInstance(person);

            clone.getBrain().setWater(clone.getBrain().getWater().setMineral(15));
            clone.setAge(15);
            System.out.println("更改clone对象之后---------------------");
            System.out.println("clone:" + clone.toString());
            System.out.println("person:" + person.toString());
            System.out.println("证明调用了clone方法，类里边的对象指向的不是同一个地址，一个修改了另外一个不会修改,深拷贝可用！");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //测试装饰者模式
    @Test
    public void testDecorator() {

        //先将输入的内容 大写 然后反转
        ReverseDecorator reverseDecorator = new ReverseDecorator(new UpperDecorator(new DetailComponent()));
        String display = reverseDecorator.display("lpf com from china!");
        System.out.println(display);
    }
}
