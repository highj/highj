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
public final class CL<Ctor, A> {
    
    private CL(){}
    
    public static <Ctor, A, B> __<Ctor, A, B> uncurry(_<CL<Ctor, A>, B> curried) {
        return (__<Ctor, A, B>) curried.read(new CL<Ctor,A>());
    }
    
    public static <Ctor, A, B>  _<CL<Ctor, A>, B> curry( __<Ctor, A, B> wrapped) {
        return new _<CL<Ctor, A>, B>(new CL<Ctor,A>(), wrapped);
    }
    
    public static <Ctor, A, B> String toString(_<CL<Ctor, A>, B> curried) {
        return uncurry(curried).toString();
    }
    
}
