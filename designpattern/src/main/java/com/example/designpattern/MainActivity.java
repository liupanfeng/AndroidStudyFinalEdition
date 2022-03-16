package com.example.designpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.designpattern.factory_abstract.User;
import com.example.libbase.FlowContent;
import com.example.libbase.adapter.FlowAdapter;
import com.example.libbase.view.FlowLayout;
import com.example.libbase.view.PopCenterView;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个module 主要用于设计模式的归纳总结
 * 深入理解设计模式以及设计原则
 */
public class MainActivity extends AppCompatActivity {

    private PopCenterView mPopCenterView;
    private FlowLayout mFlowLayout;
    private List<FlowContent> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlowLayout = findViewById(R.id.flow_layout);

        initData();
        initView();

        //简单工厂设计模式  如果进行扩展  需要修改OperationFactory 中的分支 这样扩展是方便的  但是违反了开闭原则
//        Operation operation = OperationFactory.createOperation("+");  //去除了 工具和产品的依赖
//        operation.setNumberA(10);
//        operation.setNumberB(12);
//        double result = operation.getResult();
//        Log.e("lpf", "result=" + result);

        //通过反射的方式
//        Operation operation = OperationFactory.crateOpe(OperationAdd.class);  //去除了 工具和产品的依赖
//        operation.setNumberA(10);
//        operation.setNumberB(12);
//        double result = operation.getResult();
//        Log.e("lpf", "result=" + result);

        //工厂方法模式 这样处理如果需要扩展的时候就不需要修改工厂类了，可以动态的扩展  也保证的封装性   封装 继承 多态
        //工厂方法设计模式 生产的是一种商品  不同的厂商生产某一类具体的产品  具体的工厂决定了这个是什么产品
        //把具体生产那一件商品交给厂商的类型，而不是由客户端指定商品的类型
        //普通工厂是一个工厂，外部指定要商品的类型 这样增粘了 客户端和商品的耦合  工厂方法解耦了 客户端 和商品的耦合
//        IFactory factory= new FacAdd(); //先创建工厂  具体的实例化交给子类  什么产品交给工厂决定
//        Operation operation = factory.concreteOperation();
//        operation.setNumberA(10);
//        operation.setNumberB(13);
//        double result = operation.getResult();
//        Log.e("lpf", "result=" + result);


        //抽象工厂模式  这个跟工厂方法模式区别是 一个不同的厂商还能生产不同的商品的场景
        //未使用设计模式之前的调用方式 显然创建工厂的方式直接使用了具体的某一个工厂（sql） 如果换一个数据库Access，不具备可扩展性
        //抽象工厂 里边增加了对产品族的扩充  一个工厂可以生产多个商品
        User user = new User();
//        SqlServerUser sqlServerUser = new SqlServerUser();
//        sqlServerUser.insert(user);
//        sqlServerUser.getUser(1);

        //抽象工厂 实现测试用例
//        IFactory factory=new AccessServerFactory();
//        AbstractUser user1 = factory.createUser();
//        user1.insert(user);
//        AbstractDepartment department = factory.createDepartment();
//        department.insert(new Department());



    }


    public int operation(String operation){
        int numA=10;
        int numB=15;
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

    private void initData() {
        list = DataManager.getInstance().getList();
    }

    private void initView() {
        FlowAdapter flowAdapter = new FlowAdapter(this, mFlowLayout);
        flowAdapter.setData(list, R.drawable.text_flow_round_corner_bg);
        flowAdapter.setOnFlowViewClickListener(new OnFlowClick());
        mPopCenterView = PopCenterView.create(MainActivity.this, null);
    }


    class OnFlowClick implements FlowAdapter.OnFlowViewClickListener {

        @Override
        public void onFlowClick(View view, String content) {
            if (view instanceof TextView) {
                mPopCenterView.show(content);
            }
        }
    }

}