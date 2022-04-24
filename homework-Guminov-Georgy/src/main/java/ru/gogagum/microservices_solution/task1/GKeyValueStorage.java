package ru.gogagum.microservices_solution.task1;

import lombok.extern.slf4j.Slf4j;
import ru.mipt1c.homework.task1.KeyValueStorage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class GKeyValueStorage<K, V> implements KeyValueStorage<K, V> {

    private final HashMap<K, V> storage;
    private final List<StorageIterator<K>> givenIterators;
    private final Path filePath;
    private boolean closed;

    @SuppressWarnings("unchecked")
    public GKeyValueStorage(String pathStr) {
        closed = false;
        Path path = Paths.get(pathStr);
        givenIterators = new ArrayList<>();
        try {
            filePath = Paths.get(pathStr, "data");
            if (Files.exists(filePath)) {
                log.debug("Created new file with name: " + path);
                ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(filePath));

                storage = (HashMap<K, V>)ois.readObject();
                ois.close();
            } else {
                storage = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public V read(K key) {
        if (closed) {
            throw new StorageClosedException("Can not read.");
        }

        return storage.get(key);
    }

    @Override
    public boolean exists(K key) {
        return storage.containsKey(key);
    }

    @Override
    public void write(K key, V value) {
        if (closed) {
            throw new StorageClosedException("Can not write.");
        }

        storage.put(key, value);
    }

    @Override
    public void delete(K key) {
        if (closed) {
            throw new StorageClosedException("Can not delete.");
        }

        storage.remove(key);
    }

    @Override
    public Iterator<K> readKeys() {
        if (closed) {
            throw new StorageClosedException("Can not read keys.");
        }

        StorageIterator<K> ret = new StorageIterator<>(storage.keySet().iterator());
        givenIterators.add(ret);

        return ret;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void close() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath));
        oos.writeObject(storage);
        closed = true;

        for (StorageIterator<K> iter : givenIterators) {
            iter.invalidate();
        }
    }

    @Override
    public void flush() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath));
            oos.writeObject(storage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
