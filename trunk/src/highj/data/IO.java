/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import highj.typeclasses.category.Monad;
import highj.typeclasses.category.MonadAbstract;

/**
 * Is this even remotely useful?
 * 
 * @author dgronau
 */
public final class IO {

    private final static IO hidden = new IO();
    
    private IO(){};
    
    private static <T> _<IO,T> wrap(T t) {
       return new _<IO,T>(hidden, t);
    }

    private static <T> T unwrap(_<IO,T> wrapped) {
       return (T) wrapped.read(hidden);
    }
    
    public final static Monad<IO> monad = new MonadAbstract<IO>() {

        @Override
        public <A, B> _<IO, B> bind(_<IO, A> nestedA, F<A, _<IO, B>> fn) {
            return fn.f(unwrap(nestedA));
        }

        @Override
        public <A, B> _<IO, B> star(_<IO, F<A, B>> fn, _<IO, A> nestedA) {
            return wrap(unwrap(fn).f(unwrap(nestedA)));
        }

        @Override
        public <A> _<IO, A> pure(A a) {
            return wrap(a);
        }

        @Override
        public <A, B> _<IO, B> fmap(F<A, B> fn, _<IO, A> nestedA) {
            return wrap(fn.f(unwrap(nestedA)));
        }
        
    };
    
}
