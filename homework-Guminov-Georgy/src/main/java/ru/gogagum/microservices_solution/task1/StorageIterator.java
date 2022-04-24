package ru.gogagum.microservices_solution.task1;

import java.util.Iterator;

public class StorageIterator<K> implements Iterator<K> {
    private boolean ownerClosed = false;
    private final Iterator<K> underlyingIter;

    StorageIterator(Iterator<K> underlyingIter) {
        this.underlyingIter = underlyingIter;
    }

    @Override
    public boolean hasNext() {
        if (ownerClosed) {
            throw new StorageClosedException("Can not check next");
        }

        return underlyingIter.hasNext();
    }

    @Override
    public K next() {
        if (ownerClosed) {
            throw new StorageClosedException("Can not iterate.");
        }

        return underlyingIter.next();
    }

    public void invalidate() {
        ownerClosed = true;
    }
}
