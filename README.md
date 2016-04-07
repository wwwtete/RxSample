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
    @Override
    public void call(Subscriber<String> subscriber){
        subscriber.onNext("Hellow RxJava");
        subscriber.onCompleted();
    }
 });

 Subscription subscription = observable.subscribe(new Observer<String>(){
    @Override
    public void onCompleted(){
        Log.d("onCompleted");
    }
    
    @Override
    public void onError(Throwble e){
        Log.d("onEroro = %s",e);
    }
    
    @Override
    public void onNext(String string){
        Log.d("onNext = %s",string);
    }
 });
````
### Subject = Observable + Observer

> Subject: 是一个神奇的对象，它既可以是Observable也可以是一个Observer  

  RxJava提供四种不同的Subject
- **PublishSubject**
- **BehaviorSubject**
- **ReplaySubject**
- **AsyncSubject**

**PublishSubject**
```markdown
        //1.创建一个空的Subject
        PublishSubject publishSubject = PublishSubject.create();

        //2.订阅Subjec
        publishSubject.subscribe(new Observer<String>(){
            @Override
            public void onCompleted() {
                view.output("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("onError = "+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("onNext = "+s);
            }
        });

        //3.由subjec发射数据
        for(int i=0;i<10;i++){
            char v = (char) (65+i);
            publishSubject.onNext(String.valueOf(v));
        }

        publishSubject.onCompleted();
```
**BehaviorSubject**
> BehaviorSubject会先向它的订阅者发送一条截止订阅前最新的一条数据或初始值，然后正常发送数据

```markdown
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create("默认值");

        //2.订阅Subjec
        behaviorSubject.subscribe(new Observer<String>(){
            @Override
            public void onCompleted() {
                view.output("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("onError = "+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("onNext = "+s);
            }
        });

        //3.由subjec发射数据
        for(int i=0;i<10;i++){
            char v = (char) (65+i);
            behaviorSubject.onNext(String.valueOf(v));
        }

        behaviorSubject.onCompleted();
```
**ReplaySubject**
> ReplaySubject会缓存它所订阅的所有数据，向任意一个订阅它的观察者重发，可以限制缓存数量或时间段之内的数据

```markdown
    private void onSample1(Output view) {

        ReplaySubject<String> replaySubject = ReplaySubject.create();

        Observer<String> first = getObserver(view, "first");
        replaySubject.subscribe(first);
        for(int i=0;i<10;i++){
            char v = (char) (65+i);
            replaySubject.onNext(String.valueOf(v));
        }
        replaySubject.onCompleted();
        Observer<String> second = getObserver(view,"second");
        replaySubject.subscribe(second);

    }

    public Observer<String> getObserver(final Output view,final String tag){
        return new Observer<String>() {
            @Override
            public void onCompleted() {
                view.output("["+tag+"]onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("["+tag+"]onError="+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("["+tag+"]onNext = "+s);
            }
        };
    }
```

**AsyncSubject**
> 当源Observable完成时AsyncSubject只会发布最后一条数据给已经订阅的每一个观察者

```markdown
    private void onSample1(final Output view) {
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(getObserver(view,"first"));
        asyncSubject.subscribe(getObserver(view,"second"));

        //3.由subjec发射数据
        for(int i=0;i<10;i++){
            char v = (char) (65+i);
            asyncSubject.onNext(String.valueOf(v));
        }

        asyncSubject.onCompleted();
    }

    public Observer<String> getObserver(final Output view,final String tag){
        return new Observer<String>() {
            @Override
            public void onCompleted() {
                view.output("["+tag+"]onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("["+tag+"]onError="+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("["+tag+"]onNext = "+s);
            }
        };
    }
```














































