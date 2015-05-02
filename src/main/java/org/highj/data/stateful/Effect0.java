/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.stateful;

import org.highj.data.stateful.effect0.Effect0Monoid;
import org.highj.data.stateful.effect0.Effect0Semigroup;

/**
 *
 * @author clintonselke
 */
public interface Effect0 {
    
    public void run();
    
    public static final Effect0Semigroup semigroup = new Effect0Semigroup() {};
    
    public static final Effect0Monoid monoid = new Effect0Monoid() {};
}
