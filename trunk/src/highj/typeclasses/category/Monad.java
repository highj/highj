/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Unit;
import fj.data.List;
import highj.TC;
import highj._;
import highj.data.ListOf;

/**
 *
 * @author DGronau
 */
public interface Monad<Ctor extends TC<Ctor>> extends Applicative<Ctor> {
    
    // (>>=) (Control.Monad)
    public <A,B> _<Ctor, B> bind(_<Ctor, A> nestedA, F<A, _<Ctor, B>> fn);
    
    // (>>) (Control.Monad)
    public <A,B> _<Ctor, B> semicolon(_<Ctor, A> nestedA, _<Ctor, B> nestedB);

    // join (Control.Monad)
    public <A> _<Ctor, A> join(_<Ctor, _<Ctor, A>> nestedNestedA); 
    
    // sequence (Control.Monad)
    public <A> _<Ctor, _<ListOf, A>> sequence(_<ListOf, _<Ctor, A>> list);
    // "flat" version of sequence
    public <A> _<Ctor, List<A>> sequenceFlat(List<_<Ctor, A>> list);
    // sequence_ (Control.Monad)
    public <A> _<Ctor, Unit> sequence_(_<ListOf, _<Ctor, A>> list);
    // "flat" version of sequence_ 
    public <A> _<Ctor, Unit> sequence_Flat(List<_<Ctor, A>> list); 
    // mapM (Control.Monad)
    public <A, B> _<Ctor, _<ListOf, B>> mapM(F<A, _<Ctor, B>> fn, _<ListOf, A> list);
    // "flat" version of mapM
    public <A, B> _<Ctor, List<B>> mapMFlat(F<A, _<Ctor, B>> fn, List<A> list);
    // >=> (Control.Monad)
    public <A, B, C> F<A, _<Ctor, C>> kleisli(F<A, _<Ctor, B>> fn, F<B, _<Ctor, C>> gn);
        
    // liftM (Control.Monad)
    public <A,B> _<Ctor, B> liftM(F<A, B> fn, _<Ctor, A> nestedA);
    // liftM2 (Control.Monad)
    public <A,B,C> _<Ctor, C> liftM2(F<A, F<B, C>> fn, _<Ctor, A> nestedA, _<Ctor, B> nestedB);
    // liftM3 (Control.Monad)
    public <A, B, C, D> _<Ctor, D> liftM3(F<A, F<B, F<C, D>>> fn, _<Ctor, A> nestedA, _<Ctor, B> nestedB, _<Ctor, C> nestedC);
    // return (Control.Monad) is Pointed.pure
    public <A> _<Ctor, A> returnM(A a);
    // ap (Control.Monad)
    public <A, B> _<Ctor, B> ap(_<Ctor, F<A,B>> nestedFn, _<Ctor,A> nestedA);
}
