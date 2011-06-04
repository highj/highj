/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Function;
import fj.P1;
import fj.P2;
import fj.data.List;
import fj.data.Stream;
import highj._;
import highj.__;
import highj.data.ListOf;
import highj.data2.PairOf;

/**
 * http://hackage.haskell.org/packages/archive/comonad/1.0.1/doc/html/Control-Comonad.html
 * 
 * Minimal definition: implement either duplicate or extend
 * @author DGronau
 */
public abstract class ComonadAbstract<Ctor> extends ExtendAbstract<Ctor> implements Comonad<Ctor> {

    @Override
    public <A> F<_<Ctor,A>, A> extract() {
       return new F<_<Ctor,A>, A>(){
            @Override
            public A f(_<Ctor, A> a) {
                return extract(a);
            }
       };
    }
    
    @Override
    //(=>>)
    public <A, B> _<Ctor, B> unbind(_<Ctor, A> nestedA, F<_<Ctor, A>, B> fn) {
       return extend(fn).f(nestedA);
    }

    @Override
    //(.>>) 
    public <A, B> _<Ctor, B> inject(_<Ctor, A> nestedA, B b) {
        return extend(Function.<_<Ctor, A>,B>constant(b)).f(nestedA);
    }

    @Override
    public <A, B> F<_<Ctor, A>, B> liftCtx(F<A, B> fn) {
        return lift(fn).andThen(this.<B>extract());
    }

    @Override
    public <A, B> F<_<Ctor, _<ListOf, A>>, _<ListOf, B>> mapW(final F<_<Ctor, A>, B> fn) {
        final F<_<ListOf,A>,A> headFn = new F<_<ListOf,A>,A>(){
            @Override
            public A f(_<ListOf,A> list) {
                return ListOf.unwrap(list).head();
            }
        };
        final F<_<ListOf,A>,_<ListOf,A>> tailFn = new F<_<ListOf,A>,_<ListOf,A>>(){
            @Override
            public _<ListOf,A> f(_<ListOf,A> list) {
                return ListOf.wrap(ListOf.unwrap(list).tail());
            }
        };
        return new F<_<Ctor, _<ListOf, A>>, _<ListOf, B>>(){
            @Override
            public _<ListOf, B> f(_<Ctor, _<ListOf, A>> a) {
                List<B> listB = List.nil();
                _<Ctor, _<ListOf, A>> listA = a;
                while(! ListOf.isEmpty(extract(listA))) {
                   listB.cons(fn.f(fmap(headFn, listA)));
                   listA = fmap(tailFn, listA);
                }        
                return ListOf.wrap(listB.reverse());        
            }
        };
    }

    @Override
    public <A, B> F<_<Ctor, List<A>>, List<B>> mapWFlat(final F<_<Ctor, A>, B> fn) {
        final F<List<A>,A> headFn = new F<List<A>,A>(){
            @Override
            public A f(List<A> list) {
                return list.head();
            }
        };
        final F<List<A>,List<A>> tailFn = new F<List<A>,List<A>>(){
            @Override
            public List<A> f(List<A> list) {
                return list.tail();
            }
        };
        return new F<_<Ctor, List<A>>, List<B>>(){
            @Override
            public List<B> f(_<Ctor, List<A>> a) {
                List<B> listB = List.nil();
                _<Ctor, List<A>> listA = a;
                while(extract(listA).isNotEmpty()) {
                   listB.cons(fn.f(fmap(headFn, listA)));
                   listA = fmap(tailFn, listA);
                }        
                return listB.reverse();        
            }
        };
    }

    @Override
    public <A> _<ListOf, _<Ctor, A>> parallelW(_<Ctor, _<ListOf, A>> nestedList) {
        return mapW(Function.<_<Ctor,A>>identity()).f(nestedList);
    }

    @Override
    public <A> List<_<Ctor, A>> parallelWFlat(_<Ctor, List<A>> nestedList) {
        return mapWFlat(Function.<_<Ctor,A>>identity()).f(nestedList);
    }
    
    @Override
    //TODO: replace stream by IterableW
    public <A,B> F<_<Ctor,A>, Stream<B>> unfoldW(final F<_<Ctor, A>, __<PairOf,B,A>> fn){
        return unfoldWFlat(fn.andThen(new F<__<PairOf,B,A>,P2<B,A>>(){

            @Override
            public P2<B, A> f(__<PairOf, B, A> pair) {
                return PairOf.unwrap(pair);
            }
        }));
    }
    
    @Override
    //TODO: replace stream by IterableW
    public <A,B> F<_<Ctor,A>, Stream<B>> unfoldWFlat(final F<_<Ctor, A>, P2<B,A>> fn) {
        final F<_<Ctor, A>, A> fnSnd = fn.andThen(P2.<B,A>__2());
        return new F<_<Ctor,A>, Stream<B>>() {
            @Override
            public Stream<B> f(final _<Ctor, A> a) {
                return Stream.cons(fn.f(a)._1(), new P1<Stream<B>>(){
                    @Override
                    public Stream<B> _1() {
                        return unfoldWFlat(fn).f(unbind(a, fnSnd));
                    }
                });
            }
        };
    }


    @Override
    public <A, B> _<ListOf, B> sequenceW(_<ListOf, F<_<Ctor, A>, B>> fnList, _<Ctor, A> nestedA) {
        return ListOf.wrap(sequenceWFlat(ListOf.unwrap(fnList), nestedA));
    }

    @Override
    public <A, B> List<B> sequenceWFlat(List<F<_<Ctor, A>, B>> fnList, _<Ctor, A> nestedA) {
        List<B> listB = List.nil();
        List<F<_<Ctor, A>, B>> fns = fnList;
        while(fns.isNotEmpty()){
            listB.cons(fns.head().f(nestedA));
            fns = fns.tail();
        }
        return listB.reverse();
    }
    
}
