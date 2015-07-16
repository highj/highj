/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import java.util.function.Supplier;
import org.highj.__;

/**
 * Allows one to define an ArrowTransformer for a StreamArrow in a strict language. (May also have other uses) <br>
 * It can be defined when the data type used to represent the arrow is non-strict. (Eg. A base arrow of F1, Kleisli, etc.) <br>
 * This type-class is not a standard part of Haskell.
 * @author clintonselke
 */
public interface ArrowLazy<A> extends Arrow<A> {
    
    public <B,C> __<A,B,C> lazy(Supplier<__<A,B,C>> a);
}
