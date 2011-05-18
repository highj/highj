/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Unit;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Functor<Ctor> {

    //fmap and <$> (Data.Functor)
    public <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);

    // <$  (Data.Functor)  
    public <A, B> _<Ctor, A> left$(A a, _<Ctor, B> nestedB);
    
    //void (Control.Monad)
    public <A> _<Ctor, Unit> voidF(_<Ctor, A> nestedA);
    
    //.: and binary (Data.Functor.Syntax)
    public <A, B, Ctor2> _<Ctor, _<Ctor2,B>> binary (Functor<Ctor2> functor2, 
             F<A, B> fn, _<Ctor, _<Ctor2, A>> nestedA);
    
    //.:: and trinary (Data.Functor.Syntax)
    public <A, B, Ctor2, Ctor3> _<Ctor, _<Ctor2, _<Ctor3, B>>> 
            trinary (Functor<Ctor2> functor2, Functor<Ctor3> functor3,
             F<A, B> fn, _<Ctor, _<Ctor2, _<Ctor3, A>>> nestedNestedA);

    //flip (Data.Functor.Syntax)
    public <A, B> _<Ctor, B> flip(_<Ctor, F<A, B>> nestedFn, A a);
    
    //fmap as an instance of F 
    public <A, B> F<F<A, B>, F<_<Ctor, A>, _<Ctor, B>>> fmapFunction();
    
}
