package com.omkarmhatre.fileexplorer.AsynLoader;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AsyncLoader {

    public static void load(SubscriptionBlock subscriptionBlock, final OnCompletionBlock completionBlock){

        Observable<SubscriptionBlock> observable = Observable.just(subscriptionBlock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<SubscriptionBlock>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SubscriptionBlock subscriptionBlock) {
                try {
                    subscriptionBlock.subscribe();
                } catch (Exception e) {
                    completionBlock.onComplete(e);
                }
            }

            @Override
            public void onError(Throwable e) {
                completionBlock.onComplete(new Exception(e));
            }

            @Override
            public void onComplete() {
                completionBlock.onComplete(null);
            }
        });

    }


    public static void load(SubscriptionBlock subscriptionBlock) {

        Observable<SubscriptionBlock> observable = Observable.just(subscriptionBlock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<SubscriptionBlock>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SubscriptionBlock subscriptionBlock) {
                try {
                    subscriptionBlock.subscribe();
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}

