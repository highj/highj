/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

import highj._.Accessor;

/**
 * Curry Right:
 * Translates a binary type constructor into an unary type constructor:
 * __<Ctor,A,B> becomes _<CR<Ctor,B>,A>
 * @author DGronau
 */
public class CR<Ctor extends TC2<Ctor>, B> implements TC<CR<Ctor, B>> {
    private Accessor<CR<Ctor, B>> accessor;

    public CR() {
        _.register(this);
    }
    
    public <A> __<Ctor, A, B> uncurry(_<CR<Ctor, B>, A> curried) {
        return (__<Ctor, A, B>) accessor.read(curried);
    }
    
    public <A>  _<CR<Ctor, B>, A> curry( __<Ctor, A, B> wrapped) {
        return accessor.make(wrapped);
    }
    
    public String toString(_<CR<Ctor, B>, ?> curried) {
        return uncurry(curried).toString();
    }
    
    @Override
    public void setAccessor(Accessor<CR<Ctor, B>> accessor) {
        this.accessor = accessor;
    }
    
}
