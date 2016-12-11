package org.highj.data.coroutine;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.coroutine.yieldf.YieldFFunctor;

public class YieldF<V,A> implements __2<YieldF.µ,V,A> {
    public static class µ {
    }
    
    private final V value;
    private final A next;
    
    private YieldF(V value, A next) {
        this.value = value;
        this.next = next;
    }
    
    public static <V,A> YieldF<V,A> yield(V value, A next) {
        return new YieldF<>(value, next);
    }
    
    public V value() {
        return value;
    }
    
    public A next() {
        return next;
    }
    
    public static <V> YieldFFunctor<V> functor() {
        return new YieldFFunctor<V>() {};
    }
}
