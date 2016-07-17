/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass0.num;

import org.highj.data.List;
import org.highj.data.Stream;

/**
 *
 * @author clintonselke
 */
public interface Enum<A> {
    
    public A toEnum(int a);
    
    public int fromEnum(A a);
    
    public default A succ(A a) {
        return toEnum(fromEnum(a)+1);
    }
    
    public default A pred(A a) {
        return toEnum(fromEnum(a)-1);
    }
    
    public default Stream<A> enumFrom(A a) {
        return Stream.range(fromEnum(a)).map(this::toEnum);
    }
    
    public default Stream<A> enumFromThen(A a, A b) {
        int x = fromEnum(a);
        int y = fromEnum(b);
        return Stream.range(x, y-x).map(this::toEnum);
    }
    
    public default List<A> enumFromTo(A a, A b) {
        return List.range(fromEnum(a), 1, fromEnum(b)).map(this::toEnum);
    }
    
    public default List<A> enumFromThenTo(A a, A b, A c) {
        if (a.equals(b) && b.equals(c)) {
            return List.cycle(a);
        }
        int x = fromEnum(a);
        int y = fromEnum(b);
        int z = fromEnum(c);
        return List.range(x, y-x, z).map(this::toEnum);
    }
}
