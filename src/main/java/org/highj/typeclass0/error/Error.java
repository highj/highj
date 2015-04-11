/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass0.error;

/**
 *
 * @author clintonselke
 */
public interface Error<E> {
    
    E noMsg();
    
    E strMsg(String msg);
}
