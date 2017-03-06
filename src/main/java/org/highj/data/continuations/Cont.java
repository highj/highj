package org.highj.data.continuations;

import org.derive4j.hkt.__2;
import org.highj.data.continuations.cont.ContApplicative;
import org.highj.data.continuations.cont.ContFunctor;
import org.highj.data.continuations.cont.ContMonad;
import org.highj.function.Functions;

import java.util.function.Function;

/**
 * The monad for delimited continuations.
 * @param <R> the result type
 * @param <A> the input type
 */
public class Cont<R, A> implements __2<Cont.µ, R, A>, Function<Function<A, R>, R> {

    public interface µ {
    }

    private Function<Function<A, R>, R> fn;

    public Cont(Function<Function<A, R>, R> fn) {
        this.fn = fn;
    }

    @Override
    public R apply(Function<A, R> input) {
        return fn.apply(input);
    }

    public Function<Function<A, R>, R> runCont() {
        return fn;
    }

    public Cont<R, A> mapCont(Function<R, R> transform) {
        return new Cont<>(fn.andThen(transform));
    }

    public <B> Cont<R, B> map(Function<A,B> fn) {
        return new Cont<>(b -> this.apply(a -> b.apply(fn.apply(a))));
    }

    public <B> Cont<R,B> ap(Cont<R, Function<A,B>> fnCont) {
        return new Cont<>(c -> fnCont.apply(g -> this.apply(g.andThen(c))));
    }

    public <B> Cont<R,B> bind(Function<A, Cont<R,B>> fn) {
        return new Cont<>(c -> this.apply(b -> fn.apply(b).apply(c)));
    }

    public <B> Cont<R, B> with(Function<Function<B, R>, Function<A, R>> transform) {
        return new Cont<>(transform.andThen(fn));
    }

    public static <R,A> Cont<R,A> pure(A a) {
        return new Cont<>(fn -> fn.apply(a));
    }

    public static <R> R eval(Cont<R, R> cont) {
        return cont.apply(Function.identity());
    }

    public static <R, R_> Cont<R_, R> reset(Cont<R, R> cont) {
        return new Cont<>(k -> k.apply(eval(cont)));
    }

    public static <A, S, R> Cont<R, A> shift(Function<Function<A, Cont<S, R>>, Cont<R, R>> fn) {
        return new Cont<>(k -> eval(fn.apply(k.andThen(Cont::pure))));
    }

    public static <A,B,R> Cont<R,A> callCC(Function<Function<A, Cont<R,B>>, Cont<R,A>> fn) {
        return new Cont<>(c -> fn.apply(x ->
                new Cont<>(Functions.constant(c.apply(x)))).apply(c));
    }

    public static <S> ContFunctor<S> functor() {
        return new ContFunctor<S>() {
        };
    }

    public static <S> ContApplicative<S> applicative() {
        return new ContApplicative<S>() {
        };
    }

    public static <S> ContMonad<S> monad() {
        return new ContMonad<S>() {
        };
    }
}