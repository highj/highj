/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Monoid;
import fj.data.List;
import fj.data.Option;
import highj._;

import highj.data.ListOf;
import highj.data.OptionOf;

/**
 *
 * @author DGronau
 */
public abstract class MonadPlusAbstract<Ctor> extends MonadAbstract<Ctor> implements MonadPlus<Ctor>, Alternative<Ctor> {
    
    private final AlternativeAbstract<Ctor> alternative;

    public MonadPlusAbstract(Plus<Ctor> plus) {
        alternative = new AlternativeAbstract<Ctor>(plus){
            @Override
            public <A> _<Ctor, A> pure(A a) {
                return MonadPlusAbstract.this.pure(a);
            }

            @Override
            public <A, B> _<Ctor, B> ap(_<Ctor, F<A, B>> fn, _<Ctor, A> nestedA) {
                return MonadPlusAbstract.this.ap(fn, nestedA);
            }

            @Override
            public <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA) {
                return MonadPlusAbstract.this.fmap(fn, nestedA);
            }
        };
    }
    
    @Override
    public <A> _<Ctor, A> msum(_<ListOf, _<Ctor, A>> list) {
        List<_<Ctor, A>> as = ListOf.unwrap(list);
        _<Ctor, A> result = mzero(); 
        for(_<Ctor, A> a : as) {
           result = mplus(result, a);
        }
        return result;
    }

    @Override
    public <A> _<Ctor, A> msumFlat(List<_<Ctor, A>> list) {
        _<Ctor, A> result = mzero(); 
        for(_<Ctor, A> a : list) {
           result = mplus(result, a);
        }
        return result;
    }

    @Override
    public <A> _<Ctor, A> mzero() {
        return empty();
    }

    @Override
    public <A> _<Ctor, A> mplus(_<Ctor, A> t1, _<Ctor, A> t2) {
        return or(t1, t2);
    }

    @Override
    public <A> Monoid<_<Ctor, A>> asMonoid() {
        return alternative.asMonoid();
    }
    
    @Override
    public <A> _<Ctor, A> mfilter(final F<A, Boolean> fn, _<Ctor, A> nestedA) {
        return bind(nestedA, new F<A, _<Ctor,A>>() {

            @Override
            public _<Ctor, A> f(A a) {
                return (fn.f(a)) ? pure(a) : MonadPlusAbstract.this.<A>mzero();
            }
        });
    }

    @Override
    public <A> F<_<Ctor,A>,_<Ctor, A>> mfilter(final F<A, Boolean> fn) {
        return new F<_<Ctor,A>,_<Ctor, A>>() {
            @Override
            public _<Ctor, A> f(_<Ctor, A> a) {
                return mfilter(fn, a);
            }
        };
    }

    //empty (Control.Applicative)
    @Override
    public <A> _<Ctor, A> empty() {
        return alternative.empty();
    }
    
    //<|> (Control.Applicative)
    @Override
    public <A> _<Ctor, A> or(_<Ctor, A> first, _<Ctor, A> second) {
        return alternative.or(first, second);
    }
    
    //optional (Control.Applicative)
    @Override
    public <A> _<Ctor, _<OptionOf, A>> optional(_<Ctor, A> nestedA) {
        return alternative.optional(nestedA);
    }
    
    //"flat" version of optional 
    @Override
    public <A> _<Ctor, Option<A>> optionalFlat(_<Ctor, A> nestedA) {
        return alternative.optionalFlat(nestedA);
    }

}
