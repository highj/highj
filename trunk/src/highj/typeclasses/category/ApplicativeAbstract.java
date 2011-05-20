/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Function;
import highj._;

/**
 *
 * @author DGronau
 */
public abstract class ApplicativeAbstract<Ctor> extends PointedAbstract<Ctor> implements Applicative<Ctor> {


    @Override
    // <*> (Control.Applicative)
    public abstract <A, B> _<Ctor, B> star(_<Ctor, F<A, B>> fn, _<Ctor, A> nestedA);
    
    @Override
    // *> (Control.Applicative)
    public <A, B> _<Ctor, B> rightSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return lift(Function.<A, F<B, B>>constant().f(Function.<B>identity()), nestedA, nestedB);
    }

    @Override
    // <* (Control.Applicative)
    public <A, B> _<Ctor, A> leftSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return lift(Function.<B, A>constant(), nestedA, nestedB);
    }

    @Override
    // liftA (Control.Applicative), liftM (Control.Monad)
    public <A, B> _<Ctor, B> lift(F<A, B> fn, _<Ctor, A> nestedA) {
        return fmap(fn, nestedA);
    }

    @Override
    // liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public <A, B, C> _<Ctor, C> lift(F<A, F<B, C>> fn, _<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return star(lift(fn, nestedA), nestedB);
    }

    @Override
    // liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public <A, B, C, D> _<Ctor, D> lift(F<A, F<B, F<C, D>>> fn, _<Ctor, A> nestedA, _<Ctor, B> nestedB, _<Ctor, C> nestedC) {
        return star(lift(fn, nestedA, nestedB), nestedC);
    }
    
   @Override
    // lift A and liftM as instance of F
    public <A,B> F<F<A, B>,F<_<Ctor, A>,_<Ctor, B>>> liftFn() {
        return new F2<F<A, B>,_<Ctor, A>,_<Ctor, B>>() {
            @Override
            public _<Ctor, B> f(F<A, B> fn, _<Ctor, A> nestedA) {
                return lift(fn, nestedA);
            }
        }.curry();
    }    
   
    @Override
    // liftA2 and liftM2 as instance of F
    public <A,B,C> F<F<A, F<B, C>>,F<_<Ctor, A>,F<_<Ctor, B>,_<Ctor,C>>>> liftFn2(){
        return new F2<F<A, F<B, C>>,_<Ctor, A>,F<_<Ctor, B>,_<Ctor,C>>>() {
            @Override
            public F<_<Ctor, B>,_<Ctor,C>> f(final F<A, F<B, C>> fn, final _<Ctor, A> nestedA) {
                return new F<_<Ctor, B>,_<Ctor,C>>(){
                    @Override
                    public _<Ctor, C> f(_<Ctor, B> nestedB) {
                        return lift(fn, nestedA, nestedB);
                    }
                };
            }
        }.curry();
    }


}
