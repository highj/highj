package org.highj.data.kleisli.kleisli;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.collection.Either;
import org.highj.data.kleisli.Kleisli;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass2.arrow.ArrowApply;
import org.highj.typeclass2.arrow.ArrowChoice;

import java.util.function.Function;

import static org.highj.data.kleisli.Kleisli.narrow;

public class KleisliArrow<M> implements ArrowChoice<___.µ<Kleisli.µ, M>>, ArrowApply<___.µ<Kleisli.µ, M>> {

    private final Monad<M> monad;

    public KleisliArrow(Monad<M> monad) {
        this.monad = monad;
    }

    @Override
    public <A, B> Kleisli<M, A, B> arr(Function<A, B> fn) {
        //arr f = Kleisli (return . f)
        return new Kleisli<>(a -> monad.pure(fn.apply(a)));
    }

    @Override
    public <A, B, C> Kleisli<M, T2<A, C>, T2<B, C>> first(__<___.µ<Kleisli.µ, M>, A, B> kleisli) {
        Function<A, _<M, B>> f = Kleisli.narrow(kleisli);
        //don't use diamond syntax here, gives compiler error in b92
        return new Kleisli<M, T2<A, C>, T2<B, C>>(bd -> monad.bind(f.apply(bd._1()), c -> monad.pure(T2.of(c, bd._2()))));
    }

    @Override
    public <A, B, C> Kleisli<M, T2<C, A>, T2<C, B>> second(__<___.µ<Kleisli.µ, M>, A, B> kleisli) {
        //second (Kleisli f) = Kleisli (\ ~(d,b) -> f b >>= \c -> return (d,c))
        Function<A, _<M, B>> f = narrow(kleisli);
        //don't use diamond syntax here, gives compiler error in b92
        return new Kleisli<M, T2<C, A>, T2<C, B>>(db -> monad.bind(f.apply(db._2()), c -> monad.pure(T2.of(db._1(), c))));
    }

    @Override
    public <A> Kleisli<M, A, A> identity() {
        //id = Kleisli return
        return new Kleisli<M, A, A>(monad::<A>pure);
    }

    @Override
    public <A, B, C> Kleisli<M, A, C> dot(__<___.µ<Kleisli.µ, M>, B, C> kleisliF, __<___.µ<Kleisli.µ, M>, A, B> kleisliG) {
        //(Kleisli f) . (Kleisli g) = Kleisli (\b -> g b >>= f)
        Function<B, _<M, C>> f = narrow(kleisliF);
        Function<A, _<M, B>> g = narrow(kleisliG);
        return new Kleisli<>(b -> monad.bind(g.apply(b), f));
    }

    @Override
    public <B, C, D> Kleisli<M, Either<B, D>, Either<C, D>> left(__<___.µ<Kleisli.µ, M>, B, C> arrow) {
        // left f = f +++ arr id
        return merge(arrow, arr(Function.<D>identity()));
    }

    @Override
    public <B, C, D> Kleisli<M, Either<D, B>, Either<D, C>> right(__<___.µ<Kleisli.µ, M>, B, C> arrow) {
        // right f = arr id +++ f
        return merge(arr(Function.<D>identity()), arrow);
    }

    @Override
    public <B, C, BB, CC> Kleisli<M, Either<B, BB>, Either<C, CC>> merge(__<___.µ<Kleisli.µ, M>, B, C> f, __<___.µ<Kleisli.µ, M>, BB, CC> g) {
        //f +++ g = (f >>> arr lazyLeft) ||| (g >>> arr lazyRight)
        __<___.µ<Kleisli.µ, M>, B, Either<C, CC>> kleisliF = then(f, this.<C, Either<C, CC>>arr(Either::newLeft));
        __<___.µ<Kleisli.µ, M>, BB, Either<C, CC>> kleisliG = then(g, this.<CC, Either<C, CC>>arr(Either::newRight));
        return fanin(kleisliF, kleisliG);
    }

    @Override
    public <B, C, D> Kleisli<M, Either<B, C>, D> fanin(__<___.µ<Kleisli.µ, M>, B, D> kleisliF, __<___.µ<Kleisli.µ, M>, C, D> kleisliG) {
        //Kleisli f ||| Kleisli g = Kleisli (either f g)
        Function<B, _<M, D>> f = narrow(kleisliF);
        Function<C, _<M, D>> g = narrow(kleisliG);
        return new Kleisli<>(e -> e.either(f, g));
    }

    @Override
    public <A, B> Kleisli<M, T2<__<___.µ<Kleisli.µ, M>, A, B>, A>, B> app() {
        // app = Kleisli (\(Kleisli f, x) -> f x)
        return new Kleisli<>(pair -> Kleisli.narrow(pair._1()).apply(pair._2()));
    }

}
