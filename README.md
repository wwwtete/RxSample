# RxSample
RxJava学习例子

在RxJava的世界里，有四种角色  

- ***Observable*** : 可观察者
- ***Observer*** : 观察者
- ***Subscriber*** : 订阅者
- ***Subjects*** : 主题

[TOC]


### Observable
| Event    | Iterable(pull) | Observable(push) |
| :------- | -------------: | :---------------:| 
| 检索数据 |  T next()      |   onNext(T)      |
| 发生错误 | thows Exception| onErrror(Throwble|
| 完成     | !hasNext()     | onCompleted()    |


``` markdown
 Observable<String> observable = Observable.create(new Observable.OnSubscripber<String>(){
    @override
    public void call(Subscriber<String> subscriber){
        subscriber.onNext("Hellow RxJava");
        subscriber.onCompleted();
    }
 });

 Subscription subscription = observable.subscribe(new Observer<String>(){
    @override
    public void onCompleted(){
        Log.d("onCompleted");
    }
    
    @override
    public void onError(Throwble e){
        Log.d("onEroro = %s",e);
    }
    
    @override
    public void onNext(String string){
        Log.d("onNext = %s",string);
    }
 });
````