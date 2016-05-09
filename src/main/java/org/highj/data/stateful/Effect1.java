/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful;

import org.derive4j.hkt.__;
import org.highj.data.stateful.effect1.Effect1Contravariant;
import org.highj.data.stateful.effect1.Effect1Monoid;
import org.highj.data.stateful.effect1.Effect1Semigroup;

/**
 *
 * @author clintonselke
 */
public interface Effect1<A> extends __<Effect1.µ,A> {
    public static class µ {}
    
    public static <A> Effect1<A> narrow(__<µ,A> a) {
        return (Effect1<A>)a;
    }
    
    public void run(A a);
    
    public static <A> Effect1Semigroup<A> semigroup() {
        return new Effect1Semigroup<A>() {};
    }
    
    public static <A> Effect1Monoid<A> monoid() {
        return new Effect1Monoid<A>() {};
    }
    
    public static final Effect1Contravariant contravariant = new Effect1Contravariant() {};
}
