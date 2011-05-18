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
public abstract class MonadPlusAbstract<Ctor> extends MonadAbstract<Ctor> implements MonadPlus<Ctor> {

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
    //duplicated from AlternativeAbstract
    public <A> _<Ctor, _<OptionOf, A>> optional(_<Ctor, A> nestedA) {
        return or(fmap(new F<A,_<OptionOf,A>>(){
            @Override
            public _<OptionOf, A> f(A a) {
                return OptionOf.some(a);
            }
        }, nestedA), pure(OptionOf.<A>none()));
    }

    @Override
    //duplicated from AlternativeAbstract
    //"flat" version of optional
    public <A> _<Ctor, Option<A>> optionalFlat(_<Ctor, A> nestedA) {
        return or(fmap(new F<A, Option<A>>(){
            @Override
            public Option<A> f(A a) {
                return Option.some(a);
            }
        }, nestedA), pure(Option.<A>none()));
    }
    
    @Override
    public <A> Monoid<_<Ctor, A>> asMonoid() {
        return AlternativeAbstract.asMonoid(this);
    }

}
