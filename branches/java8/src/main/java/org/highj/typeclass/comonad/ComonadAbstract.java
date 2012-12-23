package org.highj.typeclass.comonad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Stream;
import org.highj.data.tuple.T2;
import org.highj.function.F1;

public abstract class ComonadAbstract<mu> extends ExtendAbstract<mu> implements Comonad<mu> {

    @Override
    public <A> F1<_<mu,A>, A> extract() {
        return new F1<_<mu,A>, A>(){
            @Override
            public A $(_<mu, A> a) {
                return extract(a);
            }
        };
    }

    @Override
    //(=>>)
    public <A, B> _<mu, B> unbind(_<mu, A> nestedA, F1<_<mu, A>, B> fn) {
        return extend(fn).$(nestedA);
    }

    @Override
    //(.>>)
    public <A, B> _<mu, B> inject(_<mu, A> nestedA, B b) {
        return extend(F1.<_<mu, A>,B>constant(b)).$(nestedA);
    }

    @Override
    public <A, B> F1<_<mu, A>, B> liftCtx(F1<A, B> fn) {
        return lift(fn).andThen(this.<B>extract());
    }

    @Override
    public <A, B> F1<_<mu, List<A>>, List<B>> mapW(final F1<_<mu, A>, B> fn) {
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

    @Override
    public <A> List<_<mu, A>> parallelW(_<mu, List<A>> nestedList) {
        return mapW(F1.<_<mu, A>>id()).$(nestedList);
    }

    @Override
    public <A,B> F1<_<mu,A>, Stream<B>> unfoldW(final F1<_<mu, A>, T2<B,A>> fn) {
        return new F1<_<mu,A>,Stream<B>>(){
            @Override
            public Stream<B> $(final _<mu, A> nestedA) {
                T2<B,A> pair = fn.$(nestedA);
                return Stream.Cons(pair._1(), unfoldW(fn).lazy(inject(nestedA, pair._2())));
            }
        };
    }

    @Override
    public <A, B> List<B> sequenceW(List<F1<_<mu, A>, B>> fnList, _<mu, A> nestedA) {
        List<B> listB = List.Nil();
        List<F1<_<mu, A>, B>> fns = fnList;
        while(! fns.isEmpty()){
            listB.cons(fns.head().$(nestedA));
            fns = fns.tail();
        }
        return listB.reverse();
    }

}
