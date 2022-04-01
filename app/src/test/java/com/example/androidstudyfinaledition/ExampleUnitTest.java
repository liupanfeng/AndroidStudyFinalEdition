package com.example.androidstudyfinaledition;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

import static org.junit.Assert.*;

import androidx.annotation.MainThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //测试create操作符  创建型操作符号是用来创建被观察者的
    @Test
    public void testCreate() {
        //创建被观察者
//        Observable observable=Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("hi 观察者");
//            }
//        });
//
//        Observer observer=new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                System.out.println("onSubscribe");
//            }
//
//            @Override
//            public void onNext(String o) {
//                System.out.println("观察者收到来自被观察者的消息---"+o);
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
//        };

        //observable.subscribe(observer);


        //简写方式
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("hi 观察者");
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                System.out.println("onSubscribe");
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("观察者收到来自被观察者的消息---"+s);
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

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("hi 观察者");
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("观察者收到来自被观察者的消息---"+s);
//            }
//        });


        //just 操作符号 发送一串消息 顺序执行  可用于数据遍历等
//        Observable.just("唐三","唐昊","唐晨").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("观察者收到来自被观察者的消息---"+s);
//            }
//        });
//        String[] array={"唐三","唐昊","唐晨"};
//        Observable.fromArray(array).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("观察者收到来自被观察者的消息---"+s);
//            }
//        });


        //interval
        //创建一个按固定时间间隔发射整数序列的 Observable，相当于定时器
//        Observable.interval(50, 200,TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                System.out.println("观察者收到来自被观察者的消息---"+aLong);
//            }
//        });

//        Observable.range(0,5).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println("range---"+integer);
//            }
//        });

//        Observable.range(0,2).repeat(2).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println("range---"+integer);
//            }
//        });

//        String Host="http://blog.csdn.net/";
//        Observable.just("lpf85").map(s -> Host+s).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String integer) throws Exception {
//                System.out.println("accept---"+integer);
//            }
//        });

//        String Host="http://blog.csdn.net/";
//        List<String> mData=new ArrayList<>();
//        mData.add("lpf85");
//        mData.add("lpf86");
//        mData.add("lpf87");
//        Observable.fromArray(mData).flatMap(new Function<List<String>, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(List<String> strings) throws Exception {
//                return null;
//            }
//        });

        String Host="http://blog.csdn.net/";
        Observable.just("lpf85","lpf86","lpf87").flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return createResponse(Host,s);
            }
        }).cast(String.class).subscribe(new Consumer<String>() {

            @Override
            public void accept(String o) throws Exception {
                System.out.println("accept---"+o);
            }
        });
    }


    @Test
    public void testFromArray(){
        //用于遍历一个数组
//        Observable.fromArray(new Integer[]{1,2,3,4,5}).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println("观察者收到来自被观察者的消息---"+integer);
//            }
//        });

        //send empty message onNext 不走  只执行onComplete
//        Observable.empty().subscribe(new Observer<Object>() {
//
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                System.out.println("观察者收到来自被观察者的消息---onNext");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("观察者收到来自被观察者的消息---onComplete");
//            }
//        });

        //发送事件比较多，消费者处理不过来，超过缓存池数量之后就会出现被压
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 1000000; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer integer) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("观察者收到来自被观察者的消息---"+integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



    }

    @Test
    public void testFlowable(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 1000000000; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe---"+s);
                s.request(500);  //设置被压终止量
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext---"+integer);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Test
    public void testMap(){
//        Observable.just("head.png","photo.png").map(new Function<String, String>() {
//            @Override
//            public String apply(String s) throws Exception {
//                return s+1;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String integer) throws Exception {
//                System.out.println("accept---"+integer);
//            }
//        });
//
        Observable.just("config","login").flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return createResponse(s);
            }
        }).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                System.out.println("accept---"+o);
            }
        });

        Observable.just(1,2,3,4).groupBy(new Function<Integer, String>() {

            @Override
            public String apply(Integer integer) throws Exception {
                return integer>2?"A组":"B组";
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
                System.out.println("accept---"+stringIntegerGroupedObservable.getKey());
            }
        });

    }

    private ObservableSource<?> createResponse(String s) {
        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("login "+s);
            }
        });
    }

    private ObservableSource<?> createResponse(String host,String s) {
        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(host+s);
            }
        });
    }
}