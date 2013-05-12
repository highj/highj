package org.highj.data.functions;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.tuple.*;
import org.highj.typeclass2.arrow.ArrowChoice;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;
import java.util.function.Supplier;

public enum Fs {
    ;

    private static final F1 ID = a -> a;


    @SuppressWarnings("unchecked")
    public static <A> F1<A, A> id() {
        return (F1<A,A>) ID;
    }

    @SuppressWarnings("unchecked")
    public static <A, B> F1<A, B> narrow(_<__.µ<F1.µ, A>, B> function) {
        return (F1) function;
    }

    @SuppressWarnings("unchecked")
    public static <A, Super_B, B extends Super_B> F1<A, Super_B> contravariant(F1<A, B> function) {
        return (F1) function;
    }


    public static <A, B> F1<A, F1<B, A>> constant() {
        return a -> b -> a;
    }

    public static <A, B> F1<A, B> constant(final B b) {
        return a -> b;
    }

    public static <A, B> F1<A, B> constant(final Supplier<B> thunk) {
        return a -> thunk.get();
    }

    public static <A, B, C> F1<A, C> compose_(_<__.µ<F1.µ, B>, C> f, _<__.µ<F1.µ, A>, B> g) {
        return compose(narrow(f), narrow(g));
    }


    public static <A, B, C> F1<A, C> compose(final Function<? super B, ? extends C> f, final Function<? super A, ? extends B> g) {
        return a -> f.apply(g.apply(a));
    }

    public static <A, B, C, D> F1<A, D> compose_(_<__.µ<F1.µ, C>, D> f, _<__.µ<F1.µ, B>, C> g, _<__.µ<F1.µ, A>, B> h) {
        return compose(narrow(f), narrow(g), narrow(h));
    }

    public static <A, B, C, D> F1<A, D> compose(final Function<? super C, ? extends D> f, final Function<? super B, ? extends C> g, final Function<? super A, ? extends B> h) {
        return a -> f.apply(g.apply(h.apply(a)));
    }

    public static <A, B> F1<F1<A, B>, B> flip(final A a) {
        return fn -> fn.apply(a);
    }

    public static <A> Monoid<_<__.µ<F1.µ, A>, A>> endoMonoid() {
        return new Monoid<_<__.µ<F1.µ, A>, A>>() {
            @Override
            public _<__.µ<F1.µ, A>, A> identity() {
                return id();
            }

            @Override
            public _<__.µ<F1.µ, A>, A> dot(_<__.µ<F1.µ, A>, A> f, _<__.µ<F1.µ, A>, A> g) {
                return compose_(f, g);
            }
        };
    }

    public static <A, B> F1<A, F1<F1<A, B>, B>> flipApply() {
        return a -> fn -> fn.apply(a);
    }

    public static <R> Applicative<__.µ<F1.µ, R>> applicative() {
        return new Applicative<__.µ<F1.µ, R>>() {

            @Override
            public <A> _<__.µ<F1.µ, R>, A> pure(A a) {
                //pure = const
                return constant(a);
            }

            @Override
            public <A, B> _<__.µ<F1.µ, R>, B> ap(_<__.µ<F1.µ, R>, Function<A, B>> fn, _<__.µ<F1.µ, R>, A> nestedA) {
                //(<*>) f g x = f x (g x)
                final F1<R, Function<A, B>> fRAB = narrow(fn);
                final F1<R, A> fRA = narrow(nestedA);
                return (F1<R, B>) r -> fRAB.apply(r).apply(fRA.apply(r));
            }

            @Override
            public <A, B> _<__.µ<F1.µ, R>, B> map(Function<A, B> fAB, _<__.µ<F1.µ, R>, A> nestedA) {
                return compose_((F1<A,B>) fAB::apply, nestedA);
            }
        };
    }

