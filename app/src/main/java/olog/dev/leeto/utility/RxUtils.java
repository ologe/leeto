package olog.dev.leeto.utility;

import io.reactivex.disposables.Disposable;

public class RxUtils {

    private RxUtils() {
        //no instance
    }

    public static void unsubscribe(Disposable disposable){
        if(disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

}
