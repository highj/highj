/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import highj._;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author dgronau
 */
public class IteratorOf {
    private static IteratorOf hidden = new IteratorOf();
    
    private IteratorOf(){ }

    public static <T> _<IteratorOf, T> wrap(Iterator<T> iterator) {
        return new _<IteratorOf, T>(hidden, iterator);
    }

    public static <T> Iterator<T> unwrap(_<IteratorOf, T> iteratorWrapper) {
        return (Iterator<T>) iteratorWrapper.read(hidden);
    }
 
    public static <T> _<IteratorOf, T> iterator(T ... ts) {
        return new _<IteratorOf, T>(hidden, Arrays.asList(ts).iterator());
    }

}
