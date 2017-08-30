package olog.dev.leeto.utility;

import io.reactivex.disposables.Disposable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RxUtils {

    public static void unsubscribe(Disposable disposable){
        if(disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

}
