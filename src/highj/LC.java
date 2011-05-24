/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

/**
 * Curry Left:
 * Translates a binary type constructor into an unary type constructor:
 * __<Ctor,A,B> becomes _<CL<Ctor,A>,B>
 * @author DGronau
 */
public final class LC<Ctor, A> {
    
    private LC(){}
    
    public static <Ctor, A, B> __<Ctor, A, B> uncurry(_<LC<Ctor, A>, B> curried) {
        return (__<Ctor, A, B>) curried.read(new LC<Ctor,A>());
    }
    
    public static <Ctor, A, B>  _<LC<Ctor, A>, B> curry( __<Ctor, A, B> wrapped) {
        return new _<LC<Ctor, A>, B>(new LC<Ctor,A>(), wrapped);
    }
    
    public static <Ctor, A, B> String toString(_<LC<Ctor, A>, B> curried) {
        return uncurry(curried).toString();
    }
    
}
