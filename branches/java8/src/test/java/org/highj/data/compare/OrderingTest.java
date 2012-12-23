package org.highj.data.compare;

import org.highj.data.collection.List;
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
        assertEquals(EQ, monoid.identity());
        assertEquals(EQ, monoid.dot(EQ, EQ));
        assertEquals(LT, monoid.dot(LT, EQ));
        assertEquals(LT, monoid.dot(LT, GT));
        assertEquals(GT, monoid.dot(GT, EQ));
        assertEquals(GT, monoid.dot(EQ, GT));
        assertEquals(GT, monoid.fold(EQ, EQ, GT, LT, EQ));
    }

}
