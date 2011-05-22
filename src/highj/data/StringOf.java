/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import highj._;

/**
 *
 * @author DGronau
 */
public final class StringOf {
    private static StringOf hidden = new StringOf();

    public static <A extends Character> _<StringOf, A> wrap(String string) {
        return new _<StringOf, A>(hidden, string);
    }
    
    public static String unwrap(_<StringOf, ? extends Character> stringWrapper) {
        return (String) stringWrapper.read(hidden);
    }
}
