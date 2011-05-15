/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Unit;
import highj.TC;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Functor<Ctor extends TC<Ctor>> {

    //fmap and <$> (Data.Functor)
    public <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);

    // <$  (Data.Functor)  
    public <A, B> _<Ctor, A> left$(A a, _<Ctor, B> nestedB);
    
    //void (Control.Monad)
    public <A> _<Ctor, Unit> voidF(_<Ctor, A> nestedA);
    
    //.: and binary (Data.Functor.Syntax)
    public <A, B, C2 extends TC<C2>> _<Ctor, _<C2,B>> binary (Functor<C2> functor2, 
             F<A, B> fn, _<Ctor, _<C2, A>> nestedA);
    
    //.:: and trinary (Data.Functor.Syntax)
    public <A, B, C2 extends TC<C2>, C3 extends TC<C3>> _<Ctor, _<C2, _<C3, B>>> 
            trinary (Functor<C2> functor2, Functor<C3> functor3,
             F<A, B> fn, _<Ctor, _<C2, _<C3, A>>> nestedNestedA);

    //flip (Data.Functor.Syntax)
    public <A, B> _<Ctor, B> flip(_<Ctor, F<A, B>> nestedFn, A a);
    
    //fmap as an instance of F 
    public <A, B> F<F<A, B>, F<_<Ctor, A>, _<Ctor, B>>> fmapFunction();
    
}
