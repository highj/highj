package org.highj.do_;

import org.derive4j.hkt.TypeEq;
import org.derive4j.hkt.__;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.function.F3;
import org.highj.function.F4;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Do_<M,A> {
    private final DoIndexed<M, T0, A> _impl;

    private Do_(DoIndexed<M, T0, A> impl) {
        this._impl = impl;
    }

    private static <M,A> Do_<M,A> create(DoIndexed<M, T0, A> impl) {
        return new Do_<>(impl);
    }

    private DoIndexed<M,T0,A> impl() {
        return _impl;
    }

    public <S,Y> __<M,Y> runWithResult(TypeEq<A,T2<S,Y>> typeEq, MonadRec<M> mMonadRec) {
        return mMonadRec.map(
            (A a) -> typeEq.coerce(a)._2(),
            DoIndexed.run(mMonadRec, impl())
        );
    }

    public __<M,T0> runNoResult(MonadRec<M> mMonadRec) {
        return mMonadRec.map(
            unused -> T0.of(),
            DoIndexed.run(mMonadRec, impl())
        );
    }

    public <S,Y> __<M,Y> runWithResultNoTailRec(TypeEq<A,T2<S,Y>> typeEq, Monad<M> mMonad) {
        return mMonad.map(
            (A a) -> typeEq.coerce(a)._2(),
            DoIndexed.runNoTailRec(mMonad, impl())
        );
    }

    public __<M,T0> runNoResultNoTailRec(Monad<M> mMonad) {
        return mMonad.map(
            unused -> T0.of(),
            DoIndexed.runNoTailRec(mMonad, impl())
        );
    }

    public static <M> Do_<M,T0> do_() {
        return create(DoIndexed.<M>do_());
    }

    public <B> Do_<M,A> __(__<M,B> m) {
        return create(impl().__(m));
    }

    public <B> Do_<M, T2<A,B>> push(B a) {
        return create(impl().push(a));
    }

    public <B> Do_<M, T2<A,B>> pushM(__<M,B> m) {
        return create(impl().pushM(m));
    }

    public <B> Do_<M, B> mapAll(Function<A,B> f) {
        return create(impl().map(f));
    }

    public <B> Do_<M,B> bindAll(Function<A,__<M,B>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> f.apply(a)));
    }

    public <S,X> Do_<M,T2<T2<S,X>,X>> dup(TypeEq<A,T2<S,X>> typeEq) {
        return mapAll((A a) -> {
            T2<S,X> x = typeEq.coerce(a);
            return T2.of(x, x._2());
        });
    }

    public <S,X,Y> Do_<M,T2<T2<S,Y>,X>> swap(TypeEq<A,T2<T2<S,X>,Y>> typeEq) {
        return mapAll((A a) -> {
            T2<T2<S,X>,Y> x = typeEq.coerce(a);
            return T2.of(T2.of(x._1()._1(), x._2()), x._1()._2());
        });
    }

    public <S,X,Y> Do_<M,T2<S,Y>> map1(TypeEq<A,T2<S,X>> typeEq, Function<X,Y> f) {
        return create(impl().map((A a) -> {
            T2<S,X> x = typeEq.coerce(a);
            return T2.of(x._1(), f.apply(x._2()));
        }));
    }

    public <S,X1,X2,Y> Do_<M,T2<S,Y>> map2(TypeEq<A,T2<T2<S,X1>,X2>> typeEq, BiFunction<X1,X2,Y> f) {
        return create(impl().map((A a) -> {
            T2<T2<S,X1>,X2> x = typeEq.coerce(a);
            return T2.of(x._1()._1(), f.apply(x._1()._2(), x._2()));
        }));
    }

    public <S,X1,X2,X3,Y> Do_<M,T2<S,Y>> map3(TypeEq<A,T2<T2<T2<S,X1>,X2>,X3>> typeEq, F3<X1,X2,X3,Y> f) {
        return create(impl().map((A a) -> {
            T2<T2<T2<S,X1>,X2>,X3> x = typeEq.coerce(a);
            return T2.of(x._1()._1()._1(), f.apply(x._1()._1()._2(), x._1()._2(), x._2()));
        }));
    }

    public <S,X1,X2,X3,X4,Y> Do_<M,T2<S,Y>> map4(TypeEq<A,T2<T2<T2<T2<S,X1>,X2>,X3>,X4>> typeEq, F4<X1,X2,X3,X4,Y> f) {
        return create(impl().map((A a) -> {
            T2<T2<T2<T2<S,X1>,X2>,X3>,X4> x = typeEq.coerce(a);
            return T2.of(x._1()._1()._1()._1(), f.apply(x._1()._1()._1()._2(), x._1()._1()._2(), x._1()._2(), x._2()));
        }));
    }

    public <S,X,Y> Do_<M,S> bind1(TypeEq<A,T2<S,X>> typeEq, Function<X,__<M,Y>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> {
            T2<S,X> x = typeEq.coerce(a);
            return mMonad.rightSeq(f.apply(x._2()), mMonad.pure(x._1()));
        }));
    }

    public <S,X1,X2,Y> Do_<M,S> bind2(TypeEq<A,T2<T2<S,X1>,X2>> typeEq, BiFunction<X1,X2,__<M,Y>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> {
            T2<T2<S,X1>,X2> x = typeEq.coerce(a);
            return mMonad.rightSeq(f.apply(x._1()._2(), x._2()), mMonad.pure(x._1()._1()));
        }));
    }

    public <S,X1,X2,X3,Y> Do_<M,S> bind3(TypeEq<A,T2<T2<T2<S,X1>,X2>,X3>> typeEq, F3<X1,X2,X3,__<M,Y>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> {
            T2<T2<T2<S,X1>,X2>,X3> x = typeEq.coerce(a);
            return mMonad.rightSeq(f.apply(x._1()._1()._2(), x._1()._2(), x._2()), mMonad.pure(x._1()._1()._1()));
        }));
    }

    public <S,X1,X2,X3,X4,Y> Do_<M,S> bind4(TypeEq<A,T2<T2<T2<T2<S,X1>,X2>,X3>,X4>> typeEq, F4<X1,X2,X3,X4,__<M,Y>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> {
            T2<T2<T2<T2<S,X1>,X2>,X3>,X4> x = typeEq.coerce(a);
            return mMonad.map(unused -> x._1()._1()._1()._1(), f.apply(x._1()._1()._1()._2(), x._1()._1()._2(), x._1()._2(), x._2()));
        }));
    }

    public <S,X,Y> Do_<M,T2<S,Y>> pushBind1(TypeEq<A,T2<S,X>> typeEq, Function<X,__<M,Y>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> {
            T2<S,X> x = typeEq.coerce(a);
            return mMonad.map((Y y) -> T2.of(x._1(), y), f.apply(x._2()));
        }));
    }

    public <S,X1,X2,Y> Do_<M,T2<S,Y>> pushBind2(TypeEq<A,T2<T2<S,X1>,X2>> typeEq, BiFunction<X1,X2,__<M,Y>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> {
            T2<T2<S,X1>,X2> x = typeEq.coerce(a);
            return mMonad.map((Y y) -> T2.of(x._1()._1(), y), f.apply(x._1()._2(), x._2()));
        }));
    }

    public <S,X1,X2,X3,Y> Do_<M,T2<S,Y>> pushBind3(TypeEq<A,T2<T2<T2<S,X1>,X2>,X3>> typeEq, F3<X1,X2,X3,__<M,Y>> f) {
        return create(impl().bind((Monad<M> mMonad, A a) -> {
            T2<T2<T2<S,X1>,X2>,X3> x = typeEq.coerce(a);
            return mMonad.map((Y y) -> T2.of(x._1()._1()._1(), y), f.apply(x._1()._1()._2(), x._1()._2(), x._2()));
        }));
    }
}
