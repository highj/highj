package org.highj.typeclass2.bifoldable;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.structural.Const;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass2.bifunctor.Bifunctor;

import java.util.function.Function;

/**
 * Bitraversable identifies bifunctorial data structures whose elements can be traversed in order, performing Applicative
 * or Monad actions at each element, and collecting a result structure with the same shape. As opposed to Traversable data
 * structures, which have one variety of element on which an action can be performed, Bitraversable data structures have
 * two such varieties of elements.
 * <p>
 * Minimal definition: bitraverse OR bisequence
 *
 * @param <P>
 */
public interface Bitraversable<P> extends Bifoldable<P>, Bifunctor<P> {

    default <A, B, A1, B1, X> __<X, __2<P, A1, B1>> bitraverse(
            Applicative<X> applicative,
            Function<A, __<X, A1>> fn1,
            Function<B, __<X, B1>> fn2,
            __2<P, A, B> traversable) {
        return bisequence(applicative, bimap(fn1, fn2, traversable));
    }

    default <A, B, X> __<X, __2<P, A, B>> bisequence(Applicative<X> applicative, __2<P, __<X, A>, __<X, B>> traversable) {
        return bitraverse(applicative, xa -> xa, xb -> xb, traversable);
    }

    //implementation of bimap in terms of bitraverse
    default <A, A1, B, B1> __2<P, A1, B1> bimapDefault(Function<A, A1> fn1, Function<B, B1> fn2, __2<P, A, B> traversable) {
        return Hkt.asT1(
                bitraverse(
                        T1.monad,
                        a -> T1.of(fn1.apply(a)),
                        b -> T1.of(fn2.apply(b)),
                        traversable)).get();
    }

    //implementation of bifoldMap in terms of bitraverse
    default <M, A, B> M bifoldMapDefault(Monoid<M> monoid, Function<A, M> fn1, Function<B, M> fn2, __2<P, A, B> traversable) {
        return Hkt.asConst(
            bitraverse(
                    Const.applicative(monoid),
                    a -> Const.<M,T0>Const(fn1.apply(a)),
                    b -> Const.<M,T0>Const(fn2.apply(b)),
                    traversable)).get();
    }
}
