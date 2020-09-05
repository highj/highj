package org.highj.util;

/**
 * A test for invariants of a certain type class
 */
public interface Law {

    /**
     * Test all invariants of the given type class.
     * <p>
     * If the parent class implements {@link Law}, <code>super.test();</code> must be
     * called as well.
     */
    void test();
}
