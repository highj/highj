package org.highj.typeclass1.comonad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Stream;
import org.highj.data.tuple.T2;
import org.highj.data.functions.Functions;

import java.util.function.Function;

public interface Comonad<µ> extends Extend<µ> {

    public <A> A extract(_<µ,A> nestedA);

    //(=>>)
    public default <A, B> _<µ, B> unbind(_<µ, A> nestedA, Function<_<µ, A>, B> fn) {
        return extend(fn).apply(nestedA);
    }

    //(.>>)
    public default <A, B> _<µ, B> inject(_<µ, A> nestedA, B b) {
        return extend(Functions.<_<µ, A>,B>constant(b)).apply(nestedA);
    }

    public default <A, B> Function<_<µ, A>, B> liftCtx(Function<A, B> fn) {
        return Functions.compose(this::<B>extract, lift(fn));
    }

    public default <A, B> Function<_<µ, List<A>>, List<B>> mapW(final Function<_<µ, A>, B> fn) {
        return a -> {
            List<B> listB = List.nil();
            _<µ, List<A>> listA = a;
            while(! extract(listA).isEmpty()) {
                listB.plus(fn.apply(this.<List<A>, A>map(List::head, listA)));
                listA = map(List::tail, listA);
            }
            return listB.reverse();
        };
    }

    public default <A> List<_<µ, A>> parallelW(_<µ, List<A>> nestedList) {
        return mapW(Functions.<_<µ, A>>id()).apply(nestedList);
    }

    public default <A,B> Function<_<µ,A>, Stream<B>> unfoldW(final Function<_<µ, A>, T2<B,A>> fn) {
        return nestedA -> {
            T2<B,A> pair = fn.apply(nestedA);
            return Stream.Cons(pair._1(), () -> unfoldW(fn).apply(inject(nestedA, pair._2())));
        };
    }

   public default <A, B> List<B> sequenceW(List<Function<_<µ, A>, B>> fnList, _<µ, A> nestedA) {
        List<B> listB = List.nil();
        List<Function<_<µ, A>, B>> fns = fnList;
        while(! fns.isEmpty()){
            listB.plus(fns.head().apply(nestedA));
            fns = fns.tail();
        }
        return listB.reverse();
    }
}

