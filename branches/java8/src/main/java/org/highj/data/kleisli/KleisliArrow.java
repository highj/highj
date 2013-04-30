package org.highj.data.kleisli;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.Tuple;
import org.highj.function.Functions;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.ArrowChoice;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class KleisliArrow<M> implements Arrow<_<Kleisli.µ, M>>, ArrowChoice<_<Kleisli.µ, M>> {

    private final Monad<M> monad;

    public KleisliArrow(Monad<M> monad) {
        this.monad = monad;
    }

    @Override
    public <A, B> __<_<Kleisli.µ, M>, A, B> arr(Function<A, B> fn) {
        //arr f = Kleisli (return . f)
        return new Kleisli<M,A,B>(a -> monad.pure(fn.apply(a)));
    }

    @Override
    public <A, B, C> __<_<Kleisli.µ, M>, T2<A, C>, T2<B, C>> first(__<_<Kleisli.µ, M>, A, B> kleisli) {
        //first (Kleisli f) = Kleisli (\ ~(b,d) -> f b >>= \c -> return (c,d))
        Function<A, _<M, B>> f = Kleisli.narrow(kleisli).get();
        return new Kleisli<M,T2<A,C>,T2<B,C>>(bd -> monad.bind(f.apply(bd._1()), c -> monad.pure(Tuple.of(c, bd._2()))));
    }

    @Override
    public <A, B, C> __<_<Kleisli.µ, M>, T2<C, A>, T2<C, B>> second(__<_<Kleisli.µ, M>, A, B> kleisli) {
        //second (Kleisli f) = Kleisli (\ ~(d,b) -> f b >>= \c -> return (d,c))
        Function<A, _<M, B>> f = Kleisli.narrow(kleisli).get();
        return new Kleisli<M, T2<C, A>, T2<C, B>>(db -> monad.bind(f.apply(db._2()), c -> monad.pure(Tuple.of(db._1(), c))));
    }

    @Override
    public <A> __<_<Kleisli.µ, M>, A, A> identity() {
        //id = Kleisli return
        return new Kleisli<M, A, A>(monad::<A>pure);
    }

    @Override
    public <A, B, C> __<_<Kleisli.µ, M>, A, C> dot(__<_<Kleisli.µ, M>, B, C> kleisliF, __<_<Kleisli.µ, M>, A, B> kleisliG) {
        //(Kleisli f) . (Kleisli g) = Kleisli (\b -> g b >>= f)
        Function<B, _<M, C>> f = Kleisli.narrow(kleisliF).get();
        Function<A, _<M, B>> g = Kleisli.narrow(kleisliG).get();
        return new Kleisli<M,A,C>(b -> monad.bind(g.apply(b), f));
    }

    @Override
    public <B, C, D> __<_<Kleisli.µ, M>, Either<B, D>, Either<C, D>> left(__<_<Kleisli.µ, M>, B, C> arrow) {
        // left f = f +++ arr id
        return merge(arrow, arr(Functions.<D>id()));
    }

    @Override
    public <B, C, D> __<_<Kleisli.µ, M>, Either<D, B>, Either<D, C>> right(__<_<Kleisli.µ, M>, B, C> arrow) {
        // right f = arr id +++ f
        return merge(arr(Functions.<D>id()), arrow);
    }

    @Override
    public <B, C, BB, CC> __<_<Kleisli.µ, M>, Either<B, BB>, Either<C, CC>> merge(__<_<Kleisli.µ, M>, B, C> f, __<_<Kleisli.µ, M>, BB, CC> g) {
        // f +++ g = (f >>> arr LeftLazy) ||| (g >>> arr RightLazy)
        __<_<Kleisli.µ, M>, B, Either<C,CC>> kleisliF = then(f, this.<C,Either<C,CC>>arr(Either::Left));
        __<_<Kleisli.µ, M>, BB, Either<C,CC>> kleisliG = then(g, this.<CC,Either<C,CC>>arr(Either::Right));
        return fanin(kleisliF, kleisliG);
    }

    @Override
    public <B, C, D> __<_<Kleisli.µ, M>, Either<B, C>, D> fanin(__<_<Kleisli.µ, M>, B, D> kleisliF, __<_<Kleisli.µ, M>, C, D> kleisliG) {
        //Kleisli f ||| Kleisli g = Kleisli (either f g)
        Function<B, _<M, D>> f = Kleisli.narrow(kleisliF).get();
        Function<C, _<M, D>> g = Kleisli.narrow(kleisliG).get();
        return new Kleisli<M, Either<B, C>, D>(e -> e.either(f, g));
    }
}
