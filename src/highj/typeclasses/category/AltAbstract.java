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
public abstract class AltAbstract<Ctor> extends FunctorAbstract<Ctor> implements Alt<Ctor> {

    // <|> (Control.Alternative), <!> (Data.Functor.Alt)
    @Override
    public abstract <A> _<Ctor, A> or(_<Ctor, A> first, _<Ctor, A> second);
    
}
