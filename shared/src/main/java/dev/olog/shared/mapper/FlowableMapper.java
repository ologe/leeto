package dev.olog.shared.mapper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public abstract class FlowableMapper <From, To>
        implements Function<List<From>, SingleSource<List<To>>> ,
                    Mapper<From, To> {

    @Override
    public SingleSource<List<To>> apply(List<From> data) throws Exception {
        return Flowable.fromIterable(data)
                .map(this::map)
                .toList();
    }
}
