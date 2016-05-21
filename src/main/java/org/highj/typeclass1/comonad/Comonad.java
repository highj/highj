package org.highj.typeclass1.comonad;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.Stream;
import org.highj.data.tuple.T2;
import org.highj.function.Functions;

import java.util.function.Function;

public interface Comonad<W> extends Extend<W> {

    public <A> A extract(__<W,A> nestedA);

    //(=>>)
    public default <A, B> __<W, B> unbind(__<W, A> nestedA, Function<__<W, A>, B> fn) {
        return extend(fn).apply(nestedA);
    }

    //(.>>)
    public default <A, B> __<W, B> inject(__<W, A> nestedA, B b) {
        return extend(Functions.<__<W, A>,B>constant(b)).apply(nestedA);
    }

    public default <A, B> Function<__<W, A>, B> liftCtx(Function<A, B> fn) {
        return Functions.compose(this::<B>extract, lift(fn));
    }

    public default <A, B> Function<__<W, List<A>>, List<B>> mapW(final Function<__<W, A>, B> fn) {
        return a -> {
            List<B> listB = List.Nil();
            __<W, List<A>> listA = a;
            while(! extract(listA).isEmpty()) {
                listB = listB.plus(fn.apply(this.<List<A>, A>map(List::head, listA)));
                listA = map(List::tail, listA);
            }
            return listB.reverse();
        };
    }

    public default <A> List<__<W, A>> parallelW(__<W, List<A>> nestedList) {
        return mapW(Function.<__<W, A>>identity()).apply(nestedList);
    }

    public default <A,B> Function<__<W,A>, Stream<B>> unfoldW(final Function<__<W, A>, T2<B,A>> fn) {
        return nestedA -> {
            T2<B,A> pair = fn.apply(nestedA);
            return Stream.newLazyStream(pair._1(), () -> unfoldW(fn).apply(inject(nestedA, pair._2())));
        };
    }

   public default <A, B> List<B> sequenceW(List<Function<__<W, A>, B>> fnList, __<W, A> nestedA) {
        List<B> listB = List.Nil();
        List<Function<__<W, A>, B>> fns = fnList;
        while(! fns.isEmpty()){
            listB = listB.plus(fns.head().apply(nestedA));
            fns = fns.tail();
        }
        return listB.reverse();
    }
}

