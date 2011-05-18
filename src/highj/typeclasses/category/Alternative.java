/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.Monoid;
import fj.data.Option;
import highj._;
import highj.data.OptionOf;

/**
 *
 * @author DGronau
 */
public interface Alternative<Ctor> extends Applicative<Ctor> {
    //empty (Control.Applicative)
    public <A> _<Ctor, A> empty();
    
    //<|> (Control.Applicative)
    public <A> _<Ctor, A> or(_<Ctor, A> first, _<Ctor, A> second);
    
    //optional (Control.Applicative)
    public <A> _<Ctor, _<OptionOf, A>> optional(_<Ctor, A> nestedA);
    
    //"flat" version of optional 
    public <A> _<Ctor, Option<A>> optionalFlat(_<Ctor, A> nestedA);
    
    public <A> Monoid<_<Ctor, A>> asMonoid();
    
    //How to implement these? (preferably iterative)
    //public <A> _<Ctor, _<ListOf,A>> some(_<Ctor, A> ta);
    //public <A> _<Ctor, _<ListOf,A>> many(_<Ctor, A> ta);
    

}
