package org.highj.util;

import java.util.Iterator;

public interface ReadOnlyIterator<A> extends Iterator<A> {
    @Override
    public default void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
