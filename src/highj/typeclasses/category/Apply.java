/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.F3;
import highj._;
/**
 *
 * @author DGronau
 */
public interface Apply<Ctor> extends Functor<Ctor> {

    // <*> (Control.Applicative), ap (Control.Monad)
    public <A, B> _<Ctor, B> ap(_<Ctor, F<A, B>> fn, _<Ctor, A> nestedA);

    //curried version of ap
    public <A, B> F<_<Ctor, A>, _<Ctor, B>> ap(_<Ctor, F<A, B>> fn);

    // <* (Control.Applicative)
    public <A, B> _<Ctor, A> leftSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB);

    // *> (Control.Applicative)
    public <A, B> _<Ctor, B> rightSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB);

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public <A, B, C> F2<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>> lift2(F<A, F<B, C>> fn);

    //flat version of lift2
    public <A, B, C> F2<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>> lift2Flat(F2<A, B, C> fn);

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    public <A, B, C, D> F3<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>, _<Ctor, D>> lift3(F<A, F<B, F<C, D>>> fn);

    //flat version of lift2
    public <A, B, C, D> F3<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>, _<Ctor, D>> lift3Flat(F3<A, B, C, D> fn);

    
}
