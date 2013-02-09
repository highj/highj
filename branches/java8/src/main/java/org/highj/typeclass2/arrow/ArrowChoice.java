package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.data.collection.Either;

public interface ArrowChoice<µ> extends Arrow<µ> {
    public <B, C, D> __<µ, Either<B, D>, Either<C, D>> left(__<µ, B, C> arrow);

    //The default definition should be overridden with a more efficient version if possible
    public default <B, C, D> __<µ, Either<D, B>, Either<D, C>> right(__<µ, B, C> arrow) {
        __<µ, Either<D, B>, Either<B, D>> swapDB = arr((Either<D, B> e) -> e.swap());
        __<µ, Either<B, D>, Either<C, D>> leftArrow = left(arrow);
        __<µ, Either<C, D>, Either<D, C>> swapCD = arr((Either<C, D> e) -> e.swap());
        return then(swapDB, leftArrow, swapCD);
    }

    // (+++) :: a b c -> a b' c' -> a (Either b b') (Either c c')
    public default <B, C, BB, CC> __<µ, Either<B, BB>, Either<C, CC>> merge(__<µ, B, C> f, __<µ, BB, CC> g) {
        //  f +++ g = left f >>> right g
        __<µ, Either<B, BB>, Either<C, BB>> leftF = left(f);
        __<µ, Either<C, BB>, Either<C, CC>> rightG = right(g);
        return then(leftF, rightG);
    }

    // (|||) :: a b d -> a c d -> a (Either b c) d
    public default <B, C, D> __<µ, Either<B, C>, D> fanin(__<µ, B, D> f, __<µ, C, D> g) {
        // f ||| g = f +++ g >>> arr untag
        __<µ, Either<B, C>, Either<D, D>> fg = merge(f, g);
        __<µ, Either<D, D>, D> untag = arr((Either<D,D> x) -> Either.unify(x));
        return then(fg, untag);
    }
}

