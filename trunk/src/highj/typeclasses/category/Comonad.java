/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.P2;
import fj.data.List;
import fj.data.Stream;
import highj._;
import highj.__;
import highj.data.ListOf;
import highj.data2.PairOf;

/**
 * http://hackage.haskell.org/packages/archive/category-extras/latest/doc/html/Control-Comonad.html
 * 
 * TODO: replace by http://hackage.haskell.org/package/comonad-1.1.0
 * 
 * @author DGronau
 */
public interface Comonad<Ctor> extends Copointed<Ctor> {
    
    public <A> _<Ctor,_<Ctor,A>> duplicate(_<Ctor, A> nestedA);
    
    public <A,B> F<_<Ctor, A>,_<Ctor,B>> extend(F<_<Ctor,A>, B> fn);
    
    //(=>>)
    public <A,B> _<Ctor,B> unbind(_<Ctor, A> nestedA, F<_<Ctor,A>, B> fn); 

    //(.>>) 
    public <A,B> _<Ctor,B> inject(_<Ctor, A> nestedA, B b);

    public <A,B> F<_<Ctor, A>, B> liftCtx(F<A, B> fn);

    public <A, B> F<_<Ctor,_<ListOf,A>>, _<ListOf,B>> mapW(F<_<Ctor, A>, B> fn); 

    public <A, B> F<_<Ctor,List<A>>, List<B>> mapWFlat(F<_<Ctor, A>, B> fn); 

    public <A> _<ListOf,_<Ctor,A>> parallelW(_<Ctor,_<ListOf,A>> nestedList);

    public <A> List<_<Ctor,A>> parallelWFlat(_<Ctor, List<A>> nestedList);

    //TODO: replace Stream by IterableW
    public <A,B> F<_<Ctor,A>, Stream<B>> unfoldW(F<_<Ctor, A>, __<PairOf,B,A>> fn);
    
    //TODO: replace Stream by IterableW
    public <A,B> F<_<Ctor,A>, Stream<B>> unfoldWFlat(F<_<Ctor, A>, P2<B,A>> fn);

    public <A,B> _<ListOf,B> sequenceW(_<ListOf,F<_<Ctor,A>,B>> fnList, _<Ctor, A> nestedA);

    public <A,B> List<B> sequenceWFlat(List<F<_<Ctor,A>,B>> fnList, _<Ctor, A> nestedA);
}
