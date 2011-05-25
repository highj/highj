/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.Monoid;
import fj.P;
import fj.P2;
import highj._;

/**
 *
 * @author dgronau
 */
public final class Writer<W> {
    
    private Writer(){};
    
    public static <A,W> _<Writer<W>,A> wrap(A a, W w) {
        return new _<Writer<W>,A>(new Writer<W>(), P.p(a,w));
    }
    
    public static <A,W> P2<A,W> unwrap(_<Writer<W>,A> writer) {
        return (P2<A,W>) writer.read(new Writer<W>());
    }

    public static <W,A> A getValue(_<Writer<W>,A> writer) {
        return unwrap(writer)._1();
    }

    public static <W,A> W getMonoidValue(_<Writer<W>,A> writer) {
        return unwrap(writer)._2();
    }
    
    public static <W> WriterMonad<W> monad(Monoid<W> monoid) {
        return new WriterMonad(monoid);
    }
}
