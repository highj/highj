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
public final class RC<Ctor, B> {
    
    private RC(){}
    
    public static <Ctor, A, B> __<Ctor, A, B> uncurry(_<RC<Ctor, B>, A> curried) {
        return (__<Ctor, A, B>) curried.read(new RC<Ctor,B>());
    }
    
    public static <Ctor, A, B>  _<RC<Ctor, B>, A> curry( __<Ctor, A, B> wrapped) {
        return new _<RC<Ctor, B>, A>(new RC<Ctor,B>(), wrapped);
    }
    
    public static <Ctor, A, B> String toString(_<RC<Ctor, B>, A> curried) {
        return uncurry(curried).toString();
    }
    
}
