/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj.TC;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Applicative<Ctor extends TC<Ctor>> extends Pointed<Ctor> {
    
    @Override
    // pure (Control.Applicative, Data.Pointed)
    public <A> _<Ctor,A> pure(A a);
    
    // <*> (Control.Applicative)
    public <A,B> _<Ctor,B> star(_<Ctor, F<A,B>> fn, _<Ctor,A> nestedA);
   
   
    // (*>) (Control.Applicative)
    public <A,B> _<Ctor,B> rightSeq(_<Ctor,A> nestedA, _<Ctor,B> nestedB);

    // (<*) (Control.Applicative)
    public <A,B> _<Ctor,A> leftSeq(_<Ctor,A> nestedA, _<Ctor,B> nestedB);
    
    // liftA (Control.Applicative)
    public <A,B> _<Ctor,B> liftA(F<A,B> fn, _<Ctor,A> nestedA);
    // liftA2 (Control.Applicative)
    public <A,B,C> _<Ctor,C> liftA2(F<A,F<B,C>> fn, _<Ctor,A> nestedA, _<Ctor,B> nestedB);
    // liftA3 (Control.Applicative)
    public <A,B,C,D> _<Ctor,D> liftA3(F<A,F<B,F<C,D>>> fn, _<Ctor,A> nestedA, _<Ctor,B> nestedB, _<Ctor,C> nestedC);
}
