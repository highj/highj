/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.P2;
import highj.TC2;
import highj.__;
import highj.__.Accessor2;

/**
 *
 * @author DGronau
 */
public class PairOf implements TC2<PairOf> {

    private Accessor2<PairOf> accessor;

    public PairOf() {
        __.register(this);
    }
    
    public <A, B> __<PairOf, A, B> wrap(P2<A, B> either) {
        return accessor.make(either);
    }

    public <A, B> P2<A, B> unwrap(__<PairOf, A, B> wrapped) {
        return (P2<A, B>) accessor.read(wrapped);
    }
    
    public <A, B> A fst(__<PairOf, A, B> wrapped) {
        return unwrap(wrapped)._1();
    }

    public <A, B> B snd(__<PairOf, A, B> wrapped) {
        return unwrap(wrapped)._2();
    }

    @Override
    public void setAccessor(Accessor2<PairOf> accessor) {
       assert this.accessor == null;
       this.accessor = accessor;
    }
    
    private static final PairOf INSTANCE = new PairOf();

    public static PairOf getInstance() {
        return INSTANCE;
    }

}
