/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass1.monad;

/**
 *
 * @author clintonselke
 */
public interface MonadRWS<R,W,S,M> extends MonadReader<R,M>, MonadWriter<W,M>, MonadState<S,M> {
}
