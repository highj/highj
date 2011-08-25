/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import highj.typeclasses.category.Applicative;
import highj.typeclasses.category.ApplicativeAbstract;
import java.util.Iterator;

/**
 * Behaves similar like Haskell's ZipList Applicative instance, e.g.
 * pure(a) results in [a,a,a,...], and * 
 * ap([f,g,h],[a,b,c,d]) results in [f(a),g(b),h(c)]
 * 
 * @author dgronau
 */
public class IteratorApplicative extends ApplicativeAbstract<IteratorOf> implements Applicative<IteratorOf> {

    @Override
    public <A> _<IteratorOf, A> pure(final A a) {
        return IteratorOf.wrap(new Iterator<A>(){

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                return a;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
    }

    @Override
    public <A, B> _<IteratorOf, B> ap(final _<IteratorOf, F<A, B>> fn, final _<IteratorOf, A> nestedA) {
        return IteratorOf.wrap(new Iterator<B>() {

            private Iterator<A> as = IteratorOf.unwrap(nestedA);
            private Iterator<F<A, B>> fns = IteratorOf.unwrap(fn);

            @Override
            public boolean hasNext() {
                return as.hasNext() && fns.hasNext();
            }

            @Override
            public B next() {
                return fns.next().f(as.next());
            }

            @Override
            public void remove() {
                fns.remove();
                as.remove();
            }
        });
    }
}
