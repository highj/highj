package org.highj.util;

import java.util.Iterator;

public abstract class ReadOnlyIterator<A> implements Iterator<A> {
    @Override
    public final void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
