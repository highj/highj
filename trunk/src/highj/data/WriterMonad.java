/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import fj.F2;
import fj.Monoid;
import fj.P;
import fj.P2;
import highj._;
import highj.typeclasses.category.Monad;
import highj.typeclasses.category.MonadAbstract;

/**
 *
 * @author dgronau
 */
public class WriterMonad<W> extends MonadAbstract<Writer<W>> implements Monad<Writer<W>> {
    
    private final Monoid<W> monoid;
    
    public WriterMonad(Monoid<W> monoid) {
        this.monoid = monoid;
    }

    @Override
    public <A, B> _<Writer<W>, B> bind(_<Writer<W>, A> nestedA, F<A, _<Writer<W>, B>> fn) {
        P2<B,W> fnResult = Writer.unwrap(fn.f(Writer.getValue(nestedA)));
        return Writer.wrap(fnResult._1(), monoid.sum(Writer.getMonoidValue(nestedA), fnResult._2()));
    }

    @Override
    public <A, B> _<Writer<W>, B> ap(_<Writer<W>, F<A, B>> fn, _<Writer<W>, A> nestedA) {
        return tell(fmap(Writer.getValue(fn), nestedA), Writer.getMonoidValue(fn));
    }

    @Override
    public <A> _<Writer<W>, A> pure(A a) {
        return Writer.wrap(a, monoid.zero());
    }

    @Override
    public <A, B> _<Writer<W>, B> fmap(F<A, B> fn, _<Writer<W>, A> nestedA) {
        return Writer.wrap(fn.f(Writer.getValue(nestedA)), Writer.getMonoidValue(nestedA));
    }
    
    //adds a monoidValue w to a writer, leaving its value unchanged
    public <A> _<Writer<W>, A> tell(_<Writer<W>, A> nestedA, final W w) {
        return bind(nestedA, new F<A, _<Writer<W>, A>>() {
            @Override
            public _<Writer<W>, A> f(A a) {
                return Writer.wrap(a, w);
            }
        });
    }

    //keeps the value, but drops the monoidValue
    public <A> _<Writer<W>, A> forget(_<Writer<W>, A> nestedA) {
        return pure(Writer.getValue(nestedA));
    }
    
    //applies the function, but leaves the monoidValue unchanged
    public <A, B> _<Writer<W>, B> censor(_<Writer<W>, A> nestedA, F<A, _<Writer<W>, B>> fn) {
        P2<B,W> fnResult = Writer.unwrap(fn.f(Writer.getValue(nestedA)));
        return Writer.wrap(fnResult._1(), Writer.getMonoidValue(nestedA));
    }

    //combines two Writers to a Writer of a pair
    public <A, B> _<Writer<W>, P2<A,B>> pair(_<Writer<W>, A> nestedA, _<Writer<W>, B> nestedB) {
        return lift2Flat(new F2<A,B,P2<A,B>>(){
            @Override
            public P2<A, B> f(A a, B b) {
                return P.p(a,b);
            }
            
        }).f(nestedA, nestedB);
    }
}
