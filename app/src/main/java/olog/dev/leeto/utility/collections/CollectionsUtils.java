package olog.dev.leeto.utility.collections;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import olog.dev.leeto.utility.BuildVersion;

@UtilityClass
public class CollectionsUtils {

    public static <T> int indexOfFirst(@NonNull List<T> sourceList, @NonNull Predicate<T> predicate){
        for (int i = 0; i < sourceList.size(); i++) {
            if(predicate.apply(sourceList.get(i))){
                return i;
            }
        }
        return -1;
    }

    @NonNull
    public static <T> T first(@NonNull List<T> sourceList, @NonNull Predicate<T> predicate){
        T item = firstOrNull(sourceList, predicate);
        if(item == null){
            throw new NoSuchElementException("item not found");
        }
        return item;
    }

    @Nullable
    public static <T> T firstOrNull(@NonNull List<T> sourceList, @NonNull Predicate<T> predicate){
        if(BuildVersion.Nougat()){
            return sourceList.stream()
                    .filter(predicate::apply)
                    .findFirst()
                    .orElse(null);
        }

        for (T item : sourceList) {
            if(predicate.apply(item)){
                return item;
            }
        }
        return null;
    }

    public static <T> List<T> filter(@NonNull List<T> sourceList, @NonNull Predicate<T> predicate,
                                     @NonNull Comparator<? super T> comparator){
        if(BuildVersion.Nougat()){
            return sourceList.stream()
                    .filter(predicate::apply)
                    .sorted()
                    .collect(Collectors.toList());
        }

        List<T> result = new ArrayList<>();
        for (T t : sourceList) {
            if(predicate.apply(t)){
                result.add(t);
            }
        }

        Collections.sort(result, comparator);

        return result;
    }

    public interface Predicate<T> {
        boolean apply(T type);
    }
}
