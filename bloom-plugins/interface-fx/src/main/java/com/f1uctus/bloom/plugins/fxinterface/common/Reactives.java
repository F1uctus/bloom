package com.f1uctus.bloom.plugins.fxinterface.common;

import io.reactivex.rxjava3.core.Observable;
import org.reactivestreams.FlowAdapters;

import java.util.concurrent.Flow;

public class Reactives {
    public static <T> Observable<T> toObservable(Flow.Publisher<T> publisher) {
        return Observable.fromPublisher(FlowAdapters.toPublisher(publisher));
    }
}
