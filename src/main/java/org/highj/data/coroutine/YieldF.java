package org.highj.data.coroutine;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.function.F1;

public class YieldF<V,A> implements __2<YieldF.µ,V,A> {
    public static class µ {
    }
    
    private final F1<V,A> k;
    
    private YieldF(F1<V,A> k) {
        this.k = k;
    }
    
    public static <V,A> YieldF<V,A> narrow(__<__<YieldF.µ,V>,A> a) {
        return (YieldF<V,A>)a;
    }
    
    public static <V,A> YieldF<V,A> yield(F1<V,A> k) {
        return new YieldF<>(k);
    }
    
    public F1<V,A> k() {
        return k;
    }
}
