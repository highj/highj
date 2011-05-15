/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

import highj._.Accessor;

/**
 * Curry Left:
 * Translates a binary type constructor into an unary type constructor:
 * __<Ctor,A,B> becomes _<CL<Ctor,A>,B>
 * @author DGronau
 */
public class CL<Ctor extends TC2<Ctor>, A> implements TC<CL<Ctor, A>> {
    private Accessor<CL<Ctor, A>> accessor;

    public CL() {
        _.register(this);
    }
    
    public <B> __<Ctor, A, B> uncurry(_<CL<Ctor, A>, B> curried) {
        return (__<Ctor, A, B>) accessor.read(curried);
    }
    
    public <B>  _<CL<Ctor, A>, B> curry( __<Ctor, A, B> wrapped) {
        return accessor.make(wrapped);
    }
    
    public String toString(_<CL<Ctor, A>, ?> curried) {
        return uncurry(curried).toString();
    }
    
    @Override
    public void setAccessor(Accessor<CL<Ctor, A>> accessor) {
        this.accessor = accessor;
    }
    
}
