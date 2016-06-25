package org.highj.function;

import org.derive4j.hkt.__2;

public interface NF2<F,G> extends __2<NF2.µ,F,G> {
    class µ {}
    
    static <F,G> NF2<F,G> narrow(__2<NF2.µ,F,G> a) {
        return (NF2<F,G>)a;
    }
    
    <B,C> __2<G,B,C> apply(__2<F,B,C> a);
    
    static <F> NF2<F,F> identity() {
        return new NF2<F,F>() {
            @Override
            public <B, C> __2<F, B, C> apply(__2<F, B, C> a) {
                return a;
            }
        };
    }
}
