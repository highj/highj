package org.highj.do_;

import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Flavour;
import org.derive4j.Visibility;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.function.F2;
import org.highj.highjdata;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;
import java.util.function.Supplier;

@highjdata
abstract class DoIndexed<M,S1,S2> {

    public interface Cases<M,S1,S2,R> {
        R Effect(F2<Monad<M>,S1,__<M,S2>> effect);
        R Sequence(Sequence<M,S1,?,S2> sequence);
    }

    public static class Sequence<M,S1,S2,S3> {
        private final F2<Monad<M>,S1,__<M,S2>> _a;
        private final Supplier<DoIndexed<M,S2,S3>> _b;

        private Sequence(F2<Monad<M>,S1,__<M,S2>> a, Supplier<DoIndexed<M,S2,S3>> b) {
            this._a = a;
            this._b = b;
        }

        public static <M,S1,S2,S3> Sequence<M,S1,S2,S3> create(F2<Monad<M>,S1,__<M,S2>> a, Supplier<DoIndexed<M,S2,S3>> b) {
            return new Sequence<>(a, b);
        }

        public F2<Monad<M>, S1, __<M, S2>> a() {
            return _a;
        }

        public Supplier<DoIndexed<M, S2, S3>> b() {
            return _b;
        }
    }

    public abstract <R> R match(Cases<M,S1,S2,R> cases);

    public static <M,S1,S2> DoIndexed<M,S1,S2> effect(F2<Monad<M>,S1,__<M,S2>> effect) {
        return DoIndexedImpl.Effect(effect);
    }

    public static <M,S1,S2,S3> DoIndexed<M,S1,S3> sequence(F2<Monad<M>,S1,__<M,S2>> a, Supplier<DoIndexed<M,S2,S3>> b) {
        return DoIndexedImpl.Sequence(Sequence.create(a, b));
    }

    public static <M,S> __<M,S> run(MonadRec<M> mMonadRec, DoIndexed<M,T0,S> do_) {
        return mMonadRec.tailRec(
            (DoWithS1<M,?,S> a) -> go(mMonadRec, a),
            DoWithS1.create(do_, T0.of())
        );
    }

    private static <M,S> __<M,Either<DoWithS1<M,?,S>,S>> go(Monad<M> mMonad, DoWithS1<M,?,S> a) {
        return go2(mMonad, a);
    }

    private static <M,S1,S2> __<M,Either<DoWithS1<M,?,S2>,S2>> go2(Monad<M> mMonad, DoWithS1<M,S1,S2> a) {
        return DoIndexedImpl
            .<M,S1,S2>cases()
            .Effect(effect -> mMonad.map(Either::<DoWithS1<M,?,S2>,S2>Right, effect.apply(mMonad, a.s1())))
            .Sequence(sequence -> go2Sequence(mMonad, a.s1(), sequence))
            .apply(a._do());
    }

    private static <M,S1,S2,S3> __<M,Either<DoWithS1<M,?,S3>,S3>> go2Sequence(Monad<M> mMonad, S1 s1, Sequence<M,S1,S2,S3> sequence) {
        return mMonad.map(
            (S2 s2) -> Either.<DoWithS1<M, ?, S3>, S3>Left(DoWithS1.create(sequence.b().get(), s2)),
            sequence.a().apply(mMonad, s1)
        );
    }

    public static <M,S> __<M,S> runNoTailRec(Monad<M> mMonad, DoIndexed<M,T0,S> do_) {
        return _runNoTailRec(mMonad, do_, T0.of());
    }

    private static <M,S1,S2> __<M,S2> _runNoTailRec(Monad<M> mMonad, DoIndexed<M,S1,S2> do_, S1 s1) {
        return DoIndexedImpl
            .<M,S1,S2>cases()
            .Effect((effect) -> effect.apply(mMonad, s1))
            .Sequence(sequence -> _runNoTailRec_sequence(mMonad, s1, sequence))
            .apply(do_);
    }

    private static <M,S1,S2,S3> __<M,S2> _runNoTailRec_sequence(Monad<M> mMonad, S1 s1, Sequence<M,S1,S3,S2> sequence) {
        return mMonad.bind(
            sequence.a().apply(mMonad, s1),
            (S3 a) -> DoIndexed._runNoTailRec(mMonad, sequence.b().get(), a)
        );
    }

    private static class DoWithS1<M,S1,S2> {
        private final DoIndexed<M,S1,S2> _do;
        private final S1 _s1;

        private DoWithS1(DoIndexed<M,S1,S2> do_, S1 s1) {
            this._do = do_;
            this._s1 = s1;
        }

