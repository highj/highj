package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.tuple.T2;

public interface ArrowApply<A> extends Arrow<A> {
    //app :: a (a b c, b) c
    public <B, C> __<A, T2<__<A, B, C>, B>, C> app();
}
