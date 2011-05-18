/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Monoid;
import fj.data.Option;
import highj._;
import highj.data.OptionOf;
/**
 *
 * @author DGronau
 */
public abstract class AlternativeAbstract<Ctor> extends ApplicativeAbstract<Ctor> implements Alternative<Ctor> {

    
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
        return asMonoid(this);
    }
    
    public static <Ctor,T>  Monoid<_<Ctor, T>> asMonoid(final Alternative<Ctor> alt) {
        return Monoid.<_<Ctor, T>>monoid(new F2<_<Ctor, T>, _<Ctor, T>, _<Ctor, T>>(){

            @Override
            public _<Ctor, T> f(_<Ctor, T> a, _<Ctor, T> b) {
                return alt.or(a, b);
            }
        }, alt.<T>empty());
    }
}
