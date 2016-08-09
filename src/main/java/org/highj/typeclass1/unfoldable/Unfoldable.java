package org.highj.typeclass1.unfoldable;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.function.F1;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

public interface Unfoldable<T> {

    <A, B> __<T,A> unfoldr(Function<B, Maybe<T2<A,B>>> fn, B b);

    default <A> __<T,A> replicate(int n, A a) {
        return unfoldr(i -> i <= 0
                ? Maybe.Nothing()
                : Maybe.Just(T2.of(a, i-1)), n);
    }

    default <M,A> __<M, __<T,A>> replicateA(
            Applicative<M> applicative,
            Traversable<T> traversable, int n, __<M,A> ma) {
        return traversable.sequenceA(applicative, replicate(n, ma));
    }

    default <A> __<T,A> none() {
        return unfoldr(F1.constant(Maybe.Nothing()), T0.unit);
    }

    default <A> __<T,A> singleton(A a) {
        return replicate(1, a);
    }
}
