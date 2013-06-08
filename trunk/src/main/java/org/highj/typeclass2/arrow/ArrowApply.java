package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.tuple.T2;

public interface ArrowApply<µ> extends Arrow<µ> {
    //app :: a (a b c, b) c
    public <A, B> __<µ, T2<__<µ, A, B>, A>, B> app();
}
