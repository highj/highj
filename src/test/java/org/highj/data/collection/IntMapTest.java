/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.collection;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author clintonselke
 */
public class IntMapTest {
    @Test
    public void testIntMap() {
        IntMap<String> m = IntMap.empty();
        for (int i = 0; i < 1000; ++i) {
            m = m.insert(i, Integer.toHexString(i));
        }
        for (int i = 0; i < 1000; ++i) {
            assertThat(m.lookup(i).toString()).isEqualTo("Just(" + Integer.toHexString(i) + ")");
        }
    }
}
