package com.meishe.base.model;

//import com.meishe.model.net.NvsServerClient;

/**
 * author : lhz
 * date   : 2020/8/14
 * desc   :
 * 模型基类
 * Model base class
 */
public abstract class BaseModel implements IModel {

    public BaseModel(){}
    @Override
    public void onClear() {
//        NvsServerClient.getInstance().cancelRequest(this);
    }
}
