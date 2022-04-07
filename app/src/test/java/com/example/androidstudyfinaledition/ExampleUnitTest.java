package com.example.androidstudyfinaledition;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.CompletableObserver;
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
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

import androidx.annotation.MainThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

//        String Host="http://blog.csdn.net/";
//        Observable.just("lpf85","lpf86","lpf87").flatMap(new Function<String, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(String s) throws Exception {
//                return createResponse(Host,s);
//            }
//        }).cast(String.class).subscribe(new Consumer<String>() {
//
//            @Override
//            public void accept(String o) throws Exception {
//                System.out.println("accept---"+o);
//            }
//        });

//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").buffer(3).subscribe(new Consumer<List<String>>() {
//            @Override
//            public void accept(List<String> strings) throws Exception {
//                for (int i = 0; i < strings.size(); i++) {
//                    System.out.println("accept---"+strings.get(i)+i);
//                }
//                System.out.println("accept-------------------");
//            }
//        });

//        Observable.just(1,2,3,4,5,6).groupBy(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return integer>3?"1组":"2组";
//            }
//        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
//            @Override
//            public void accept(GroupedObservable<String, Integer> objectIntegerGroupedObservable) throws Exception {
//                System.out.println("accept---"+objectIntegerGroupedObservable.getKey()+" value=");
//            }
//        });

//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").filter(new Predicate<String>() {
//            @Override
//            public boolean test(String s) throws Exception {
//                return s.startsWith("唐");
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").elementAt(3).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

//        Observable.just(1,2,2,3,4,4,5,6).distinct().subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println("accept---"+integer);
//            }
//        });

//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").skip(3).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String str) throws Exception {
//                System.out.println("accept---"+str);
//            }
//        });


//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").take(3).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String str) throws Exception {
//                System.out.println("accept---"+str);
//            }
//        });


//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").ignoreElements().subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                System.out.println("onSubscribe---");
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("onComplete---");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError---");
//            }
//        });


        /////////////////////////////////////组合操作符////////////////////////////////
//        Observable.just("云韵","古薰儿","美杜莎").startWith("萧炎").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

//        Observable<String> just1 = Observable.just("云韵", "古薰儿", "美杜莎");
//        Observable<String> just2 = Observable.just("小舞", "宁容容", "白沉香");
//        Observable.merge(just1,just2).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

//        Observable<String> just1 = Observable.just("云韵", "古薰儿", "美杜莎");
//        Observable<String> just2 = Observable.just("小舞", "宁容容", "白沉香");
//        Observable.concat(just1,just2).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

//        Observable<String> just1 = Observable.just("云韵", "古薰儿", "美杜莎");
//        Observable<String> just2 = Observable.just("一", "二", "三");
//        Observable.zip(just1, just2, new BiFunction<String, String, String>() {
//            @Override
//            public String apply(String s, String s2) throws Exception {
//                return s+"颜值第"+s2;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

//        Observable<String> just1 = Observable.just("云韵", "古薰儿", "美杜莎");
//        Observable<String> just2 = Observable.just("一", "二", "三");
//        Observable.combineLatest(just1, just2, new BiFunction<String, String, String>() {
//            @Override
//            public String apply(String s, String s2) throws Exception {
//                return s+"第"+s2;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

        //这种延时的必须在Android环境才可以
//        Observable.just("云韵","古薰儿","美杜莎").startWith("萧炎").delay(300,TimeUnit.MICROSECONDS).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i>2){
//                        emitter.onError(new Throwable("throwable"));
//                    }
//                    emitter.onNext(i);
//                }
//                emitter.onComplete();
//            }
//        }).onErrorReturn(new Function<Throwable, Integer>() {
//            @Override
//            public Integer apply(Throwable throwable) throws Exception {
//                return 6;
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("accept---"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError---"+e.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("onComplete---");
//            }
//        });


//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i>2){
//                        emitter.onError(new Throwable("throwable"));
//                    }
//                    emitter.onNext(i);
//                }
//            }
//        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
//                Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                        emitter.onNext(6);
//                    }
//                });
//                return integerObservable;
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("accept---"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError---"+e.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("onComplete---");
//            }
//        });

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i>2){
//                        emitter.onError(new Throwable("throwable"));
//                    }
//                    emitter.onNext(i);
//                }
//            }
//        }).onExceptionResumeNext(new Observable<Integer>() {
//            @Override
//            protected void subscribeActual(Observer<? super Integer> observer) {
//                observer.onNext(6);
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("accept---"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError---"+e.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("onComplete---");
//            }
//        });

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i==1){
//                        emitter.onError(new Throwable("throwable"));
//                    }
//                    emitter.onNext(i);
//                }
//            }
//        }).retry(2).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("accept---"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError---"+e.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("onComplete---");
//            }
//        });

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 5; i++) {
//                    if (i>2){
//                        emitter.onError(new Throwable("throwable"));
//                    }
//                    emitter.onNext(i);
//                }
//            }
//        }).onExceptionResumeNext(new Observable<Integer>() {
//            @Override
//            protected void subscribeActual(Observer<? super Integer> observer) {
//                observer.onNext(6);
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("accept---"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError---"+e.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("onComplete---");
//            }
//        });
//

//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").all(new Predicate<String>() {
//            @Override
//            public boolean test(String s) throws Exception {
//                return s.startsWith("唐");
//            }
//        }).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                System.out.println("accept---"+aBoolean);
//            }
//        });

//        Observable.just("唐三","唐昊","唐晨","小舞","马红俊","宁容蓉").contains("唐三").subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                System.out.println("accept---"+aBoolean);
//            }
//        });


//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onComplete();
//            }
//        }).defaultIfEmpty("萧炎").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("accept---"+s);
//            }
//        });


//        Observable.just("唐三","唐昊","唐晨").toList().subscribe(new Consumer<List<String>>() {
//            @Override
//            public void accept(List<String> strings) throws Exception {
//                for (int i = 0; i < strings.size(); i++) {
//                    String s = strings.get(i);
//                    System.out.println("accept---"+s);
//                }
//            }
//        });

//        Observable.just("唐三","小舞","唐昊","唐晨").toSortedList().subscribe(new Consumer<List<String>>() {
//            @Override
//            public void accept(List<String> strings) throws Exception {
//                for (int i = 0; i < strings.size(); i++) {
//                    String s = strings.get(i);
//                    System.out.println("accept---"+s);
//                }
//            }
//        });

        Douluo tangsan=new Douluo("唐三","100级");
        Douluo douluo=new Douluo("菊斗罗","95级");
        Douluo tangchen=new Douluo("唐晨","99级");

        Observable.just(tangchen,douluo,tangchen).toMap(new Function<Douluo, String>() {
            @Override
            public String apply(Douluo douluo) throws Exception {
                return douluo.getLevel();
            }
        }).subscribe(new Consumer<Map<String, Douluo>>() {
            @Override
            public void accept(Map<String, Douluo> stringDouluoMap) throws Exception {
                String name = stringDouluoMap.get("99级").getName();
                System.out.println("accept---"+name);
            }
        });

    }


    class Douluo{
        public Douluo(String name, String level) {
            this.name = name;
            this.level = level;
        }
        String name;
        String level;

        public String getName() {
            return name;
        }

        public String getLevel() {
            return level;
        }

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