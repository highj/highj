package org.highj.typeclass.arrow;

import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.function.F1;
import org.highj.typeclass.monad.Applicative;

public interface Arrow<µ> extends Category<µ> {

    // arr  (Control.Arrow)
    public <A,B> __<µ,A,B> arr(F1<A,B> fn);

    // first  (Control.Arrow)
    public <A,B,C> __<µ, T2<A,C>, T2<B,C>> first(__<µ,A,B> arrow);

    // second  (Control.Arrow)
    public <A,B,C> __<µ, T2<C,A>, T2<C,B>> second(__<µ,A,B> arrow);

    // (***) (Control.Arrow)
    public <A,B,AA,BB> __<µ, T2<A,AA>, T2<B,BB>> split(__<µ,A,B> arr1, __<µ,AA,BB> arr2);

    // (&&&) (Control.Arrow)
    public <A,B,C> __<µ, A, T2<B,C>> fanout(__<µ,A,B> arr1, __<µ,A,C> arr2);

    //returnA (Control.Arrow)
    public <A> __<µ, A, A> returnA();

    // (^>>) (Control.Arrow)
    public <A,B,C> F1<__<µ, B, C>, __<µ, A, C>> precomposition(F1<A, B> fn);

    // (^<<) (Control.Arrow)
    public <A,B,C> F1<__<µ, A, B>, __<µ, A, C>> postcomposition(F1<B, C> fn);

    //the Applicative instance for a left-curried Arrow
    public <X> Applicative<__.µ<µ,X>> getApplicative();
}