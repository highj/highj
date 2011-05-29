/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F2;
import fj.Monoid;
import highj._;

/**
 *
 * @author DGronau
 */
public abstract class PlusAbstract<Ctor> extends AltAbstract<Ctor> implements Plus<Ctor> {

    @Override
    public abstract <A> _<Ctor, A> empty();
   
        /*@Override
    public <A> _<Ctor, _<ListOf, A>> some(_<Ctor, A> ta) {
        throw new UnsupportedOperationException();
    }*/
    
    /*@Override
    public <A> _<Ctor, _<ListOf, A>> many(_<Ctor, A> ta) {
        throw new UnsupportedOperationException();
    }*/

  
  @Override
    public <A> Monoid<_<Ctor, A>> asMonoid() {
        return asMonoid(this);
    }
    
    public static <Ctor,T>  Monoid<_<Ctor, T>> asMonoid(final Plus<Ctor> alt) {
        return Monoid.<_<Ctor, T>>monoid(new F2<_<Ctor, T>, _<Ctor, T>, _<Ctor, T>>(){

            @Override
            public _<Ctor, T> f(_<Ctor, T> a, _<Ctor, T> b) {
                return alt.or(a, b);
            }
        }, alt.<T>empty());
    }
}
