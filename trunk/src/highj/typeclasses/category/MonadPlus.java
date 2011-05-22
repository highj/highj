/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.data.List;
import highj._;
import highj.data.ListOf;

/**
 *
 * @author DGronau
 */
public interface MonadPlus<Ctor> extends Monad<Ctor>, Alternative<Ctor> {

    // mzero (Control.Monad)
    // equivalent to Alternative.empty
    public <A> _<Ctor, A> mzero();

    // mplus (Control.Monad)
    // equivalent to Alternative.or
    public <A> _<Ctor, A> mplus(_<Ctor, A> first, _<Ctor, A> second);

    // msum (Control.Monad)
    public <A> _<Ctor, A> msum(_<ListOf, _<Ctor, A>> list);

    // "flat" version of msum
    public <A> _<Ctor, A> msumFlat(List<_<Ctor, A>> list);
    
    public <A> _<Ctor, A> mfilter(F<A, Boolean> fn, _<Ctor, A> nestedA);
}
