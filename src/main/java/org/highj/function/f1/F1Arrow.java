package org.highj.function.f1;

import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowApply;
import org.highj.typeclass2.arrow.ArrowChoice;
import org.highj.typeclass2.arrow.ArrowLoop;

import java.util.function.Function;

import static org.highj.Hkt.asF1;

public interface F1Arrow extends ArrowChoice<F1.µ>, ArrowApply<F1.µ>, ArrowLoop<F1.µ> {
    @Override
    default <A, B> F1<A, B> arr(Function<A, B> fn) {
        return fn::apply;
    }

    @Override
    default <A, B, C> F1<T2<A, C>, T2<B, C>> first(__2<F1.µ, A, B> arrow) {
        final F1<A, B> fn = asF1(arrow);
        return pair -> T2.of(fn.apply(pair._1()), pair._2());
    }

    @Override
    default <A, B, C> F1<T2<C, A>, T2<C, B>> second(__2<F1.µ, A, B> arrow) {
        final F1<A, B> fn = asF1(arrow);
        return pair -> T2.of(pair._1(), fn.apply(pair._2()));
    }

    @Override
    default <A, B, C> F1<A, T2<B, C>> fanout(__2<F1.µ, A, B> arr1, __2<F1.µ, A, C> arr2) {
        return F1.fanout(arr1, arr2);
    }

    @Override
    default <A> F1<A, A> identity() {
        return F1.id();
    }

    @Override
    default <A, B, C> F1<A, C> dot(__2<F1.µ, B, C> bc, __2<F1.µ, A, B> ab) {
        F1<B, C> bcFn = asF1(bc);
        F1<A, B> abFn = asF1(ab);
        return F1.compose(bcFn, abFn);
    }

    @Override
    default <B, C, D> F1<Either<B, D>, Either<C, D>> left(__2<F1.µ, B, C> arrow) {
        return merge(arrow, F1.<D>id());
    }

    @Override
    default <B, C, D> F1<Either<D, B>, Either<D, C>> right(__2<F1.µ, B, C> arrow) {
        return merge(F1.<D>id(), arrow);
    }

    @Override
    default <B, C, BB, CC> F1<Either<B, BB>, Either<C, CC>> merge(__2<F1.µ, B, C> f, __2<F1.µ, BB, CC> g) {
        return asF1(ArrowChoice.super.merge(f,g));
    }

    @Override
    default <B, C, D> F1<Either<B, C>, D> fanin(__2<F1.µ, B, D> f, __2<F1.µ, C, D> g) {
        Function<B,D> funF = asF1(f);
        Function<C,D> funG = asF1(g);
        return e -> e.either(funF, funG);
    }

    @Override
    default <A, B> F1<T2<__2<F1.µ, A, B>,A>, B> app() {
        return pair -> asF1(pair._1()).apply(pair._2());
    }

    @Override
    default <B, C, D> F1<B, C> loop(__2<F1.µ, T2<B, D>, T2<C, D>> arrow) {
        F1<T2<B,D>,T2<C,D>> fn = asF1(arrow);
        class Util {
            T2<B,D> input(B b) {
                return T2.of$(() -> b, () -> fn.apply(input(b))._2());
            }
        }
        final Util util = new Util();
        return (B b) -> fn.apply(util.input(b))._1();
    }
}
