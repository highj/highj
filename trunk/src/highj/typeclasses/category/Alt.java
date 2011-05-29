/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import highj._;

/**
 *
 * @author DGronau
 */
public interface Alt<Ctor> extends Functor<Ctor> {
    
    //<|> (Control.Applicative), <!> (Data.Functor.Alt)
    public <A> _<Ctor, A> or(_<Ctor, A> first, _<Ctor, A> second);
    
}
