/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.F3;
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
        return lift2(Function.<A, F<B, B>>constant().f(Function.<B>identity())).f(nestedA, nestedB);
    }

    @Override
    // <* (Control.Applicative)
    public <A, B> _<Ctor, A> leftSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return lift2(Function.<B, A>constant()).f(nestedA, nestedB);
    }

    @Override
    public <A,B,C> F2<_<Ctor, A>,_<Ctor,B>,_<Ctor, C>> lift2(final F<A, F<B, C>> fn) {
        return new F2<_<Ctor, A>,_<Ctor,B>,_<Ctor, C>>() {

            @Override
            public _<Ctor, C> f(_<Ctor, A> a, _<Ctor, B> b) {
                return star(fmap(fn, a), b);
            }
        };
    }
    
    @Override
    public <A,B,C,D> F3<_<Ctor, A>,_<Ctor,B>,_<Ctor, C>,_<Ctor,D>> lift3(final F<A, F<B, F<C, D>>> fn) {
        return new F3<_<Ctor, A>,_<Ctor,B>,_<Ctor, C>,_<Ctor,D>>() {
            @Override
            public _<Ctor, D> f(_<Ctor, A> a, _<Ctor, B> b, _<Ctor, C> c) {
                return star(lift2(fn).f(a, b), c);
            }
        };
    }

}
