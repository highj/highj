/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Function;
import fj.P2;
import fj.data.List;
import highj._;
import highj.__;
import highj.data.ListOf;
import highj.data2.PairOf;

/**
 * Minimal definition: implement either duplicate or extend
 * @author DGronau
 */
public abstract class ComonadAbstract<Ctor> extends CopointedAbstract<Ctor> implements Comonad<Ctor> {

    @Override
    public <A> _<Ctor, _<Ctor, A>> duplicate(_<Ctor, A> nestedA) {
        return extend(Function.<_<Ctor, A>>identity()).f(nestedA);
    }

    @Override
    public <A, B> F<_<Ctor, A>, _<Ctor, B>> extend(final F<_<Ctor, A>, B> fn) {
        return new F<_<Ctor, A>, _<Ctor, B>>(){
            @Override
            public _<Ctor, B> f(_<Ctor, A> a) {
                return fmap(fn, duplicate(a));
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
    public <A, B> F<_<Ctor, _<ListOf, A>>, _<ListOf, B>> mapW(F<_<Ctor, A>, B> fn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <A, B> F<_<Ctor, List<A>>, List<B>> mapWFlat(F<_<Ctor, A>, B> fn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <A> _<ListOf, _<Ctor, A>> parallelW(_<Ctor, _<ListOf, A>> nestedList) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <A> List<_<Ctor, A>> parallelWFlat(_<Ctor, List<A>> nestedList) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <A, B> F<_<Ctor, B>, _<ListOf, A>> unfoldW(F<_<Ctor, A>, __<PairOf, A, B>> fn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <A, B> F<_<Ctor, B>, List<A>> unfoldWFlat(F<_<Ctor, A>, P2<A, B>> fn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <A, B> _<ListOf, B> sequenceW(_<ListOf, F<_<Ctor, A>, B>> fnList, _<Ctor, A> nestedA) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <A, B> List<B> sequenceWFlat(List<F<_<Ctor, A>, B>> fnList, _<Ctor, A> nestedA) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
