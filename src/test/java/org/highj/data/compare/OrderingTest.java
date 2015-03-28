package org.highj.data.compare;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.highj.data.compare.Ordering.*;

public class OrderingTest {
    @Test
    public void cmpResultTest() throws Exception {
        assertEquals(-1, LT.cmpResult());
        assertEquals(0, EQ.cmpResult());
        assertEquals(1, GT.cmpResult());
    }

    @Test
    public void monoidTest() throws Exception {
        assertEquals(EQ, group.identity());
        assertEquals(EQ, group.apply(EQ, EQ));
        assertEquals(LT, group.apply(LT, EQ));
        assertEquals(LT, group.apply(LT, GT));
        assertEquals(GT, group.apply(GT, EQ));
        assertEquals(GT, group.apply(EQ, GT));
        assertEquals(GT, group.fold(EQ, EQ, GT, LT, EQ));
    }

}
