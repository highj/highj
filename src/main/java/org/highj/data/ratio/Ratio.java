/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.ratio;

/**
 *
 * @author clintonselke
 */
public class Ratio<A> {
    private final A _numerator;
    private final A _denominator;
    
    private Ratio(A numerator, A denominator) {
        this._numerator = numerator;
        this._denominator = denominator;
    }
    
    public static <A> Ratio<A> ratio(A numerator, A denominator) {
        return new Ratio<>(numerator, denominator);
    }
    
    public A numerator() {
        return _numerator;
    }
    
    public A denominator() {
        return _denominator;
    }
}
