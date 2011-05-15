/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Function;
import highj.TC;
import highj._;
import highj.typeclasses.category.Applicative;

/**
 *
 * @author DGronau
 */
public abstract class ApplicativeAbstract<Ctor extends TC<Ctor>> extends PointedAbstract<Ctor> implements Applicative<Ctor> {


    @Override
    // <*>
    public abstract <A, B> _<Ctor, B> star(_<Ctor, F<A, B>> fn, _<Ctor, A> nestedA);

    
    @Override
    public abstract <A> _<Ctor, A> pure(A a);

    @Override
    public abstract <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA);
    
    @Override
    // *>
    public <A, B> _<Ctor, B> rightSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return liftA2(Function.<A, F<B, B>>constant().f(Function.<B>identity()), nestedA, nestedB);
    }

    @Override
    // <*
    public <A, B> _<Ctor, A> leftSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return liftA2(Function.<B, A>constant(), nestedA, nestedB);
    }

    @Override
    public <A, B> _<Ctor, B> liftA(F<A, B> fn, _<Ctor, A> nestedA) {
        return fmap(fn, nestedA);
    }

    @Override
    public <A, B, C> _<Ctor, C> liftA2(F<A, F<B, C>> fn, _<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return star(liftA(fn, nestedA), nestedB);
    }

    @Override
    public <A, B, C, D> _<Ctor, D> liftA3(F<A, F<B, F<C, D>>> fn, _<Ctor, A> nestedA, _<Ctor, B> nestedB, _<Ctor, C> nestedC) {
        return star(liftA2(fn, nestedA, nestedB), nestedC);
    }

}
