/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.List;
import highj.TC;
import highj._;
import highj._.Accessor;


/**
 *
 * @author DGronau
 */
public final class ListOf implements TC<ListOf>  {
    
    private Accessor<ListOf> accessor;

    public ListOf() {
        _.register(this);
    }
    
    @Override
    public void setAccessor(Accessor<ListOf> accessor) {
        if(this.accessor == null) {
           this.accessor = accessor;
        }
    }
    
    public <T> _<ListOf, T> wrap(List<T> list) {
        return accessor.make(list);
    }
    
    public <T> List<T> unwrap(_<ListOf, T> listWrapper) {
        return (List<T>) accessor.read(listWrapper);
    }

    public <T> _<ListOf, T> empty() {
        return wrap(List.<T>nil());
    }

    public <T> boolean isEmpty(_<ListOf, T> listWrapper) {
        return unwrap(listWrapper).isEmpty();
    }
    
    public String toString(_<ListOf, ?> wrapped) {
        return  unwrap(wrapped).toCollection().toString();
    }
  
    private static ListOf INSTANCE = new ListOf();
    
    public static ListOf getInstance() {
        return INSTANCE;
    }
}
