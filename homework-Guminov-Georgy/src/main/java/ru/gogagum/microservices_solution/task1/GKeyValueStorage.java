package ru.gogagum.microservices_solution.task1;

import ru.mipt1c.homework.task1.KeyValueStorage;

import java.io.IOException;
import java.util.Iterator;

public class GKeyValueStorage<K, V> implements KeyValueStorage<K, V> {
    @Override
    public Object read(Object key) {
        return null;
    }

    @Override
    public boolean exists(Object key) {
        return false;
    }

    @Override
    public void write(Object key, Object value) {

    }

    @Override
    public void delete(Object key) {

    }

    @Override
    public Iterator readKeys() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
