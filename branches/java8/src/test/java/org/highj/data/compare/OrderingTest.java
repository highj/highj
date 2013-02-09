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
        assertEquals(EQ, group.identity());
        assertEquals(EQ, group.dot(EQ, EQ));
        assertEquals(LT, group.dot(LT, EQ));
        assertEquals(LT, group.dot(LT, GT));
        assertEquals(GT, group.dot(GT, EQ));
        assertEquals(GT, group.dot(EQ, GT));
        assertEquals(GT, group.fold(EQ, EQ, GT, LT, EQ));
    }

}
