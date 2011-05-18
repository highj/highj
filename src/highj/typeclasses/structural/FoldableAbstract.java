/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.structural;

import fj.F;
import fj.F2;
import fj.Function;
import fj.Monoid;
import fj.data.Option;
import highj._;

/**
 * 
 * @author DGronau
 */
public abstract class FoldableAbstract<Ctor> implements Foldable<Ctor> {

    @Override
    public <A> A fold(Monoid<A> ma, _<Ctor, A> nestedA) {
        return foldMap(ma, Function.<A>identity(), nestedA);
    }

    @Override
    public <A, B> B foldMap(final Monoid<B> mb, final F<A, B> fn, _<Ctor, A> nestedA) {
        return foldr(new F2<A,B,B>(){
            @Override
            public B f(A a, B b) {
                return mb.sum(fn.f(a),b);
            }
        }.curry(), mb.zero(), nestedA);        
    }

    @Override
    public abstract <A, B> B foldr(F<A, F<B, B>> fn, B b, _<Ctor, A> nestedA);

    @Override 
    //This is very inefficient, please override if possible.
    public <A, B> A foldl(final F<A, F<B, A>> fn, A a, _<Ctor, B> nestedB) {
        //foldl f a bs = foldr (\b h -> \a ->h (f a b)) id bs a
        return foldr(new F2<B,F<A,A>,F<A,A>>(){
            @Override
            public F<A, A> f(final B b, final F<A, A> h) {
                return new F<A,A>(){
                    @Override
                    public A f(A a) {
                        return h.f(fn.f(a).f(b));
                    }
                };
            }
        }, Function.<A>identity(), nestedB).f(a);
    }

    @Override
    public <A> A foldr1(final F<A, F<A, A>> fn,  _<Ctor, A> nestedA) {
        return foldr(new F2<A, Option<A>,Option<A>>(){
            @Override
            public Option<A> f(A a, Option<A> b) {
                return b.isNone() 
                        ? Option.some(a) 
                        : Option.some(fn.f(a).f(b.some()));
            }
        }.curry(), Option.<A>none(), nestedA).some();
    }

    @Override
    public <A> A foldl1(final F<A, F<A, A>> fn, _<Ctor, A> nestedA) {
        return foldl(new F2<Option<A>, A, Option<A>>(){
            @Override
            public Option<A> f(Option<A> b, A a) {
                return b.isNone() 
                        ? Option.some(a) 
                        : Option.some(fn.f(b.some()).f(a));
            }
        }.curry(), Option.<A>none(), nestedA).some();
    }

    @Override
    public <A, B> B foldr(F2<A, B, B> fn, B b, _<Ctor, A> nestedA) {
        return foldr(fn.curry(), b, nestedA);
    }

    @Override
    public <A, B> A foldl(F2<A, B, A> fn, A a, _<Ctor, B> nestedB) {
        return foldl(fn.curry(), a, nestedB);
    }

    @Override
    public <A> A foldr1(F2<A, A, A> fn, _<Ctor, A> nestedA) {
        return foldr1(fn.curry(), nestedA);
    }

    @Override
    public <A> A foldl1(F2<A, A, A> fn, _<Ctor, A> nestedA) {
        return foldl1(fn.curry(), nestedA);
    }
}
