package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.collection.Either;

public interface ArrowChoice<A> extends Arrow<A> {
    public <B, C, D> __<A, Either<B, D>, Either<C, D>> left(__<A, B, C> arrow);

    //The default definition should be overridden with a more efficient version if possible
    public default <B, C, D> __<A, Either<D, B>, Either<D, C>> right(__<A, B, C> arrow) {
        __<A, Either<D, B>, Either<B, D>> swapDB = arr(Either<D, B>::swap);
        __<A, Either<B, D>, Either<C, D>> leftArrow = left(arrow);
        __<A, Either<C, D>, Either<D, C>> swapCD = arr(Either<C, D>::swap);
        return then(swapDB, leftArrow, swapCD);
    }

    // (+++) :: a b c -> a b' c' -> a (Either b b') (Either c c')
    public default <B, C, BB, CC> __<A, Either<B, BB>, Either<C, CC>> merge(__<A, B, C> f, __<A, BB, CC> g) {
        //  f +++ g = left f >>> right g
        __<A, Either<B, BB>, Either<C, BB>> leftF = left(f);
        __<A, Either<C, BB>, Either<C, CC>> rightG = right(g);
        return then(leftF, rightG);
    }

    // (|||) :: a b d -> a c d -> a (Either b c) d
    public default <B, C, D> __<A, Either<B, C>, D> fanin(__<A, B, D> f, __<A, C, D> g) {
        // f ||| g = f +++ g >>> arr untag
        __<A, Either<B, C>, Either<D, D>> fg = merge(f, g);
        __<A, Either<D, D>, D> untag = arr(Either::unify);
        return then(fg, untag);
    }
}