        public static <M,S1,S2> DoWithS1<M,S1,S2> create(DoIndexed<M,S1,S2> do_, S1 s1) {
            return new DoWithS1<>(do_, s1);
        }

        public DoIndexed<M,S1,S2> _do() {
            return _do;
        }

        public S1 s1() {
            return _s1;
        }
    }

    public static <M> DoIndexed<M,T0,T0> do_() {
        return DoIndexedImpl.Effect(Monad<M>::pure);
    }

    public <A> DoIndexed<M,S1,S2> __(__<M,A> ma) {
        return DoIndexedImpl
            .<M,S1,S2>cases()
            .Effect(effect ->
                sequence(
                    effect,
                    () -> effect((Monad<M> mMonad, S2 s) -> mMonad.rightSeq(ma, mMonad.pure(s)))
                )
            )
            .Sequence(sequence -> __sequence(ma, sequence))
            .apply(this);
    }

    private <S3,A> DoIndexed<M,S1,S2> __sequence(__<M,A> ma, Sequence<M,S1,S3,S2> sequence) {
        return sequence(
            sequence.a(),
            () -> sequence.b().get().__(ma)
        );
    }

    public <A> DoIndexed<M,S1,T2<S2,A>> push(A a) {
        return DoIndexedImpl
            .<M,S1,S2>cases()
            .Effect(effect ->
                sequence(
                    effect,
                    () -> effect((Monad<M> mMonad, S2 s) -> mMonad.pure(T2.of(s, a)))
                )
            )
            .Sequence(sequence -> push_sequence(a, sequence))
            .apply(this);
    }

    private <S3,A> DoIndexed<M,S1,T2<S2,A>> push_sequence(A a, Sequence<M,S1,S3,S2> sequence) {
        return sequence(
            sequence.a(),
            () -> sequence.b().get().push(a)
        );
    }

    public <A> DoIndexed<M,S1,T2<S2,A>> pushM(__<M,A> ma) {
        return DoIndexedImpl
            .<M,S1,S2>cases()
            .Effect(effect ->
                sequence(
                    effect,
                    () -> effect((Monad<M> mMonad, S2 s) -> mMonad.map((A a) -> T2.of(s, a), ma))
                )
            )
            .Sequence(sequence -> pushM_sequence(ma, sequence))
            .apply(this);
    }

    private <S3,A> DoIndexed<M,S1,T2<S2,A>> pushM_sequence(__<M,A> ma, Sequence<M,S1,S3,S2> sequence) {
        return sequence(
            sequence.a(),
            () -> sequence.b().get().pushM(ma)
        );
    }

    public <S3> DoIndexed<M,S1,S3> map(Function<S2,S3> f) {
        return DoIndexedImpl
            .<M,S1,S2>cases()
            .Effect(effect ->
                sequence(
                    effect,
                    () -> effect((Monad<M> mMonad, S2 s) -> mMonad.pure(f.apply(s)))
                )
            )
            .Sequence(sequence -> map_sequence(f, sequence))
            .apply(this);
    }

    private <S3,S4> DoIndexed<M,S1,S3> map_sequence(Function<S2,S3> f, Sequence<M,S1,S4,S2> sequence) {
        return sequence(
            sequence.a(),
            () -> sequence.b().get().map(f)
        );
    }

    public <S3> DoIndexed<M,S1,S3> bind(F2<Monad<M>,S2,__<M,S3>> f) {
        return DoIndexedImpl
            .<M,S1,S2>cases()
            .Effect(effect ->
                sequence(
                    effect,
                    () -> effect(f::apply)
                )
            )
            .Sequence(sequence -> bind_sequence(f, sequence))
            .apply(this);
    }

    private <S3,S4> DoIndexed<M,S1,S3> bind_sequence(F2<Monad<M>,S2,__<M,S3>> f, Sequence<M,S1,S4,S2> sequence) {
        return sequence(
            sequence.a(),
            () -> sequence.b().get().bind(f)
        );
    }

    public <S3> DoIndexed<M,S3,S2> compose(DoIndexed<M,S3,S1> other) {
        return DoIndexedImpl
            .<M,S3,S1>cases()
            .Effect(effect ->
                sequence(
                    effect,
                    () -> DoIndexed.this
                )
            )
            .Sequence(this::compose_sequence)
            .apply(other);
    }

    private <S3,S4> DoIndexed<M,S3,S2> compose_sequence(Sequence<M,S3,S4,S1> sequence) {
        return sequence(
            sequence.a(),
            () -> DoIndexed.this.compose(sequence.b().get())
        );
    }
}
