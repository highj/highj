/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.Monoid;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Plus<Ctor> extends Alt<Ctor> {
   //empty (Control.Applicative)
    public <A> _<Ctor, A> empty();
    
    //<|> (Control.Applicative)
    @Override
    public <A> _<Ctor, A> or(_<Ctor, A> first, _<Ctor, A> second);
    
    public <A> Monoid<_<Ctor, A>> asMonoid();
    
   
}
