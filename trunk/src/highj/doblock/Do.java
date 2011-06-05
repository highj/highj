/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.doblock;

import fj.Unit;
import highj._;
import highj.typeclasses.category.Monad;

/**
 *
 * @author DGronau
 */
public class Do<Ctor, A> {
    protected final Monad<Ctor> monad;
    protected final _<Ctor,A> returnValue;
    
    Do(Monad<Ctor> monad, _<Ctor,A> action) {
        this.monad = monad;
        this.returnValue = action;
    }
    
    public <B> Do<Ctor,B> act(_<Ctor,B> action) {
        return new Do<Ctor,B>(monad, monad.semicolon(returnValue, action));
    }

    public <B> _<Ctor,B> return_(B b) {
        return monad.semicolon(returnValue, monad.pure(b));
    }
    
    public _<Ctor,A> return_() {
        return returnValue;
    }
    
    public <B> Do1<Ctor,B,B> assign1(_<Ctor,B> action) {
        return new Do1<Ctor, B, B>(monad, monad.semicolon(returnValue,action), action);
    }
    
    public static <Ctor> Do<Ctor, Unit> with(Monad<Ctor> monad) {
        return new Do<Ctor, Unit>(monad, monad.pure(Unit.unit()));
    }
}
