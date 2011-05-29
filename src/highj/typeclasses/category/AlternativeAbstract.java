/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.Monoid;
import fj.data.Option;
import highj._;
import highj.data.OptionOf;
/**
 *
 * @author DGronau
 */
public abstract class AlternativeAbstract<Ctor> extends ApplicativeAbstract<Ctor> implements Alternative<Ctor> {

    private final Plus plus;
    
    public AlternativeAbstract(Plus<Ctor> plus) {
        this.plus = plus;
    }
    
    @Override
    public <A> _<Ctor, A> empty(){
        return plus.empty();
    }
            

    @Override
    public <A> _<Ctor, A> or(_<Ctor, A> first, _<Ctor, A> second) {
        return plus.empty();
    }

    /*@Override
    public <A> _<Ctor, _<ListOf, A>> some(_<Ctor, A> ta) {
        throw new UnsupportedOperationException();
    }*/
    
    /*@Override
    public <A> _<Ctor, _<ListOf, A>> many(_<Ctor, A> ta) {
        throw new UnsupportedOperationException();
    }*/

    @Override
    public <A> _<Ctor, _<OptionOf, A>> optional(_<Ctor, A> nestedA) {
        return or(fmap(new F<A,_<OptionOf,A>>(){
            @Override
            public _<OptionOf, A> f(A a) {
                return OptionOf.some(a);
            }
        }, nestedA), pure(OptionOf.<A>none()));
    }

    @Override
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
        return plus.asMonoid();
    }
  
}
