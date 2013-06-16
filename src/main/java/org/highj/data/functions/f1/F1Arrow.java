package org.highj.data.functions.f1;

import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.functions.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowApply;
import org.highj.typeclass2.arrow.ArrowChoice;

import java.util.function.Function;

public class F1Arrow implements ArrowChoice<F1.µ>, ArrowApply<F1.µ> {
    @Override
    public <A, B> F1<A, B> arr(Function<A, B> fn) {
        return fn::apply;
    }

    @Override
    public <A, B, C> F1<T2<A, C>, T2<B, C>> first(__<F1.µ, A, B> arrow) {
        final F1<A, B> fn = F1.narrow(arrow);
        return pair -> T2.of(fn.apply(pair._1()), pair._2());
    }

    @Override
    public <A, B, C> F1<T2<C, A>, T2<C, B>> second(__<F1.µ, A, B> arrow) {
        final F1<A, B> fn = F1.narrow(arrow);
        return pair -> T2.of(pair._1(), fn.apply(pair._2()));
    }

    @Override
    public <A, B, C> F1<A, T2<B, C>> fanout(__<F1.µ, A, B> arr1, __<F1.µ, A, C> arr2) {
        return F1.fanout(arr1, arr2);
    }

    @Override
    public <A> F1<A, A> identity() {
        return F1.id();
    }

    @Override
    public <A, B, C> F1<A, C> dot(__<F1.µ, B, C> bc, __<F1.µ, A, B> ab) {
        return F1.compose(F1.narrow(bc), F1.narrow(ab));
    }

    @Override
    public <B, C, D> F1<Either<B, D>, Either<C, D>> left(__<F1.µ, B, C> arrow) {
        return merge(arrow, F1.<D>id());
    }

    @Override
    public <B, C, D> F1<Either<D, B>, Either<D, C>> right(__<F1.µ, B, C> arrow) {
        return merge(F1.<D>id(), arrow);
    }

    @Override
    public <B, C, BB, CC> F1<Either<B, BB>, Either<C, CC>> merge(__<F1.µ, B, C> f, __<F1.µ, BB, CC> g) {
        return F1.narrow(ArrowChoice.super.merge(f,g));
    }

    @Override
    public <B, C, D> F1<Either<B, C>, D> fanin(__<F1.µ, B, D> f, __<F1.µ, C, D> g) {
        Function<B,D> funF = F1.narrow(f);
        Function<C,D> funG = F1.narrow(g);
        return e -> e.either(funF, funG);
    }

    @Override
    public <A, B> F1<T2<__<F1.µ, A, B>,A>, B> app() {
        return pair -> F1.narrow(pair._1()).apply(pair._2());
    }
}
