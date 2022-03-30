package com.example.androidstudyfinaledition.rxjava;

/**
 * @author : lpf
 * @FileName: MSObserable
 * @Date: 2022/3/30 17:46
 * @Description: 被观察者
 */
public class MSObserable<T> {

 private MSObservableOnSubscribe onSubscribe;

 private MSObserable(MSObservableOnSubscribe onSubscribe) {
  this.onSubscribe = onSubscribe;
 }

 /**
  *  模拟Rxjava create操作符
  * @param onSubscribe
  * @param <T>
  * @return
  */
  public static <T> MSObserable<T> create(MSObservableOnSubscribe<T> onSubscribe){
     return new MSObserable(onSubscribe);
  }

 /**
  * 订阅 这里有点反常的是 被观察者 调用这个方法 传的参数的是观察者
  * @param observer
  */
 public void subscrible(MSObserver<? super T> observer){
     onSubscribe.subscribe(observer);
  }

}
