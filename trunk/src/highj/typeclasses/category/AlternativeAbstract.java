/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Monoid;
import fj.data.Option;
import highj.TC;
import highj._;
import highj.data.OptionOf;
/**
 *
 * @author DGronau
 */
public abstract class AlternativeAbstract<Ctor extends TC<Ctor>> extends ApplicativeAbstract<Ctor> implements Alternative<Ctor> {

    
    @Override
    public abstract <A> _<Ctor, A> empty();

    @Override
    public abstract <A> _<Ctor, A> or(_<Ctor, A> first, _<Ctor, A> second);

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
        final OptionOf optionOf = OptionOf.getInstance();
        return or(fmap(new F<A,_<OptionOf,A>>(){
            @Override
            public _<OptionOf, A> f(A a) {
                return optionOf.some(a);
            }
        }, nestedA), pure(optionOf.<A>none()));
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
        return asMonoid(this);
    }
    
    public static <C extends TC<C>,T>  Monoid<_<C, T>> asMonoid(final Alternative<C> alt) {
        return Monoid.<_<C, T>>monoid(new F2<_<C, T>, _<C, T>, _<C, T>>(){

            @Override
            public _<C, T> f(_<C, T> a, _<C, T> b) {
                return alt.or(a, b);
            }
        }, alt.<T>empty());
    }
}
