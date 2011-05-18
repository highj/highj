/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.List;
import highj._;

/**
 *
 * @author DGronau
 */
public final class ListOf  {
    
    private static ListOf hidden = new ListOf();

    public static <T> _<ListOf, T> wrap(List<T> list) {
        return new _<ListOf, T>(hidden, list);
    }
    
    public static <T> List<T> unwrap(_<ListOf, T> listWrapper) {
        return (List<T>) listWrapper.read(hidden);
    }

    public static <T> _<ListOf, T> empty() {
        return wrap(List.<T>nil());
    }

    public static <T> boolean isEmpty(_<ListOf, T> listWrapper) {
        return unwrap(listWrapper).isEmpty();
    }
    
    public static String toString(_<ListOf, ?> wrapped) {
        return  unwrap(wrapped).toCollection().toString();
    }
  
}
