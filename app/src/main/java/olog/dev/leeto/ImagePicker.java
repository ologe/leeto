package olog.dev.leeto;

import android.app.Activity;
import android.content.Context;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImagePicker {

    public static Observable<ImagePickerResult> single(Context context){
        return RxPaparazzo.single((Activity) context)
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> new ImagePickerResult(response.resultCode(), response.data()));
    }

    public static Observable<ImagePickerResult> multiple(Context context){
        return RxPaparazzo.multiple((Activity) context)
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> new ImagePickerResult(response.resultCode(), response.data()));
    }

    public static class ImagePickerResult {

        private final int resultCode;
        private final List<FileData> data = new ArrayList<>();

        public ImagePickerResult(int resultCode, FileData data) {
            this.resultCode = resultCode;
            this.data.add(data);
        }

        public ImagePickerResult(int resultCode, List<FileData> data) {
            this.resultCode = resultCode;
            this.data.addAll(data);
        }

        public int getResultCode() {
            return resultCode;
        }

        public List<FileData> getData() {
            return data;
        }
    }

}
