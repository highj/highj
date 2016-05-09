package org.highj.typeclass2.arrow;

import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;

public interface ArrowApply<A> extends Arrow<A> {
    //app :: a (a b c, b) c
    public <B, C> __2<A, T2<__2<A, B, C>, B>, C> app();
}
