package com.idthk.network.http;



import com.idthk.network.BllException;
import com.idthk.network.ResultData;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MVP on 2016/11/9 0009.
 */

public class RxUtil {

    private RxUtil() {
        //空实现
    }

    public static <T> Observable.Transformer<ResultData<T>, T> transResult() {

        return new Observable.Transformer<ResultData<T>, T>() {
            @Override
            public Observable<T> call(Observable<ResultData<T>> tObservable) {
                return tObservable.flatMap(new Func1<ResultData<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResultData<T> result) {
                        if (result.success()) {
                            return createData(result.getData());
                        } else {
                            return Observable.error(new BllException(result.getCode(), result.getMsg()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }


    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }

}
