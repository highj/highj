package org.highj.typeclass.comonad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Stream;
import org.highj.data.tuple.T2;
import org.highj.function.F1;

//minimal definition: extract() or extract(nestedA)
public interface Comonad<mu> extends Extend<mu> {

    public default <A> A extract(_<mu,A> nestedA) {
        return this.<A>extract().$(nestedA);
    }

    public default <A> F1<_<mu,A>, A> extract() {
        return new F1<_<mu,A>, A>(){
            @Override
            public A $(_<mu, A> a) {
                return extract(a);
            }
        };
    }

    //(=>>)
    public default <A, B> _<mu, B> unbind(_<mu, A> nestedA, F1<_<mu, A>, B> fn) {
        return extend(fn).$(nestedA);
    }

    //(.>>)
    public default <A, B> _<mu, B> inject(_<mu, A> nestedA, B b) {
        return extend(F1.<_<mu, A>,B>constant(b)).$(nestedA);
    }

    public default <A, B> F1<_<mu, A>, B> liftCtx(F1<A, B> fn) {
        return lift(fn).andThen(this.<B>extract());
    }

    public default <A, B> F1<_<mu, List<A>>, List<B>> mapW(final F1<_<mu, A>, B> fn) {
        final F1<List<A>,A> headFn = new F1<List<A>,A>(){
            @Override
            public A $(List<A> list) {
                return list.head();
            }
        };
        final F1<List<A>,List<A>> tailFn = new F1<List<A>,List<A>>(){
            @Override
            public List<A> $(List<A> list) {
                return list.tail();
            }
        };
        return new F1<_<mu, List<A>>, List<B>>(){
            @Override
            public List<B> $(_<mu, List<A>> a) {
                List<B> listB = List.Nil();
                _<mu, List<A>> listA = a;
                while(! extract(listA).isEmpty()) {
                    listB.cons(fn.$(map(headFn, listA)));
                    listA = map(tailFn, listA);
                }
                return listB.reverse();
            }
        };
    }

    public default <A> List<_<mu, A>> parallelW(_<mu, List<A>> nestedList) {
        return mapW(F1.<_<mu, A>>id()).$(nestedList);
    }

    public default <A,B> F1<_<mu,A>, Stream<B>> unfoldW(final F1<_<mu, A>, T2<B,A>> fn) {
        return new F1<_<mu,A>,Stream<B>>(){
            @Override
            public Stream<B> $(final _<mu, A> nestedA) {
                T2<B,A> pair = fn.$(nestedA);
                return Stream.Cons(pair._1(), unfoldW(fn).lazy(inject(nestedA, pair._2())));
            }
        };
    }

   public default <A, B> List<B> sequenceW(List<F1<_<mu, A>, B>> fnList, _<mu, A> nestedA) {
        List<B> listB = List.Nil();
        List<F1<_<mu, A>, B>> fns = fnList;
        while(! fns.isEmpty()){
            listB.cons(fns.head().$(nestedA));
            fns = fns.tail();
        }
        return listB.reverse();
    }
}