    public static <A, B, C> F1<A, T2<B, C>> fanout(_<__.µ<F1.µ, A>, B> fab, _<__.µ<F1.µ, A>, C> fac) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        return a -> Tuple.of(fnab.apply(a), fnac.apply(a));
    }

    public static <A, B, C, D> F1<A, T3<B, C, D>> fanout(_<__.µ<F1.µ, A>, B> fab, _<__.µ<F1.µ, A>, C> fac, _<__.µ<F1.µ, A>, D> fad) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        final F1<A, D> fnad = narrow(fad);
        return a -> Tuple.of(fnab.apply(a), fnac.apply(a), fnad.apply(a));
    }

    public static <A, B, C, D, E> F1<A, T4<B, C, D, E>> fanout(_<__.µ<F1.µ, A>, B> fab, _<__.µ<F1.µ, A>, C> fac, _<__.µ<F1.µ, A>, D> fad, _<__.µ<F1.µ, A>, E> fae) {
        final F1<A, B> fnab = narrow(fab);
        final F1<A, C> fnac = narrow(fac);
        final F1<A, D> fnad = narrow(fad);
        final F1<A, E> fnae = narrow(fae);
        return a -> Tuple.of(fnab.apply(a), fnac.apply(a), fnad.apply(a), fnae.apply(a));
    }


    public static final ArrowChoice<F1.µ> arrow = new ArrowChoice<F1.µ>() {
        @Override
        public <A, B> __<F1.µ, A, B> arr(Function<A, B> fn) {
            return (F1<A, B>) fn::apply;
        }

        @Override
        public <A, B, C> __<F1.µ, T2<A, C>, T2<B, C>> first(__<F1.µ, A, B> arrow) {
            final F1<A, B> fn = narrow(arrow);
            return (F1<T2<A, C>, T2<B, C>>) pair -> Tuple.of(fn.apply(pair._1()), pair._2());
        }

        @Override
        public <A, B, C> __<F1.µ, T2<C, A>, T2<C, B>> second(__<F1.µ, A, B> arrow) {
            final F1<A, B> fn = narrow(arrow);
            return (F1<T2<C, A>, T2<C, B>>) pair -> Tuple.of(pair._1(), fn.apply(pair._2()));
        }

        @Override
        public <A, B, C> __<F1.µ, A, T2<B, C>> fanout(__<F1.µ, A, B> arr1, __<F1.µ, A, C> arr2) {
            return Fs.fanout(arr1, arr2);
        }

        @Override
        public <A> __<F1.µ, A, A> identity() {
            return id();
        }

        @Override
        public <A, B, C> __<F1.µ, A, C> dot(__<F1.µ, B, C> bc, __<F1.µ, A, B> ab) {
            return compose_(bc, ab);
        }

        @Override
        public <B, C, D> __<F1.µ, Either<B, D>, Either<C, D>> left(__<F1.µ, B, C> arrow) {
            return merge(arrow, Fs.<D>id());
        }

        @Override
        public <B, C, D> __<F1.µ, Either<D, B>, Either<D, C>> right(__<F1.µ, B, C> arrow) {
            return merge(Fs.<D>id(), arrow);
        }

        @Override
        public <B, C, BB, CC> __<F1.µ, Either<B, BB>, Either<C, CC>> merge(__<F1.µ, B, C> f, __<F1.µ, BB, CC> g) {
            return null;
        }

        @Override
        public <B, C, D> __<F1.µ, Either<B, C>, D> fanin(__<F1.µ, B, D> f, __<F1.µ, C, D> g) {
            Function<B,D> funF = narrow(f);
            Function<C,D> funG = narrow(g);
            return (F1<Either<B, C>, D>) e -> e.either(funF, funG);
        }
    };

    public static <A> Supplier<A> fromF1(final F1<T0, A> fn) {
        return () -> fn.apply(T0.unit);
    }

    public static <A, B> Supplier<B> lazy(final F1<A, B> fn, final A a) {
        return () -> fn.apply(a);
    }

}
