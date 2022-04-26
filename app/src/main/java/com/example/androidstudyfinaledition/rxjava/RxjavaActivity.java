package com.example.androidstudyfinaledition.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidstudyfinaledition.R;
import com.example.androidstudyfinaledition.retrofit.antishake.WanAndroidApi;
import com.example.androidstudyfinaledition.retrofit.antishake.bean.ProjectBean;
import com.example.androidstudyfinaledition.retrofit.antishake.bean.ProjectItem;
import com.example.androidstudyfinaledition.retrofit.antishake.util.RetrofitClient;
import com.example.androidstudyfinaledition.retrofit.nesting.IUserListener;
import com.example.androidstudyfinaledition.retrofit.nesting.LoginReqeust;
import com.example.androidstudyfinaledition.retrofit.nesting.LoginResponse;
import com.example.androidstudyfinaledition.retrofit.nesting.RegisterRequest;
import com.example.androidstudyfinaledition.retrofit.nesting.RegisterResponse;
import com.example.androidstudyfinaledition.retrofit.nesting.RetrofitUtil;
import com.jakewharton.rxbinding2.view.RxView;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.androidstudyfinaledition.utils.RxJavaUtils.rxud;

/**
 * 模拟rxjava各种操作符号
 * rxjava 比较难理解的就是泛型
 * ？通配符 用于赋值
 * T 用户声明类型
 * ？ extends ：可读不可写  类型的上界限
 * ？ super  ：可写不可读  类型的下界限
 */
public class RxjavaActivity extends AppCompatActivity {

    private static final String TAG = RxjavaActivity.class.getSimpleName();
    // 网络图片的链接地址
    private final static String PATH = "http://pic1.win4000.com/wallpaper/c/53cdd1f7c1f21.jpg";
    private ImageView imageView;


    // 弹出加载框
    private ProgressDialog progressDialog;
    private View btnLoadPic;
    private View btn_request;
    private WanAndroidApi wanAndroidApi;
    private TextView textview;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        wanAndroidApi = RetrofitClient.createRetrofit().create(WanAndroidApi.class);
        imageView = findViewById(R.id.imageView);
        btnLoadPic = findViewById(R.id.btn_load_pic);
        btn_request = findViewById(R.id.btn_request);
        textview = findViewById(R.id.textview);
        //测试手写rxjava create操作符号，并实现链式调度
//        MSObserable.create(new MSObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(MSObserver<? super String> msObserver) {
//                msObserver.onNext("hi 观察者");
//            }
//        }).subscrible(new MSObserver<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d("lpf","观察者收到消息---"+s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        //interval
        //创建一个按固定时间间隔发射整数序列的 Observable，相当于定时器
//        Observable.interval(50, 200, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
//            @SuppressLint("CheckResult")
//            @Override
//            public void accept(Long aLong) throws Exception {
//                System.out.println("观察者收到来自被观察者的消息---"+aLong);
//            }
//        });


//        Observable.just("云韵","古薰儿","美杜莎").startWith("萧炎").
//                delay(3000,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Toast.makeText(RxjavaActivity.this, s, Toast.LENGTH_SHORT).show();
//            }
//        });


        btnLoadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPic();
            }
        });

        antiShake();

    }


    //应用场景1  延时执行操作 默认是子线程 observeOn 切换的是观察者的执行线程
    public void delayLoading() {


//        Observable.just(1).delay(2000,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Toast.makeText(RxjavaActivity.this, "延时执行", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    //应用场景2  对数据进行变换处理
    public void loadPic() {

        Observable.just(PATH).map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(@NotNull String s) throws Exception {
                URL url = new URL(s);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
                return null;
            }
        }).map(new Function<Bitmap, Bitmap>() {
            @Override
            public Bitmap apply(@NotNull Bitmap bitmap) throws Exception {
                Log.d("lpf", "apply: 是这个时候下载了图片啊:" + System.currentTimeMillis());
                return bitmap;
            }
        }).map(new Function<Bitmap, Bitmap>() {
            @Override
            public Bitmap apply(@NotNull Bitmap bitmap) throws Exception {
                //添加水印
                Paint paint = new Paint();
                paint.setTextSize(56);
                paint.setColor(Color.GREEN);

                return drawTextToBitmap(bitmap, "水印来了，哈哈", paint, 100, 100);
            }
        }).compose(rxud()).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                progressDialog = new ProgressDialog(RxjavaActivity.this);
                progressDialog.setTitle("download run");
                progressDialog.show();
            }

            @Override
            public void onNext(@NotNull Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.d("lpf", e.getMessage());
                progressDialog.dismiss();
            }

            @Override
            public void onComplete() {
                progressDialog.dismiss();
            }
        });
    }


    /**
     * 使用RxJava 处理网络嵌套的问题 线程自由切换
     */
    @SuppressLint("CheckResult")
    public void doNetworkNesting() {
        RetrofitUtil.createRetrofit().create(IUserListener.class)
                .registerAction(new RegisterRequest())//执行注册操作  耗时操作 这里是子线程
                .compose(rxud())
                .doOnNext(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        //执行了注册操作，更新UI  这里是主线程
                    }
                }).observeOn(Schedulers.io()) //给下面的操作分配子线程
                .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
                    @Override
                    public ObservableSource<LoginResponse> apply(@NotNull RegisterResponse registerResponse) throws Exception {
                        //这里执行登录操作 耗时操作，这里是子线程
                        Observable<LoginResponse> loginResponseObservable = RetrofitUtil.createRetrofit().
                                create(IUserListener.class).loginAction(new LoginReqeust());
                        return loginResponseObservable;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        //登录成功后，这里执行更新UI的操作  这里是主线程
                    }
                });
    }


    private final Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {
        Bitmap.Config config = bitmap.getConfig();

        paint.setDither(true);
        paint.setFilterBitmap(true);
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(config, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);

        return bitmap;
    }


    //防抖动
    @SuppressLint("CheckResult")
    private void antiShake() {
        RxView.clicks(btn_request).throttleFirst(2000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .flatMap(new Function<Object, ObservableSource<ProjectBean>>() {
                    @Override
                    public ObservableSource<ProjectBean> apply(@NotNull Object o) throws Exception {
                        return wanAndroidApi.getProject();
                    }
                }).flatMap(new Function<ProjectBean, ObservableSource<ProjectBean.DataBean>>() {
            @Override
            public ObservableSource<ProjectBean.DataBean> apply(@NotNull ProjectBean projectBean) throws Exception {
                return Observable.fromIterable(projectBean.getData());
            }
        }).flatMap(new Function<ProjectBean.DataBean, ObservableSource<ProjectItem>>() {
            @Override
            public ObservableSource<ProjectItem> apply(@NotNull ProjectBean.DataBean dataBean) throws Exception {
                return wanAndroidApi.getProjectItem(1, dataBean.getId());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ProjectItem>() {
            @Override
            public void accept(ProjectItem projectItem) throws Exception {
                Log.d(TAG, "accept: " + projectItem);
                textview.setText(projectItem.toString());
            }
        });
    }


}