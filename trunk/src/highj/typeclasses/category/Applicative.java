/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Applicative<Ctor> extends Pointed<Ctor> {
    
    // <*> (Control.Applicative)
    public <A,B> _<Ctor,B> star(_<Ctor, F<A,B>> fn, _<Ctor,A> nestedA);
   
    // (*>) (Control.Applicative)
    public <A,B> _<Ctor,B> rightSeq(_<Ctor,A> nestedA, _<Ctor,B> nestedB);

    // (<*) (Control.Applicative)
    public <A,B> _<Ctor,A> leftSeq(_<Ctor,A> nestedA, _<Ctor,B> nestedB);
    
    // liftA (Control.Applicative), liftM (Control.Monad)
    public <A,B> _<Ctor,B> lift(F<A,B> fn, _<Ctor,A> nestedA);
    
    // liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public <A,B,C> _<Ctor,C> lift(F<A,F<B,C>> fn, _<Ctor,A> nestedA, _<Ctor,B> nestedB);
    
    // liftA3 (Control.Applicative), liftM3 (Control.Monad)
    public <A,B,C,D> _<Ctor,D> lift(F<A,F<B,F<C,D>>> fn, _<Ctor,A> nestedA, _<Ctor,B> nestedB, _<Ctor,C> nestedC);
    
    // liftA and liftM as instance of F
    public <A,B> F<F<A, B>,F<_<Ctor, A>,_<Ctor, B>>> liftFn();

    // liftA2 and liftM2 as instance of F
    public <A,B,C> F<F<A, F<B,C>>,F<_<Ctor, A>,F<_<Ctor, B>,_<Ctor,C>>>> liftFn2();

}
