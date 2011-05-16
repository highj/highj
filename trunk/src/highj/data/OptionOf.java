/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.Option;
import highj.TC;
import highj._;
import highj._.Accessor;

/**
 *
 * @author DGronau
 */
public final class OptionOf implements TC<OptionOf> {
    
   private Accessor<OptionOf> accessor;
    
   public OptionOf() {
       _.register(this);
   }
    
    public <T> _<OptionOf, T> option(T t) {
        return t == null ? this.<T>none() : some(t);
    }
    
    public <T> _<OptionOf, T> some(T t) {
        return wrap(Option.some(t));
    }

    public <T> _<OptionOf, T> none() {
        return wrap(Option.<T>none());
    }
    
    public <T> _<OptionOf, T> wrap(Option<T> ot) {
        return accessor.make(ot);
    }

    public <T> Option<T> unwrap(_<OptionOf, T> ow) {
        return (Option<T>) accessor.read(ow);
    }

    public boolean isSome(_<OptionOf, ?> ow) {
        return unwrap(ow).isSome();
    }

    public boolean isNone(_<OptionOf, ?> ow) {
        return unwrap(ow).isNone();
    }

    public <T> T orSome(_<OptionOf, T> ow, T t) {
        return unwrap(ow).orSome(t);
    }

    public <T> T get(_<OptionOf, T> ow) {
        return unwrap(ow).valueE("No such element");
    }
    
    private static final OptionOf INSTANCE = new OptionOf();

    public static OptionOf getInstance() {
        return INSTANCE;
    }

    @Override
    public void setAccessor(Accessor<OptionOf> accessor) {
        if(this.accessor == null) {
           this.accessor = accessor;
        }
    }
}
