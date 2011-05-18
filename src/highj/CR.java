/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

/**
 * Curry Right:
 * Translates a binary type constructor into an unary type constructor:
 * __<Ctor,A,B> becomes _<CR<Ctor,B>,A>
 * @author DGronau
 */
public final class CR<Ctor, B> {
    
    private CR(){}
    
    public static <Ctor, A, B> __<Ctor, A, B> uncurry(_<CR<Ctor, B>, A> curried) {
        return (__<Ctor, A, B>) curried.read(new CR<Ctor,B>());
    }
    
    public static <Ctor, A, B>  _<CR<Ctor, B>, A> curry( __<Ctor, A, B> wrapped) {
        return new _<CR<Ctor, B>, A>(new CR<Ctor,B>(), wrapped);
    }
    
    public static <Ctor, A, B> String toString(_<CR<Ctor, B>, A> curried) {
        return uncurry(curried).toString();
    }
    
}
