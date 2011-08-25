/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import highj.typeclasses.category.Applicative;
import highj.typeclasses.category.ApplicativeAbstract;
import java.util.HashSet;
import java.util.Set;

/**
 * Note that all types you use here need a suitable implementation of
 * hashcode() and equals().
 * 
 * @author dgronau
 */
public class SetApplicative extends ApplicativeAbstract<SetOf> implements Applicative<SetOf> {

    @Override
    public <A> _<SetOf, A> pure(A a) {
        return SetOf.set(a);
    }

    @Override
    public <A, B> _<SetOf, B> ap(_<SetOf, F<A, B>> fn, _<SetOf, A> nestedA) {
        Set<B> result = new HashSet<B>();
        for(A a : SetOf.unwrap(nestedA)) {
            for(F<A,B> f : SetOf.unwrap(fn)) {
                result.add(f.f(a));
            }
        }
        return SetOf.wrap(result);
    }
    
}
