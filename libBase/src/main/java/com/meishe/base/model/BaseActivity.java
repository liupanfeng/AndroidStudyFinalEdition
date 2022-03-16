package com.meishe.base.model;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.meishe.base.manager.AppManager;
import com.meishe.base.utils.BarUtils;
//import com.umeng.analytics.MobclickAgent;


/**
 * author : lhz
 * date   : 2019/5/11
 * desc   :基类Activity
 * base class Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String BUNDLE_FRAGMENTS_KEY = "android:support:fragments";

    /**
     * 是否拦截模板定义的onCreate
     * Whether to intercept the template definition
     */
    protected boolean interceptOnCreate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && this.clearFragmentsTag()) {
            /**
             *重建时清除 fragment的状态
             * Clear the state of the fragment during reconstruction
             */
            savedInstanceState.remove(BUNDLE_FRAGMENTS_KEY);
        }
        super.onCreate(savedInstanceState);
        /*
         * 设置布局
         * setLayout
         * */
        setContentView(bindLayout());
        AppManager.getInstance().addActivity(this);
        BarUtils.transparentStatusBar(this);
        if (interceptOnCreate) {
            return;
        }
        /*
         * 初始化数据
         * initialization data
         * */
        initData(savedInstanceState);
        /*
         * 初始化布局
         * Initialize layout
         * */
        initView();
        /*
         * 请求类数据加载
         * Request class data loading
         * */
        requestData();
    }

    /**
     * 是否拦截模板定义的onCreate
     * Whether to intercept the template definition  onCreate
     */
    public void setInterceptOnCreate(boolean interceptOnCreate) {
        this.interceptOnCreate = interceptOnCreate;
    }

    protected abstract int bindLayout();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initView();

    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    public Resources getResources() {
        /*
         * 字体大小不随设置变动而改变。
         * The font size does not change with the Settings.
         * */
        Resources resources = super.getResources();
        Configuration newConfig = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        if (newConfig.fontScale != 1) {
            newConfig.fontScale = 1;
           /* if (Build.VERSION.SDK_INT >= 17) {
                Context configurationContext = createConfigurationContext(newConfig);
                resources = configurationContext.getResources();
                displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale;
            } else {*/
            resources.updateConfiguration(newConfig, displayMetrics);
            // }
        }
        return resources;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null && this.clearFragmentsTag()) {
            /*
             * 销毁时不保存fragment的状态
             * The state of the fragment is not saved during destruction
             */
            outState.remove(BUNDLE_FRAGMENTS_KEY);
        }
    }

    protected boolean clearFragmentsTag() {
        return true;
    }

    /**
     * 使用IdleHandler延迟执行部分任务
     * Use IdleHandler to delay the execution of some tasks
     */
    protected final void delayToDealOnUiThread(final Runnable runnable) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (runnable != null) {
                    runnable.run();
                }
                return false;
            }
        });
    }

}
