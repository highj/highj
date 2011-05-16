/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import highj.CL;
import highj.CR;
import highj.TC2;
import highj._;
import highj.__;
import highj.__.Accessor2;

/**
 *
 * @author DGronau
 */
public class FunctionOf implements TC2<FunctionOf> {

    private Accessor2<FunctionOf> accessor;

    public FunctionOf() {
       __.register(this);
    }    
    
    public <A, B> __<FunctionOf, A, B> wrap(F<A, B> fn) {
        return accessor.make(fn);
    }

    public <A, B> _<CL<FunctionOf, A>, B> wrapCL(F<A, B> fn) {
        return new CL<FunctionOf, A>().curry(wrap(fn));
    }

    public <A, B> _<CR<FunctionOf, B>, A> wrapCR(F<A, B> fn) {
        return new CR<FunctionOf, B>().curry(wrap(fn));
    }
    
    public <A, B> F<A, B> unwrap(__<FunctionOf, A, B> wrapped) {
        return (F<A, B>) accessor.read(wrapped);
    }

    public <A, B> F<A, B> unwrapCL(_<CL<FunctionOf, A>, B> curried) {
        return unwrap(new CL<FunctionOf, A>().uncurry(curried));
    }

    public <A, B> F<A, B> unwrapCR(_<CR<FunctionOf, B>, A> curried) {
        return unwrap(new CR<FunctionOf, B>().uncurry(curried));
    }

    public <A, B> B apply(__<FunctionOf, A, B> wrapped, A a) {
        return unwrap(wrapped).f(a);
    }
    
    @Override
    public void setAccessor(Accessor2<FunctionOf> accessor) {
        if (this.accessor == null) {
            this.accessor = accessor;
        }
    }
    
    private static FunctionOf INSTANCE = new FunctionOf();

    static FunctionOf getInstance() {
        return INSTANCE;
    }
    
}
