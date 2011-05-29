/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Function;
import fj.Unit;
import fj.data.List;
import highj._;
import highj.data.ListOf;

/**
 * You need to implement either bind or join
 * @author DGronau
 */
public abstract class BindAbstract<Ctor> extends ApplyAbstract<Ctor> implements Bind<Ctor> {
    
    @Override
    // You need to implement either bind or join
    // (>>=) (Control.Monad)
    public <A, B> _<Ctor, B> bind(_<Ctor, A> nestedA, F<A, _<Ctor, B>> fn) {
        //(>>-) :: m a -> (a -> m b) -> m b
        //m >>= f = join (fmap f m)
        return join(fmap(fn, nestedA));
    }

    @Override
    //You need to implement either bind or join
    // join (Control.Monad)
    public <A> _<Ctor, A> join(_<Ctor, _<Ctor, A>> nestedNestedA) {
        //join :: m (m a) -> m a
        //join = (>>- id)
        return bind(nestedNestedA, Function.<_<Ctor, A>>identity());
    }

    // (>>) (Control.Monad)
    @Override
    public <A, B> _<Ctor, B> semicolon(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return bind(nestedA, Function.<A, _<Ctor, B>>constant().f(nestedB));
    }

    @Override
    // (>=>) (Control.Monad) left-to-right Kleisli composition of monads
    public <A, B, C> F<A, _<Ctor, C>> kleisli(final F<A, _<Ctor, B>> f, final F<B, _<Ctor, C>> g) {
        return new F<A, _<Ctor, C>>() {
            @Override
            public _<Ctor, C> f(A a) {
                return BindAbstract.this.bind(f.f(a), g);
            }
        };
    }
}
