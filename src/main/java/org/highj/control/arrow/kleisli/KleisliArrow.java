package org.highj.control.arrow.kleisli;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.control.arrow.Kleisli;
import org.highj.data.Either;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass2.arrow.ArrowApply;
import org.highj.typeclass2.arrow.ArrowChoice;

import java.util.function.Function;

import static org.highj.Hkt.asKleisli;

public interface KleisliArrow<M> extends ArrowChoice<__<Kleisli.µ, M>>, ArrowApply<__<Kleisli.µ, M>> {

    Monad<M> getM();

    @Override
    default <A, B> Kleisli<M, A, B> arr(Function<A, B> fn) {
        return new Kleisli<>(a -> getM().pure(fn.apply(a)));
    }

    @Override
    default <A, B, C> Kleisli<M, T2<A, C>, T2<B, C>> first(__2<__<Kleisli.µ, M>, A, B> kleisli) {
        Function<A, __<M, B>> f = asKleisli(kleisli);
        //don't use diamond syntax here, gives compiler error in b92
        return new Kleisli<M, T2<A, C>, T2<B, C>>(bd -> getM().bind(f.apply(bd._1()), c -> getM().pure(T2.of(c, bd._2()))));
    }

    @Override
    default <A, B, C> Kleisli<M, T2<C, A>, T2<C, B>> second(__2<__<Kleisli.µ, M>, A, B> kleisli) {
        Function<A, __<M, B>> f = asKleisli(kleisli);
        //don't use diamond syntax here, gives compiler error in b92
        return new Kleisli<M, T2<C, A>, T2<C, B>>(db -> getM().bind(f.apply(db._2()), c -> getM().pure(T2.of(db._1(), c))));
    }

    @Override
    default <A> Kleisli<M, A, A> identity() {
        return new Kleisli<M, A, A>(getM()::<A>pure);
    }

    @Override
    default <A, B, C> Kleisli<M, A, C> dot(__2<__<Kleisli.µ, M>, B, C> kleisliF, __2<__<Kleisli.µ, M>, A, B> kleisliG) {
        Function<B, __<M, C>> f = asKleisli(kleisliF);
        Function<A, __<M, B>> g = asKleisli(kleisliG);
        return new Kleisli<>(b -> getM().bind(g.apply(b), f));
    }

    @Override
    default <B, C, D> Kleisli<M, Either<B, D>, Either<C, D>> left(__2<__<Kleisli.µ, M>, B, C> arrow) {
        return merge(arrow, arr(Function.<D>identity()));
    }

    @Override
    default <B, C, D> Kleisli<M, Either<D, B>, Either<D, C>> right(__2<__<Kleisli.µ, M>, B, C> arrow) {
        return merge(arr(Function.<D>identity()), arrow);
    }

    @Override
    default <B, C, BB, CC> Kleisli<M, Either<B, BB>, Either<C, CC>> merge(__2<__<Kleisli.µ, M>, B, C> f, __2<__<Kleisli.µ, M>, BB, CC> g) {
        __2<__<Kleisli.µ, M>, B, Either<C, CC>> kleisliF = then(f, this.<C, Either<C, CC>>arr(Either::Left));
        __2<__<Kleisli.µ, M>, BB, Either<C, CC>> kleisliG = then(g, this.<CC, Either<C, CC>>arr(Either::Right));
        return fanin(kleisliF, kleisliG);
    }

    @Override
    default <B, C, D> Kleisli<M, Either<B, C>, D> fanin(__2<__<Kleisli.µ, M>, B, D> kleisliF, __2<__<Kleisli.µ, M>, C, D> kleisliG) {
        Function<B, __<M, D>> f = asKleisli(kleisliF);
        Function<C, __<M, D>> g = asKleisli(kleisliG);
        return new Kleisli<>(e -> e.either(f, g));
    }

    @Override
    default <A, B> Kleisli<M, T2<__2<__<Kleisli.µ, M>, A, B>, A>, B> app() {
        return new Kleisli<>(pair -> asKleisli(pair._1()).apply(pair._2()));
    }

}
