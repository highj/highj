/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Function;
import highj._;

/**
 * http://hackage.haskell.org/packages/archive/comonad/1.0.1/doc/html/Control-Comonad.html
 * 
 * Minimal definition: implement either duplicate or extend
 * @author DGronau
 */
public abstract class ExtendAbstract<Ctor> extends FunctorAbstract<Ctor> implements Extend<Ctor> {
    
    @Override
    public <A> _<Ctor, _<Ctor, A>> duplicate(_<Ctor, A> nestedA) {
        // duplicate = extend id
        return extend(Function.<_<Ctor, A>>identity()).f(nestedA);
    }

    @Override
    public <A> F<_<Ctor, A>,_<Ctor, _<Ctor, A>>> duplicate() {
        return new F<_<Ctor, A>,_<Ctor, _<Ctor, A>>>() {
            @Override
            public _<Ctor, _<Ctor, A>> f(_<Ctor, A> a) {
                return duplicate(a);
            }
        };
    }

    @Override
    public <A, B> F<_<Ctor, A>, _<Ctor, B>> extend(final F<_<Ctor, A>, B> fn) {
        //extend f = fmap f . duplicate
        F<_<Ctor, A>,_<Ctor, _<Ctor, A>>> dupFn = duplicate();
        return lift(fn).o(dupFn);
    }

    @Override
    public <A, B, C> F<_<Ctor, A>, C> cokleisli(F<_<Ctor, A>, B> f, F<_<Ctor, B>, C> g) {
        //f =>= g = g . extend f 
       return g.o(extend(f));
    }
    
}
