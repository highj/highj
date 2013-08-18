package org.highj.typeclass1.comonad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Stream;
import org.highj.data.tuple.T2;
import org.highj.data.functions.Functions;

import java.util.function.Function;

public interface Comonad<W> extends Extend<W> {

    public <A> A extract(_<W,A> nestedA);

    //(=>>)
    public default <A, B> _<W, B> unbind(_<W, A> nestedA, Function<_<W, A>, B> fn) {
        return extend(fn).apply(nestedA);
    }

    //(.>>)
    public default <A, B> _<W, B> inject(_<W, A> nestedA, B b) {
        return extend(Functions.<_<W, A>,B>constant(b)).apply(nestedA);
    }

    public default <A, B> Function<_<W, A>, B> liftCtx(Function<A, B> fn) {
        return Functions.compose(this::<B>extract, lift(fn));
    }

    public default <A, B> Function<_<W, List<A>>, List<B>> mapW(final Function<_<W, A>, B> fn) {
        return a -> {
            List<B> listB = List.nil();
            _<W, List<A>> listA = a;
            while(! extract(listA).isEmpty()) {
                listB.plus(fn.apply(this.<List<A>, A>map(List::head, listA)));
                listA = map(List::tail, listA);
            }
            return listB.reverse();
        };
    }

    public default <A> List<_<W, A>> parallelW(_<W, List<A>> nestedList) {
        return mapW(Functions.<_<W, A>>id()).apply(nestedList);
    }

    public default <A,B> Function<_<W,A>, Stream<B>> unfoldW(final Function<_<W, A>, T2<B,A>> fn) {
        return nestedA -> {
            T2<B,A> pair = fn.apply(nestedA);
            return Stream.newLazyStream(pair._1(), () -> unfoldW(fn).apply(inject(nestedA, pair._2())));
        };
    }

   public default <A, B> List<B> sequenceW(List<Function<_<W, A>, B>> fnList, _<W, A> nestedA) {
        List<B> listB = List.nil();
        List<Function<_<W, A>, B>> fns = fnList;
        while(! fns.isEmpty()){
            listB.plus(fns.head().apply(nestedA));
            fns = fns.tail();
        }
        return listB.reverse();
    }
}

