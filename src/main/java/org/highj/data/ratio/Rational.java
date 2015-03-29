/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.ratio;

import java.math.BigInteger;

/**
 *
 * @author clintonselke
 */
public class Rational {
    private Ratio<BigInteger> _ratio;
    
    private Rational(Ratio<BigInteger> ratio) {
        this._ratio = ratio;
    }
    
    public static Rational rational(Ratio<BigInteger> ratio) {
        return new Rational(ratio);
    }
    
    public Ratio<BigInteger> ratio() {
        return _ratio;
    }
}
