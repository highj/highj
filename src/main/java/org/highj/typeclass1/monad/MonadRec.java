package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.collection.Either;
import org.highj.data.functions.F3;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Monad allowing space-constant recursion TCO
 * <p>
 * Inspired by Phil Freeman's code for purescript:
 * https://pursuit.purescript.org/packages/purescript-tailrec/0.3.1/docs/Control.Monad.Rec.Class
 *
 * @param <M> the monadic type
 */
public interface MonadRec<M> extends Monad<M> {

    //tailRecM :: forall a b. (a -> m (Either a b)) -> a -> m b
    <A, B> _<M, B> tailRec(Function<A, _<M, Either<A, B>>> function, A startA);

    //tailRecM2 :: forall m a b c. (MonadRec m) => (a -> b -> m (Either { a :: a, b :: b } c)) -> a -> b -> m c
    default <A, B, C> _<M, C> tailRec2(BiFunction<A, B, _<M, Either<T2<A, B>, C>>> function, A startA, B startB) {
        //tailRecM2 f a b = tailRecM (\o -> f o.a o.b) { a: a, b: b }
        return tailRec(t2 -> function.apply(t2._1(), t2._2()), T2.of(startA, startB));
    }

    //tailRecM3 :: forall m a b c d. (MonadRec m) => (a -> b -> c -> m (Either { a :: a, b :: b, c :: c } d)) -> a -> b -> c -> m d
    default <A, B, C, D> _<M, D> tailRec3(F3<A, B, C, _<M, Either<T3<A, B, C>, D>>> function, A startA, B startB, C startC) {
        //tailRecM3 f a b c = tailRecM (\o -> f o.a o.b o.c) { a: a, b: b, c: c }
        return tailRec(t3 -> function.apply(t3._1(), t3._2(), t3._3()), T3.of(startA, startB, startC));
    }
}
